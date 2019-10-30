package com.newlife.s4.util;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.util.HtmlUtils;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author Steven
 */
public class XmlUtil {
    /**
     * 将对象直接转换成String类型的 XML输出
     *
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        // 创建输出流
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * 将对象根据路径转换成xml文件
     *
     * @param obj
     * @param path
     * @return
     */
    public static void convertToXml(Object obj, String path) {
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(obj.getClass());

            Marshaller marshaller = context.createMarshaller();
            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            // 创建输出流
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static <T> T convertXmlStrToObject(Class clazz, String xmlStr) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static <T> T convertXmlFileToObject(Class clazz, String xmlPath) {
        T xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            xmlObject = (T) unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    /**
     * 将实体类转为XML
     *
     * @param t     实体类对象
     * @param clazz 需要转换的类(包含子类)
     * @return
     */
    public static <T> String toXML(T t, Class<?>... clazz) {
        try {
            XStream xStream = new XStream(new XppDriver(new XStreamNameCoder()));
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(clazz);
            return xStream.toXML(t);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 将XML转为实体类
     *
     * @param xml   XML原文
     * @param clazz 需要转换的类(包含子类)
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseObject(String xml, Class<?>... clazz) {
        try {
            XStream xStream = new XStream(new XppDriver(new XStreamNameCoder()));
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(clazz);
            return (T) xStream.fromXML(xml);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 将输入流中的XML解析为json dom4j
     *
     * @param inputStream
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static  Map<String, String> parseInputStreamToJson(InputStream inputStream) throws DocumentException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 将map转为xml {ToUserName,FromUserName,Articles:{Title,Description,PicUrl,Url}}
     *
     * @param map
     * @return
     */
    private static final String cdata = "<![CDATA[%s]]>";

    public static String tranfXML(Map<String, Object> map) {

        Document document = DocumentHelper.createDocument();
        Element rootElement = document.addElement("xml");
        for (Map.Entry<String, Object> m : map.entrySet()) {
            String key = m.getKey();

            if ("Articles".equals(key))
                continue;

            Element element = rootElement.addElement(key);
            if ("MsgId".equals(key) || "ArticleCount".equals(key)) {
                element.setText(String.valueOf(m.getValue()));
            } else {
                element.setText(String.format(cdata, m.getValue()));
            }

        }
        int createTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
        rootElement.addElement("CreateTime").setText(String.valueOf(createTime));

        List<Map<String, String>> articles = (List<Map<String, String>>) map.get("Articles");
        if (articles != null) {
            Element Articles = rootElement.addElement("Articles");
            for (Map<String, String> article : articles) {
                Element item = Articles.addElement("item");
                for (Map.Entry<String, String> m : article.entrySet()) {
                    item.addElement(m.getKey()).setText(String.format(cdata, m.getValue()));
                }
            }

        }


        String xml = HtmlUtils.htmlUnescape(document.asXML());
        return xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "").replace("\n", ""); //将document文档对象直接转换成字符串输出

    }

}