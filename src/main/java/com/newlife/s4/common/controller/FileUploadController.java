package com.newlife.s4.common.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newlife.s4.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.newlife.s4.common.constants.Constants;
import com.newlife.s4.common.constants.ErrorEnum;
import com.newlife.s4.config.exception.CommonJsonException;
import com.newlife.s4.config.storage.FileStorageConfig;
import com.newlife.s4.util.CommonUtil;
import com.newlife.s4.util.FileUtil;
import com.newlife.s4.util.MD5Util;


/**
 * 图片上传公用 接口
 *
 * @author newlife
 * @description 上传图片
 * @date 2018/3/28
 */

@RestController
@RequestMapping("/public")
public class FileUploadController {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    @PostMapping("/fileUpload")
    public JSONObject fileRecord(@RequestParam(value = "files", required = true) MultipartFile[] multipartFile,
                                 @RequestParam(value = "category", required = false) String category,
                                 HttpServletRequest request) throws IOException {
        try {
            /** 得到图片保存目录的真实路径 **/
            String realPathDir = "";
            //realPathDir = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();
            //realPathDir = Thread.currentThread().getContextClassLoader()..getResource("uploadFile");
            realPathDir = request.getSession().getServletContext().getRealPath("/");

            if (!realPathDir.endsWith("/")) {
                realPathDir += "/";
            }

            System.out.println(realPathDir);
            //System.out.println("文件上传数:"+multipartFile.length);
            /** 根据真实路径创建目录 **/
            String fileStorePath = Constants.UPLOAD_WEB_FILE;
            fileStorePath = fileStorePath + "/" + category;

            System.out.println(realPathDir + fileStorePath);
            File saveFile = new File(realPathDir + fileStorePath);

            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }

            List<JSONObject> storedMap = new ArrayList<JSONObject>();
            for (MultipartFile item : multipartFile) {
                JSONObject temp = storeFile(item, realPathDir, fileStorePath);
                if (temp != null) {
                    storedMap.add(temp);
                }
            }

            //logger.info("上传信息：" + JSONObject.toJSONString(storedMap));

            if (storedMap.size() > 0) {
                return CommonUtil.successPage(storedMap);
            } else {

                JSONObject jsonObject = new JSONObject();
                jsonObject.clear();
                jsonObject.put("returnCode", ErrorEnum.E_90004.getErrorCode());
                jsonObject.put("returnMsg", ErrorEnum.E_90004.getErrorMsg());
                jsonObject.put("returnData", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        } catch (Exception e) {
            //logger.error("When store the image occur exception.", e);

            JSONObject jsonObject = new JSONObject();
            jsonObject.clear();
            jsonObject.put("returnCode", ErrorEnum.E_90004.getErrorCode());
            jsonObject.put("returnMsg", ErrorEnum.E_90004.getErrorMsg());
            jsonObject.put("returnData", new JSONObject());
            throw new CommonJsonException(jsonObject);
        }
    }

    //    @RequiresPermissions("carColor:add")
    @PostMapping("/uploadimage")
    public JSONObject uploadimage(@RequestParam(value = "upfile", required = true) MultipartFile[] multipartFile,
                                  @RequestParam(value = "category", required = false) String category,
                                  HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {
            //** 得到图片保存目录的真实路径 **//*
            String realPathDir = "";
            //realPathDir = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();
            //realPathDir = Thread.currentThread().getContextClassLoader()..getResource("uploadFile");
            realPathDir = request.getSession().getServletContext().getRealPath("/");

            if (!realPathDir.endsWith("/")) {
                realPathDir += "/";
            }

            System.out.println(realPathDir);
            //System.out.println("文件上传数:"+multipartFile.length);
            //** 根据真实路径创建目录 **//*
            String fileStorePath = Constants.UPLOAD_WEB_FILE;
            fileStorePath = fileStorePath + "/" + category;

            File saveFile = new File(realPathDir + fileStorePath);

            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            List<JSONObject> storedMap = new ArrayList<JSONObject>();
            for (MultipartFile item : multipartFile) {
                JSONObject temp = storeFile2(item, realPathDir, fileStorePath);
                if (temp != null) {
                    return temp;
                    //storedMap.add(temp);
                }
            }

            //logger.info("上传信息：" + JSONObject.toJSONString(storedMap));

            if (storedMap.size() > 0) {
                return CommonUtil.successPage(storedMap);
            } else {

                JSONObject jsonObject = new JSONObject();
                jsonObject.clear();
                jsonObject.put("returnCode", ErrorEnum.E_90004.getErrorCode());
                jsonObject.put("returnMsg", ErrorEnum.E_90004.getErrorMsg());
                jsonObject.put("returnData", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        } catch (Exception e) {
            //logger.error("When store the image occur exception.", e);

            JSONObject jsonObject = new JSONObject();
            jsonObject.clear();
            jsonObject.put("returnCode", ErrorEnum.E_90004.getErrorCode());
            jsonObject.put("returnMsg", ErrorEnum.E_90004.getErrorMsg());
            jsonObject.put("returnData", new JSONObject());
            throw new CommonJsonException(jsonObject);
        }
    }

    /**
     * @param multipartFile
     * @param realPathDir   物理路径
     * @param storePath     ：相对路径
     * @return
     * @throws IOException
     */
    protected JSONObject storeFile(MultipartFile multipartFile, String realPathDir, String storePath) {

        JSONObject map = new JSONObject();

        try {
            byte[] fileBytes = FileUtil.inputStream2byte(multipartFile.getInputStream());
            //String suffix = this.getTypeByStream(fileBytes);
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

            /** 拼成完整的文件保存路径加文件 **/
            String name = MD5Util.MD5(fileBytes) + suffix;

            String fileName = storePath + "/" + name;
            //String comprefileName = storePath + "/" + "compre" + name;

            InputStream inputStream = FileUtil.byte2inputStream(fileBytes);
            System.out.println(realPathDir + fileName);
            FileUtil.inputStream2File(inputStream, realPathDir + fileName);

            saveFile(fileBytes, realPathDir + fileName);
            //saveImage(fileBytes, realPathDir + comprefileName);
            //this.compressPic(realPathDir + comprefileName, realPathDir + comprefileName);

            InputStream inputStream2 = FileUtil.byte2inputStream(fileBytes);
            //Double scale = getScale(inputStream2);
            map.put("name", name);
            map.put("url", fileStorageConfig.getFileStorageAddress() + fileName);
            map.put("status", "finished");
            //map.put("scale",scale);
            //map.put("thumbPicUrl",fileName);
            //map.put("thumbScale",fileName);

            return map;
        } catch (Exception ex) {
            //logger.error("上传图片文件发生故障", ex);
            return null;
        }
    }

    protected JSONObject storeFile2(MultipartFile multipartFile, String realPathDir, String storePath) {

        JSONObject map = new JSONObject();
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] fileBytes = FileUtil.inputStream2byte(inputStream);
            String suffix = this.getTypeByStream(fileBytes);

            /** 拼成完整的文件保存路径加文件 **/
            String name = MD5Util.MD5(fileBytes) + suffix;

            String fileName = storePath + "/" + name;
            File file = new File(realPathDir + fileName);
            if(!file.getParentFile().exists()){
                file.mkdirs();
            }
            Files.copy(multipartFile.getInputStream(),file.toPath(),StandardCopyOption.REPLACE_EXISTING);
            compressPic2(realPathDir + fileName, realPathDir + "/qu/" + fileName);

            map.put("state", "SUCCESS");
            //map.put("url", "/" + fileName);
            map.put("title", name);
            map.put("original", name);
            map.put("name", name);
            map.put("url", fileStorageConfig.getFileStorageAddress() + fileName);
            map.put("status", "finished");
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            //logger.error("上传图片文件发生故障", ex);
            return null;
        }
    }

    /**
     * 根据文件流读取图片文件真实类型
     *
     * @param fileTypeByte
     * @return
     */
    public String getTypeByStream(byte[] fileTypeByte) {

        String type = bytesToHexString(fileTypeByte).toUpperCase();
        if (type.contains("FFD8FF")) {
            return ".jpg";
        } else if (type.contains("89504E47")) {
            return ".png";
        } else if (type.contains("47494638")) {
            return ".gif";
        } else if (type.contains("49492A00")) {
            return ".tif";
        } else if (type.contains("424D")) {
            return ".bmp";
        } else {
            return "";
        }
    }


    /**
     * byte数组转换成16进制字符串
     *
     * @param src add by sgh
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 对字符串md5加密(大写+数字)
     *
     * @param s 传入要加密的字符串
     * @return MD5加密后的字符串
     */

    public String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要  
            mdInst.update(btInput);
            // 获得密文  
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void saveFile(byte[] bytes, String comprefileName) throws IOException {
        // 复制图片
        InputStream inputStream = FileUtil.byte2inputStream(bytes);
        FileUtil.inputStream2File(inputStream, comprefileName);
    }


    public void compressPic(String srcFilePath, String descFilePath) throws IOException {
        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;

        // 指定写图片的方式为 jpg
        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality((float) 0.3);
        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
        ColorModel colorModel = ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
        // imgWriteParams.setDestinationType(new
        // javax.imageio.ImageTypeSpecifier(
        // colorModel, colorModel.createCompatibleSampleModel(16, 16)));
        imgWriteParams.setDestinationType(
                new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

        try {
            if (isBlank(srcFilePath)) {
            } else {
                file = new File(srcFilePath);
                src = ImageIO.read(file);
                out = new FileOutputStream(descFilePath);

                imgWrier.reset();
                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
                // OutputStream构造
                imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                // 调用write方法，就可以向输入流写图片
                imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片质量压缩
     *
     * @param srcFilePath
     * @param descFilePath
     */
    public void compressPic2(String srcFilePath, String descFilePath) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(srcFilePath);
            long size = in.getChannel().size();
            System.out.println("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        File file = new File(descFilePath);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        resize(srcFilePath, descFilePath, 0, 0);
    }

    /**
     * 图片缩放
     *
     * @param src
     * @param to
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static boolean resize(String src, String to, int newWidth, int newHeight) {
        try {
            File srcFile = new File(src);
            File toFile = new File(to);
            BufferedImage img = ImageIO.read(srcFile);
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            if (newWidth == 0 && newHeight == 0) {
                g.drawImage(img, 0, 0, null);
            } else {
                g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, w, h, null);
            }
            g.dispose();
            ImageIO.write(dimg, "jpg", toFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.trim().equals("")) {
        }
        return false;
    }


    public Double getScale(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedImage bufferedImg = ImageIO.read(fis);
        int imgWidth = bufferedImg.getWidth();
        int imgHeight = bufferedImg.getHeight();
        Double scale = (imgWidth + 0.0) / imgHeight;
        BigDecimal bg = new BigDecimal(scale);
        scale = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return scale;
    }


    public Double getScale(InputStream fis) throws IOException {
        BufferedImage bufferedImg = ImageIO.read(fis);
        int imgWidth = bufferedImg.getWidth();
        int imgHeight = bufferedImg.getHeight();
        Double scale = (imgWidth + 0.0) / imgHeight;
        BigDecimal bg = new BigDecimal(scale);
        scale = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return scale;
    }


    @RequestMapping(value = "/ueditor")
    public String ueditor(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        String config = "";
        String callbackName = request.getParameter("callback");
        if (null != callbackName && !"".equals(callbackName)) {
            config = "/* 前后端通信相关的配置,注释只允许使用多行方式 */\n" +
                    callbackName + "({\n" +
                    "    /* 上传图片配置项 */\n" +
                    "    \"imageActionName\": \"uploadimage\", /* 执行上传图片的action名称 */\n" +
                    "    \"imageFieldName\": \"upfile\", /* 提交的图片表单名称 */\n" +
                    "    \"imageMaxSize\": 2048000, /* 上传大小限制，单位B */\n" +
                    "    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 上传图片格式显示 */\n" +
                    "    \"imageCompressEnable\": true, /* 是否压缩图片,默认是true */\n" +
                    "    \"imageCompressBorder\": 1600, /* 图片压缩最长边限制 */\n" +
                    "    \"imageInsertAlign\": \"none\", /* 插入的图片浮动方式 */\n" +
//		                "    \"imageUrlPrefix\": \"http://"+request.getServerName()+":"+request.getServerPort()+"\", /* 图片访问路径前缀 */\n" +
//		                "    \"imageUrlPrefix\": \"http://"+request.getServerName()+":9090\", /* 图片访问路径前缀 */\n" +
                    "    \"imageUrlPrefix\": \"" + fileStorageConfig.getFileStorageAddress() + "\", /* 图片访问路径前缀 */\n" +
                    "    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +

                    "})";
        } else {
            config = "/* 前后端通信相关的配置,注释只允许使用多行方式 */\n" +
                    "{\n" +
                    "    /* 上传图片配置项 */\n" +
                    "    \"imageActionName\": \"uploadimage\", /* 执行上传图片的action名称 */\n" +
                    "    \"imageFieldName\": \"upfile\", /* 提交的图片表单名称 */\n" +
                    "    \"imageMaxSize\": 2048000, /* 上传大小限制，单位B */\n" +
                    "    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 上传图片格式显示 */\n" +
                    "    \"imageCompressEnable\": false, /* 是否压缩图片,默认是true */\n" +
                    "    \"imageCompressBorder\": 1600, /* 图片压缩最长边限制 */\n" +
                    "    \"imageInsertAlign\": \"none\", /* 插入的图片浮动方式 */\n" +
//		                "    \"imageUrlPrefix\": \"http://"+request.getServerName()+":"+request.getServerPort()+"\", /* 图片访问路径前缀 */\n" +
//		                "    \"imageUrlPrefix\": \"http://"+request.getServerName()+":9090\", /* 图片访问路径前缀 */\n" +
                    "    \"imageUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +

                    "}";
        }

        return config;

    }

    public JSONObject getUploadImageUrl(JSONObject jsonObject) {
        if( !StringTools.isNullOrEmpty(jsonObject.get("url"))){
            JSONObject result = new JSONObject();
            result.put("url",jsonObject.get("url"));
            return  CommonUtil.successData(result);
        }
        return CommonUtil.successData("1","上传图片失败");
    }
}
