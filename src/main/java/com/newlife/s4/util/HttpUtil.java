package com.newlife.s4.util;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.newlife.s4.common.constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

/**
 * Http工具类
 */

@SuppressWarnings("deprecation")
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static final String GET = "GET";

    public static final String POST = "POST";

    /**
     * 将 Object 转换成 QueryString 格式，例如：a=1&b=2&c=3&d=4
     *
     * @param obj
     * @return
     */
    public static String convertQueryString(Object obj) {
        try {
            JSONObject jsonObj = (JSONObject) JSONObject.toJSON(obj);

            if (jsonObj != null) {
                StringBuilder stringBuilder = new StringBuilder();

                for (String key : jsonObj.keySet()) {
                    stringBuilder.append(String.format("&%s=%s", key, jsonObj.get(key).toString()));
                }

                return stringBuilder.substring(1).toString();
            }
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * 是否是IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIP(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * @param domain
     * @return
     */
    public static String httpDns(String domain) {
        if (isIP(domain)) {
            return domain;
        }

        return httpRequestString("http://119.29.29.29/d?dn=" + domain, "GET", null);
    }

    /**
     * 获取客户端请求的 IP地址
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 判断是否为Ajax请求
     *
     * @param request HttpServletRequest
     * @return 是true, 否false
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    public static String httpRequestString(String requestUrl, String requestMethod, String paramData) {
        return httpRequestString(requestUrl, requestMethod, paramData, null, null);
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    public static String httpRequestString(String requestUrl, String requestMethod, String paramData, String charset) {
        return httpRequestString(requestUrl, requestMethod, paramData, charset, charset);
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    public static String httpRequestString(String requestUrl, String requestMethod, String paramData, String inCharset,
                                           String outCharset) {
        if (requestUrl.startsWith("https://")) {
            return httpsRequestString(requestUrl, requestMethod, paramData, inCharset, outCharset);
        }

        String response = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setRequestProperty("Content-Type", "application/json");

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            response = buffer.toString();
        } catch (ConnectException ce) {
            log.error("http server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:{}", e);
        }

        return response;
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    private static String httpsRequestString(String requestUrl, String requestMethod, String paramData) {
        return httpsRequestString(requestUrl, requestMethod, paramData, null, null);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    private static String httpsRequestString(String requestUrl, String requestMethod, String paramData,
                                             String charset) {
        return httpsRequestString(requestUrl, requestMethod, paramData, charset, charset);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回响应的String
     */
    private static String httpsRequestString(String requestUrl, String requestMethod, String paramData,
                                             String inCharset, String outCharset) {
        String response = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            response = buffer.toString();
        } catch (ConnectException ce) {
            log.error("https server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return response;
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequestJsonObject(String requestUrl, String requestMethod, String paramData) {
        return httpRequestJsonObject(requestUrl, requestMethod, paramData, "", "");
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequestJsonObject(String requestUrl, String requestMethod, String paramData,
                                                   String charset) {
        return httpRequestJsonObject(requestUrl, requestMethod, paramData, charset, charset);
    }

    /**
     * 发起http请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequestJsonObject(String requestUrl, String requestMethod, String paramData,
                                                   String inCharset, String outCharset) {
        if (requestUrl.startsWith("https://")) {
            return httpsRequest(requestUrl, requestMethod, paramData, inCharset, outCharset);
        }

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Content-Type", "application/json");

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("http server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestJsonObject(String requestUrl, String requestMethod, String paramData,
                                              Class<T> clazz) {
        return httpRequestJsonObject(requestUrl, requestMethod, paramData, clazz, null, null);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestJsonObject(String requestUrl, String requestMethod, String paramData, Class<T> clazz,
                                              String charset) {
        return httpRequestJsonObject(requestUrl, requestMethod, paramData, clazz, charset);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestJsonObject(String requestUrl, String requestMethod, String paramData, Class<T> clazz,
                                              String inCharset, String outCharset) {

        if (requestUrl.startsWith("https://")) {
            return httpsRequest(requestUrl, requestMethod, paramData, clazz, inCharset, outCharset);
        }

        T jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Content-Type", "application/json");

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            if (clazz != null) {
                String respString = buffer.toString();
                if (respString != null && respString.startsWith("<")) {
                    jsonObject = XmlUtil.parseObject(respString, clazz);
                } else {
                    jsonObject = JSONObject.parseObject(respString, clazz);
                }
            }
        } catch (ConnectException ce) {
            log.error("http server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    private static JSONObject httpsRequest(String requestUrl, String requestMethod, String paramData, String inCharset,
                                           String outCharset) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("https server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestObject(String requestUrl, String requestMethod, String paramData, Class<T> clazz) {
        return httpRequestObject(requestUrl, requestMethod, paramData, clazz, null, null);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestObject(String requestUrl, String requestMethod, String paramData, Class<T> clazz,
                                          String charset) {
        return httpRequestObject(requestUrl, requestMethod, paramData, clazz, charset);
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpRequestObject(String requestUrl, String requestMethod, String paramData, Class<T> clazz,
                                          String inCharset, String outCharset) {

        if (requestUrl.startsWith("https://")) {
            return httpsRequest(requestUrl, requestMethod, paramData, clazz, inCharset, outCharset);
        }

        T jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            if (clazz != null) {
                String respString = buffer.toString();
                if (respString != null && respString.startsWith("<")) {
                    jsonObject = XmlUtil.parseObject(respString, clazz);
                } else {
                    jsonObject = JSONObject.parseObject(respString, clazz);
                }
            }
        } catch (ConnectException ce) {
            log.error("http server connection timed out.");
        } catch (Exception e) {
            log.error("http request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(不带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    private static <T> T httpsRequest(String requestUrl, String requestMethod, String paramData, Class<T> clazz,
                                      String inCharset, String outCharset) {
        T jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            if (clazz != null) {
                String respString = buffer.toString();
                if (respString != null && respString.startsWith("<")) {
                    jsonObject = XmlUtil.parseObject(respString, clazz);
                } else {
                    jsonObject = JSONObject.parseObject(respString, clazz);
                }
            }
        } catch (ConnectException ce) {
            log.error("https server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return 返回的响应String
     */
    public static String httpRequestStringWithSSLCert(String requestUrl, String requestMethod, String paramData,
                                                      String sslCertFile, String sslCertPassword) {
        String response = null;
        StringBuffer buffer = new StringBuffer();

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(sslCertFile));
            try {
                ks.load(instream, sslCertPassword.toCharArray());
            } finally {
                instream.close();
            }
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(ks, sslCertPassword.toCharArray()).build();

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(sslContext.getSocketFactory());
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Content-Type", "application/json");

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes("utf8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            response = buffer.toString();
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return response;
    }

    /**
     * 发起https请求并获取结果(带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequestWithSSLCert(String requestUrl, String requestMethod, String paramData,
                                                     String sslCertPath, String sslCertPassword) {
        return httpsRequestWithSSLCert(requestUrl, requestMethod, paramData, sslCertPath, sslCertPassword, null, null);
    }

    /**
     * 发起https请求并获取结果(带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequestWithSSLCert(String requestUrl, String requestMethod, String paramData,
                                                     String sslCertPath, String sslCertPassword, String charset) {
        return httpsRequestWithSSLCert(requestUrl, requestMethod, paramData, sslCertPath, sslCertPassword, charset,
                charset);
    }

    /**
     * 发起https请求并获取结果(带SSL请求证书)
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequestWithSSLCert(String requestUrl, String requestMethod, String paramData,
                                                     String sslCertPath, String sslCertPassword, String inCharset, String outCharset) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            if (!StringTools.isNullOrEmpty(sslCertPath)) {
                String sslKeySotrePath = sslCertPath.substring(0, sslCertPath.lastIndexOf("\\.") + 1) + ".keystore";
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                FileInputStream is = new FileInputStream(sslKeySotrePath);
                ks.load(is, sslCertPassword.toCharArray());
                is.close();
                KeyManagerFactory keyManagerFactory = KeyManagerFactory
                        .getInstance(KeyManagerFactory.getDefaultAlgorithm());// Sunx509
                keyManagerFactory.init(ks, sslCertPassword.toCharArray());
                sslContext.init(keyManagerFactory.getKeyManagers(), tm, new java.security.SecureRandom());
            } else {
                sslContext.init(null, tm, new java.security.SecureRandom());
            }

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes(inCharset));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, outCharset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("https server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 发起https请求并获取结果(带SSL请求证书)
     *
     * @param <T>
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param paramData     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static <T> T httpsRequestWithSSLCert(String requestUrl, String requestMethod, String paramData,
                                                Class<T> clazz, String sslCertPath, String sslCertPassword) {
        T jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            if (!StringTools.isNullOrEmpty(sslCertPath)) {
                String sslKeySotrePath = sslCertPath.substring(0, sslCertPath.lastIndexOf("\\.") + 1) + ".keystore";
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                FileInputStream is = new FileInputStream(sslKeySotrePath);
                ks.load(is, sslCertPassword.toCharArray());
                is.close();
                KeyManagerFactory keyManagerFactory = KeyManagerFactory
                        .getInstance(KeyManagerFactory.getDefaultAlgorithm());// Sunx509
                keyManagerFactory.init(ks, sslCertPassword.toCharArray());
                sslContext.init(keyManagerFactory.getKeyManagers(), tm, new java.security.SecureRandom());
            } else {
                sslContext.init(null, tm, new java.security.SecureRandom());
            }

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();

            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestProperty("Content-Type", "application/json");

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时
            if (null != paramData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(paramData.getBytes("utf8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            if (clazz != null) {
                String respString = buffer.toString();
                if (respString != null && respString.startsWith("<")) {
                    jsonObject = XmlUtil.parseObject(respString, clazz);
                } else {
                    jsonObject = JSONObject.parseObject(respString, clazz);
                }
            }
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 上传图片
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    public static JSONObject httpRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap) {

        if (requestUrl != null && requestUrl.startsWith("https://")) {
            return httpsRequestWithFile(requestUrl, paramData, fileMap);
        }

        JSONObject res = null;
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------" + System.currentTimeMillis(); // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (!StringTools.isNullOrEmpty(paramData)) {
                StringBuffer strBuf = new StringBuffer();
                for (String param : paramData.split("\\&")) {
                    String[] formDatas = param.split("\\=");
                    String inputName = formDatas[0];
                    String inputValue = formDatas[1];
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes("utf8"));
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    MagicMatch match = Magic.getMagicMatch(file, false, true);
                    String contentType = match.getMimeType();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes("utf8"));

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = JSONObject.parseObject(strBuf.toString());
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + requestUrl);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * 上传图片
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    public static <T> T httpRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap,
                                            Class<T> clazz) {
        return httpRequestWithFile(requestUrl, paramData, fileMap, clazz, null, null);
    }

    /**
     * 上传图片
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    public static <T> T httpRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap,
                                            Class<T> clazz, String charset) {
        return httpRequestWithFile(requestUrl, paramData, fileMap, clazz, charset, charset);
    }

    /**
     * 上传图片
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    public static <T> T httpRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap,
                                            Class<T> clazz, String inCharset, String outCharset) {

        if (requestUrl != null && requestUrl.startsWith("https://")) {
            return httpsRequestWithFile(requestUrl, paramData, fileMap, clazz, inCharset, outCharset);
        }

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        T res = null;
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------" + System.currentTimeMillis(); // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(requestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (!StringTools.isNullOrEmpty(paramData)) {
                StringBuffer strBuf = new StringBuffer();
                for (String param : paramData.split("\\&")) {
                    String[] formDatas = param.split("\\=");
                    String inputName = formDatas[0];
                    String inputValue = formDatas[1];
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes(inCharset));
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    MagicMatch match = Magic.getMagicMatch(file, false, true);
                    String contentType = match.getMimeType();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes(inCharset));

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(inCharset);
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), outCharset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }

            if (clazz != null) {
                String respString = strBuf.toString();
                if (respString != null && respString.startsWith("<")) {
                    res = XmlUtil.parseObject(respString, clazz);
                } else {
                    res = JSONObject.parseObject(respString, clazz);
                }
            }

            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + requestUrl);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * 上传图片(HTTPS)
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    private static JSONObject httpsRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap) {
        JSONObject res = null;
        HttpsURLConnection conn = null;
        String BOUNDARY = "---------------------------" + System.currentTimeMillis(); // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (!StringTools.isNullOrEmpty(paramData)) {
                StringBuffer strBuf = new StringBuffer();
                for (String param : paramData.split("\\&")) {
                    String[] formDatas = param.split("\\=");
                    String inputName = formDatas[0];
                    String inputValue = formDatas[1];
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes("utf8"));
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    MagicMatch match = Magic.getMagicMatch(file, false, true);
                    String contentType = match.getMimeType();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes("utf8"));

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = JSONObject.parseObject(strBuf.toString());
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + requestUrl);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * 上传图片(HTTPS)
     *
     * @param requestUrl
     * @param paramData
     * @param fileMap
     * @return
     */
    private static <T> T httpsRequestWithFile(String requestUrl, String paramData, Map<String, String> fileMap,
                                              Class<T> clazz, String inCharset, String outCharset) {

        if (StringTools.isNullOrEmpty(inCharset)) {
            inCharset = "utf8";
        }

        if (StringTools.isNullOrEmpty(outCharset)) {
            outCharset = "utf8";
        }

        T res = null;
        HttpsURLConnection conn = null;
        String BOUNDARY = "---------------------------" + System.currentTimeMillis(); // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (!StringTools.isNullOrEmpty(paramData)) {
                StringBuffer strBuf = new StringBuffer();
                for (String param : paramData.split("\\&")) {
                    String[] formDatas = param.split("\\=");
                    String inputName = formDatas[0];
                    String inputValue = formDatas[1];
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes(inCharset));
            }

            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    MagicMatch match = Magic.getMagicMatch(file, false, true);
                    String contentType = match.getMimeType();

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes(inCharset));

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(inCharset);
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), outCharset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }

            if (clazz != null) {
                String respString = strBuf.toString();
                if (respString != null && respString.startsWith("<")) {
                    res = XmlUtil.parseObject(respString, clazz);
                } else {
                    res = JSONObject.parseObject(respString, clazz);
                }
            }

            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + requestUrl);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * 图片下载
     *
     * @param requestUrl 请求地址
     * @return 返回保存路径
     */
    public static String downLoad(String requestUrl, String relativelyPath) {
        if(StringUtils.isEmpty(requestUrl))
            return "";
        if (!relativelyPath.endsWith("/")) {
            relativelyPath += "/";
        }
        String number = String.valueOf(Math.floor(Math.random() * 6) + 1).substring(0, 1);
        String imgPath = Constants.UPLOAD_WEB_FILE + "/member/m" + number + "/";
        String imgName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        OutputStream output = null;
        InputStream input = null;
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            imgPath = imgPath + imgName;
            File file = new File(relativelyPath + imgPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            output = new BufferedOutputStream(new FileOutputStream(file), 2048);
            // 将返回的输入流转换成字符串
            input = con.getInputStream();
            byte[] buffer = new byte[2048];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            con.disconnect();
        } catch (ConnectException ce) {
            log.error("https server connection timed out.");
            imgPath = "";
        } catch (Exception e) {
            log.error("https request error:{}", e);
            imgPath = "";
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return imgPath;
    }

}
