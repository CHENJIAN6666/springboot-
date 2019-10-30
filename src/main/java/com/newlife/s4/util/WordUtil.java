package com.newlife.s4.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class WordUtil {
//    private static Logger logger = LoggerFactory.getLogger(WordUtil.class);

    /**
     *
     * @param tempFile 模板文件，应放在classpath目录下
     * @param model 数据
     * @return
     */
    public static ByteArrayOutputStream render(String tempFile, HashMap<String,Object> model) throws IOException{
        ByteArrayOutputStream out = null;
        XWPFTemplate template = null;
        try {
            template = XWPFTemplate.compile(
                    new ClassPathResource(tempFile).getInputStream()).render(model);
            out = new ByteArrayOutputStream();
            template.write(out);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }finally {
            if(out != null){
               out.close();
            }
            if(template != null){
                try {
                    template.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return out;
    }

//    public static void main(String[] args) throws IOException {
//        HashMap<String,Object> data = new HashMap<>();
//        data.put("feature", new NumbericRenderData(new ArrayList<TextRenderData>() {
//            {
//                add(new TextRenderData("Plug-in grammar"));
//                add(new TextRenderData("Supports word text, header..."));
//                add(new TextRenderData("Not just templates, but also style templates"));
//            }
//        }));
//        final ByteArrayOutputStream render = render("template/test.docx", data);
//        Files.write(new ClassPathResource("template/fff.docx").getFile().toPath(),render.toByteArray());
//    }
}
