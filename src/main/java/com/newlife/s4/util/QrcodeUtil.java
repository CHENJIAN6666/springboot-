package com.newlife.s4.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * 描述:生成二维码
 *
 * @author withqianqian@163.com
 * @create 2018-07-05 11:32
 */
public class QrcodeUtil {
    private static Logger logger = LoggerFactory.getLogger(QrcodeUtil.class);

    private static final int QRCOLOR = 0xFF000000;   //默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

    private static final String QR_SUFFIX = "png";
    //二维码路径
    private static final String QR_PATH = "file/qr/";
    //海报路径
    private static final String POSTER_PATH = "file/poster/";

    /**
     * 生成二维码并使用Base64编码
     *
     * @param content
     * @param width
     * @param height
     * @return base64 img
     */
    public static String getBase64QRCode(String content, int width, int height) {
        byte[] bytes = null;
        BitMatrix bitMatrix = createQRCode(content, width, height);
        if (bitMatrix != null) {
            BufferedImage image = toBufferedImage(bitMatrix);
            //注意此处拿到字节数据
            bytes = imageToBytes(image, QR_SUFFIX);
        }
        logger.debug("==============={}===============", "生成二维码成功");
        //Base64编码
        return bytes != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes) : "";
    }

    /**
     * 生成二维码并使用Base64编码 默认长宽高 300 * 300
     *
     * @param content
     * @return base64 img
     */
    public static String getBase64QRCode(String content) {
        return getBase64QRCode(content, 300, 300);
    }

    /**
     * 生成二维码并保存
     *
     * @param relativelyPath
     * @param content        二维码内容
     * @return String 保存路径
     */
    public static String getQRCode(String content, String relativelyPath) {
        if (!relativelyPath.endsWith("/")) {
            relativelyPath += "/";
        }
        String qrName = MD5Util.MD5(content) + "." + QR_SUFFIX;
        String qrPath = relativelyPath + QR_PATH;
        FileOutputStream outputStream = null;
        File img = new File(qrPath + qrName);
        if (!img.getParentFile().exists()) {
            img.mkdirs();
        }
        if (!img.exists()) {
            BitMatrix bitMatrix = createQRCode(content, 300, 300);
            if (bitMatrix != null) {
                try {
                    outputStream = new FileOutputStream(qrPath + qrName);
                    MatrixToImageWriter.writeToStream(bitMatrix, QR_SUFFIX, outputStream);
                    logger.debug("==============={} {}===============", "生成二维码成功:" + qrPath + qrName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return qrPath + qrName;
    }

    /**
     * 生成带Logo二维码 默认300x300
     *
     * @param content
     * @param logoPath
     * @param relativelyPath
     * @return 绝对路径
     */
    public static String createQRCodeWithLogo(String content, String logoPath, String relativelyPath) {
        return createQRCodeWithLogo(content, 300, 300, logoPath, relativelyPath);
    }

    /**
     * 生成带Logo二维码
     *
     * @param content
     * @param width
     * @param height
     * @param logoPath
     * @param relativelyPath
     * @return 绝对路径
     */
    public static String createQRCodeWithLogo(String content, int width, int height, String logoPath, String relativelyPath) {
        String dis = MD5Util.MD5(content + logoPath) + "." + QR_SUFFIX;
        BitMatrix bitMatrix = createQRCode(content, width, height);
        if (bitMatrix != null) {
            BufferedImage qrcode = toBufferedImage(bitMatrix);
            //注意此处拿到字节数据
            BufferedImage logo = null;
            try {
                logo = ImageIO.read(new File(logoPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int widthLogo = logo.getWidth(null) > qrcode.getWidth() * 2 / 10 ? (qrcode.getWidth() * 2 / 10) : logo.getWidth(null);
            int heightLogo = logo.getHeight(null) > qrcode.getHeight() * 2 / 10 ? (qrcode.getHeight() * 2 / 10) : logo.getWidth(null);

            int x = (qrcode.getWidth() - widthLogo) / 2;
            int y = (qrcode.getHeight() - heightLogo) / 2;

            Graphics2D g = (Graphics2D) qrcode.getGraphics();
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);

            g.dispose();
            try {
                if (!relativelyPath.endsWith("/")) {
                    relativelyPath += "/";
                }
                File disQrCode = new File(relativelyPath + QR_PATH + dis);
                if (!disQrCode.getParentFile().exists()) {
                    disQrCode.getParentFile().mkdirs();
                }
                ImageIO.write(qrcode, QR_SUFFIX, disQrCode);
                return QR_PATH + dis;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 生成带Logo二维码
     *
     * @param content
     * @param width
     * @param height
     * @param logoPath
     * @param relativelyPath
     * @return 绝对路径
     */
    public static void createQRCodeWithLogo(String content, int width, int height, String logoPath, String relativelyPath, OutputStream outputStream) {
        String dis = MD5Util.MD5(content + logoPath) + "." + QR_SUFFIX;
        BitMatrix bitMatrix = createQRCode(content, width, height);
        if (bitMatrix != null) {
            BufferedImage qrcode = toBufferedImage(bitMatrix);
            //注意此处拿到字节数据
            BufferedImage logo = null;
            try {
                logo = ImageIO.read(new File(logoPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int widthLogo = qrcode.getWidth() / 4;
            int heightLogo = qrcode.getHeight() / 4;

            int x = (qrcode.getWidth() - widthLogo) / 2;
            int y = (qrcode.getHeight() - heightLogo) / 2;

            Graphics2D g = (Graphics2D) qrcode.getGraphics();
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);

            g.dispose();
            try {
                if (!relativelyPath.endsWith("/")) {
                    relativelyPath += "/";
                }
//                File disQrCode = new File(relativelyPath + QR_PATH + dis);
//                if (!disQrCode.getParentFile().exists()) {
//                    disQrCode.getParentFile().mkdirs();
//                }
                ImageIO.write(qrcode, QR_SUFFIX, outputStream);
//                return new FileOutputStream(disQrCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        return null ;
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    private static BitMatrix createQRCode(String content, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        Map hints = new HashMap();

        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 0);//设置0-4之间
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        //设置编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //画二维码
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

    private static BitMatrix deleteWhite(BitMatrix bitMatrix){
        int[] rec = bitMatrix.getEnclosingRectangle();

        int resWidth = rec[2] + 1;

        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);

        resMatrix.clear();

        for (int i = 0; i < resWidth; i++) {

            for (int j = 0; j < resHeight; j++) {

                if (bitMatrix.get(i + rec[0], j + rec[1])) {

                    resMatrix.set(i, j);

                }

            }

        }
        return resMatrix;
    }

    private static byte[] imageToBytes(BufferedImage image, String type) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] base64 = null;
        try {
            ImageIO.write(image, type, out);
            base64 = out.toByteArray();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64;
    }

    private static BufferedImage toBufferedImage(BitMatrix bm) {
        int w = bm.getWidth();
        int h = bm.getHeight();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        return image;
    }

    /**
     * 创建海报
     * {
     *     backgroundPath:模版路径
     *     avatarPath:头像路径
     *     qrCodePath:二维码路径
     *     realPath:request.getSession().getServletContext().getRealPath("/")
     *     text:文字
     * }
     *
     * @return
     */
    public static String createPoster(Map<String, String> contentMap) throws IOException {
        logger.debug("createPoster map:{}",contentMap);
        String backgroundPath = contentMap.get("backgroundPath");
        String relativelyPath = contentMap.get("realPath");
        if(!relativelyPath.endsWith("/")){
            relativelyPath += "/";
        }
        String avatarPath = verifyAvatar(contentMap.get("avatarPath"), relativelyPath);
        //下载失败重复下载
        if (org.apache.commons.lang3.StringUtils.isEmpty(avatarPath))
            avatarPath = verifyAvatar(contentMap.get("avatarPath"), relativelyPath);
        if (org.apache.commons.lang3.StringUtils.isEmpty(avatarPath))
            return "";
        logger.debug("下载头像地址：{}",avatarPath);
        String qrCodePath = contentMap.get("qrCodePath");

        //BufferedImage
        BufferedImage backgroundPic = ImageIO.read(new File(backgroundPath));
        BufferedImage avatar = ImageIO.read(new File(avatarPath));
        BufferedImage qrCode = ImageIO.read(new File(qrCodePath));

        //绘制画布
        Graphics2D canvas = (Graphics2D) backgroundPic.getGraphics();

        //头像定位
        int avatarWidth = backgroundPic.getWidth() / 6;
        int avatarHeight = backgroundPic.getWidth() / 6;
        int x0 =  40;
        int y0 = 15;

        //将头像切成圆角
        BufferedImage avatar2 = getRoundAvatar(avatar, avatarWidth, avatarHeight);

        //二维码定位
        int qrCodeWidth = backgroundPic.getWidth() / 7 * 4;
        int qrCodeHeight = backgroundPic.getWidth() / 7 * 4;
        int x1 = (backgroundPic.getWidth() - qrCodeWidth) / 2;
        int y1 = (backgroundPic.getHeight() - qrCodeHeight) / 4;

        //绘制二维码边框颜色
        canvas.setColor(new Color(45, 125, 255));//画笔颜色
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        canvas.setStroke(stroke);
        canvas.drawRect(x1 - 5, y1 - 5, qrCodeWidth + 10, qrCodeHeight + 10);

        //绘制头像
        canvas.drawImage(qrCode, x1, y1, qrCodeWidth, qrCodeHeight, null);
        //绘制二维码
        canvas.drawImage(avatar2, x0, y0, avatarWidth, avatarHeight, null);
        //设置文字
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        String name = "我是" + contentMap.get("text");
        String name2 = "这是一个不错的公众号，我推荐你关注一下！";
        canvas.setColor(Color.white);//画笔颜色
        canvas.setFont(new Font("宋体", Font.BOLD, 30));
        canvas.drawString(name, 180, 60);
        canvas.setFont(new Font("宋体", Font.BOLD, 24));
        canvas.drawString(name2, 180, 100);

        canvas.dispose();

        String posterName = MD5Util.MD5(contentMap.get("text")+"_"+backgroundPath) + "." + QR_SUFFIX;

        File disQrCode = new File(relativelyPath + POSTER_PATH + posterName);
        if (!disQrCode.getParentFile().exists()) {
            disQrCode.getParentFile().mkdirs();
        }
        ImageIO.write(backgroundPic, QR_SUFFIX, new FileOutputStream(disQrCode));
        return relativelyPath + POSTER_PATH + posterName;
    }

    /**
     * 创建海报
     * {
     *     backgroundPath:模版路径
     *     qrCodePath:二维码路径
     *     realPath:request.getSession().getServletContext().getRealPath("/")
     *     text:文字
     * }
     *
     * @return
     */
    public static String createPosterWithoutAvatar(Map<String, String> contentMap) throws IOException {
        String backgroundPath = contentMap.get("backgroundPath");
        String relativelyPath = contentMap.get("realPath");
        if(!relativelyPath.endsWith("/")){
            relativelyPath += "/";
        }
        String qrCodePath = contentMap.get("qrCodePath");

        //BufferedImage
        BufferedImage backgroundPic = ImageIO.read(new File(backgroundPath));
        BufferedImage qrCode = ImageIO.read(new File(qrCodePath));
        //绘制画布
        Graphics2D canvas = (Graphics2D) backgroundPic.getGraphics();
        //二维码定位
        int qrCodeWidth = backgroundPic.getWidth() / 7*4;
        int qrCodeHeight = backgroundPic.getWidth() / 7*4;
        int x1 = (backgroundPic.getWidth() - qrCodeWidth) / 2;
        int y1 = (backgroundPic.getHeight() - qrCodeHeight) / 2+20;
        //绘制头像
        canvas.drawImage(qrCode, x1, y1, qrCodeWidth, qrCodeHeight, null);
        Long index = Long.valueOf(contentMap.get("index"));
        String num = "000";
        if(index<10)
            num += index;
        else if(index<100)
            num = "00"+index;
        else if(index<1000)
            num = "0"+index;
        else
            num = ""+index;
        canvas.setColor(Color.WHITE);//画笔颜色
        canvas.setFont(new Font("宋体", Font.BOLD, 20));
        canvas.drawString(num, backgroundPic.getWidth()-60, backgroundPic.getHeight()-10);
        String posterName = MD5Util.MD5(num) + "." + QR_SUFFIX;
        File disQrCode = new File(relativelyPath + POSTER_PATH + posterName);
        if (!disQrCode.getParentFile().exists()) {
            disQrCode.getParentFile().mkdirs();
        }
        ImageIO.write(backgroundPic, QR_SUFFIX, new FileOutputStream(disQrCode));
        return relativelyPath + POSTER_PATH + posterName;
    }
    /**
     * 创建海报
     * {
     *     backgroundPath:模版路径
     *     qrCodePath:二维码路径
     *     realPath:request.getSession().getServletContext().getRealPath("/")
     *     text:文字
     * }
     *
     * @return
     */
    public static String createPosterWithoutAvatarFull(Map<String, String> contentMap) throws IOException {
        String backgroundPath = contentMap.get("fullBackgroundPath");
        String relativelyPath = contentMap.get("realPath");
        if(!relativelyPath.endsWith("/")){
            relativelyPath += "/";
        }
        String qrCodePath = contentMap.get("qrCodePath");

        //BufferedImage
        BufferedImage backgroundPic = ImageIO.read(new File(backgroundPath));
        BufferedImage qrCode = ImageIO.read(new File(qrCodePath));
        //绘制画布
        Graphics2D canvas = (Graphics2D) backgroundPic.getGraphics();
        //二维码定位
        int qrCodeWidth = 240 ;
        int qrCodeHeight = 240;
        int x1 = 80;
        int y1 = (backgroundPic.getHeight() - qrCodeHeight) / 2+25;
        //绘制头像
        canvas.drawImage(qrCode, x1, y1, qrCodeWidth, qrCodeHeight, null);
        canvas.drawImage(qrCode, x1*6-20, y1, qrCodeWidth, qrCodeHeight, null);
        Long index = Long.valueOf(contentMap.get("index"));
        String num = "000";
        if(index<10)
            num += index;
        else if(index<100)
            num = "00"+index;
        else if(index<1000)
            num = "0"+index;
        else
            num = ""+index;
        canvas.setColor(Color.WHITE);//画笔颜色
        canvas.setFont(new Font("宋体", Font.BOLD, 20));
        canvas.drawString(num, backgroundPic.getWidth()/2-60, backgroundPic.getHeight()-10);
        canvas.drawString(num, backgroundPic.getWidth()-60, backgroundPic.getHeight()-10);
        String posterName = "FF_" + MD5Util.MD5(num) + "." + QR_SUFFIX;
        File disQrCode = new File(relativelyPath + POSTER_PATH + posterName);
        if (!disQrCode.getParentFile().exists()) {
            disQrCode.getParentFile().mkdirs();
        }
        ImageIO.write(backgroundPic, QR_SUFFIX, new FileOutputStream(disQrCode));
        return relativelyPath + POSTER_PATH + posterName;
    }

    /**
     * 将头像切成圆角
     *
     * @param avatar
     * @param avatarWidth
     * @param avatarHeight
     * @return
     */
    private static BufferedImage getRoundAvatar(BufferedImage avatar, int avatarWidth, int avatarHeight) {
        BufferedImage avatar2 = new BufferedImage(avatarWidth, avatarHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = avatar2.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, avatarWidth, avatarHeight, 120, 120));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(avatar, 0, 0, avatarWidth, avatarHeight, null);
        g2.dispose();
        return avatar2;
    }

    /**
     * 校验头像
     *
     * @param avatarPath
     * @param relativelyPath
     * @return 下载图片路径
     */
    private static String verifyAvatar(String avatarPath, String relativelyPath) {
        if (!relativelyPath.endsWith("/")) {
            relativelyPath += "/";
        }
        int index = avatarPath.indexOf("file");
        if (index > -1) {
            return relativelyPath + avatarPath.substring(index);
        }
        if(avatarPath.indexOf("thirdwx") == -1)
            return avatarPath;
        return relativelyPath + HttpUtil.downLoad(avatarPath, relativelyPath);

    }

    public static void main(String[] args) throws IOException {
////        createQRCodeWithLogo("http://www.baidu.com", 600, 600, "d:/test/414x414.png", "d:/test/");
//        Map<String, String> map = Maps.newHashMap();
//        map.put("backgroundPath", "D:/test/img_3.jpg");
////        map.put("avatarPath", "D:/qr/avar.jpg");
//        map.put("qrCodePath", "D:\\test\\file\\qr\\20BEA1FFAFC0CDC59B1E5AF87407C221.png");
//        map.put("realPath", "D:\\test\\");
//        map.put("index", "0");
////        map.put("text", "张大伟");
//        System.out.println(createPosterWithoutAvatarFull(map));
////        System.out.println(getBase64QRCode("weixin://wxpay/bizpayurl?pr=4rG3Fbk"));
        Map<String,String> map = new HashMap<>();
        map.put("backgroundPath", "D:/pic/c.png");
        map.put("avatarPath", "D:/pic/b.jpg");
        map.put("qrCodePath", "D:\\pic\\file\\qr\\a.png");
        map.put("realPath", "D:\\pic\\");

        map.put("text", "ITFK");

        map.put("avatarWidth", "108");
        map.put("avatarHeight", "108");
        map.put("avatarX", "27");
        map.put("avatarY", "897");

        map.put("qrcodeWidth", "200");
        map.put("qrcodeHeight", "200");
        map.put("qrcodeX", "442");
        map.put("qrcodeY", "868");

        map.put("fontSize", "27");
        map.put("font", "xxx");
        map.put("fontColor", "1d2a5a");
       createPosterByXY(map);


    }

    /**
     * 根据坐标创建海报
     * {
     *     backgroundPath:模版路径
     *     avatarPath:头像路径
     *     qrCodePath:二维码路径
     *     realPath:request.getSession().getServletContext().getRealPath("/")
     *     text:文字
     * }
     *
     * @return
     */
    public static String createPosterByXY(Map<String, String> contentMap) throws IOException {
        logger.debug("createPoster map:{}",contentMap);
        String backgroundPath = contentMap.get("backgroundPath");
        String relativelyPath = contentMap.get("realPath");
        if(!relativelyPath.endsWith("/")){
            relativelyPath += "/";
        }
        String avatarPath = verifyAvatar(contentMap.get("avatarPath"), relativelyPath);
        //下载失败重复下载
        if (org.apache.commons.lang3.StringUtils.isEmpty(avatarPath))
            avatarPath = verifyAvatar(contentMap.get("avatarPath"), relativelyPath);
        if (org.apache.commons.lang3.StringUtils.isEmpty(avatarPath))
            return "";
        logger.debug("下载头像地址：{}",avatarPath);
        String qrCodePath = contentMap.get("qrCodePath");

        //BufferedImage
        BufferedImage backgroundPic = ImageIO.read(new File(backgroundPath));
        BufferedImage avatar = ImageIO.read(new File(avatarPath));
        BufferedImage qrCode = ImageIO.read(new File(qrCodePath));

        //绘制画布
        Graphics2D canvas = (Graphics2D) backgroundPic.getGraphics();

        //头像定位
        int avatarWidth =Integer.parseInt(contentMap.get("avatarWidth"));
        int avatarHeight = Integer.parseInt(contentMap.get("avatarHeight"));
        int x0 =  Integer.parseInt(contentMap.get("avatarX"));
        int y0 = Integer.parseInt(contentMap.get("avatarY"));

        //缩放头像
        avatar = zoomImage(avatar,avatarWidth,avatarHeight);

        //将头像切成圆角
        BufferedImage avatar2 = getRoundAvatar(avatar, avatarWidth, avatarHeight);

        //二维码定位
        int qrCodeWidth =Integer.parseInt(contentMap.get("qrcodeWidth"));
        int qrCodeHeight = Integer.parseInt(contentMap.get("qrcodeHeight"));
        int x1 = Integer.parseInt(contentMap.get("qrcodeX"));
        int y1 = Integer.parseInt(contentMap.get("qrcodeY"));



        //绘制头像
        canvas.drawImage(qrCode, x1, y1, qrCodeWidth, qrCodeHeight, null);
        //绘制二维码
        canvas.drawImage(avatar2, x0, y0, avatarWidth, avatarHeight, null);
        //设置文字
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        String name = "我是" + contentMap.get("text");

        String name2 = "这是一个不错的公众号，我推荐你关注一下！";//默认文案
        String name3 = "";

        //更改文案
        if(!StringTools.isNullOrEmpty(contentMap.get("font"))){
            name2 = contentMap.get("font");
        }

        if(name2.length()>11){
            name3 = name2.substring(11);
            name2 = name2.substring(0,11);
        }

        int fontSize = 26 ;//默认字体大小
        String fontColor = "000000";//默认字体颜色


        //更改文字大小
        if(!StringTools.isNullOrEmpty(contentMap.get("fontSize"))){
            fontSize = Integer.parseInt(contentMap.get("fontSize"));
        }

        if(!StringTools.isNullOrEmpty(contentMap.get("fontColor"))){
            fontColor = contentMap.get("fontColor");
        }

        canvas.setColor(ColorUtil.toColorFromString(fontColor));//画笔颜色
        canvas.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
        canvas.drawString(name, avatarWidth +50, y0+ avatarHeight/4);
        canvas.drawString(name2, avatarWidth+50, y0+ avatarHeight/4+ avatarHeight/4 +10);
        canvas.drawString(name3, avatarWidth+50, y0+ avatarHeight/4+ avatarHeight/4 + avatarHeight/4 +20);
        canvas.dispose();

        String posterName = MD5Util.MD5(contentMap.get("text") + "_" + backgroundPath + System.currentTimeMillis()) + "." + QR_SUFFIX;

        File disQrCode = new File(relativelyPath + POSTER_PATH + posterName);
        if (!disQrCode.getParentFile().exists()) {
            disQrCode.getParentFile().mkdirs();
        }
        ImageIO.write(backgroundPic, QR_SUFFIX, new FileOutputStream(disQrCode));
        return relativelyPath + POSTER_PATH + posterName;
    }

    /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     *
     */
    public static BufferedImage zoomImage(BufferedImage bufImg, int w, int h)  {

        double wr=0,hr=0;
        Image itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);//设置缩放目标图片模板

        wr=w*1.0/bufImg.getWidth();     //获取缩放比例
        hr=h*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        itemp = ato.filter(bufImg, null);
        return  (BufferedImage)itemp;
    }







}
