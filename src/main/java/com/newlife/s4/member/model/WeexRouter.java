package com.newlife.s4.member.model;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述: 微信端路由
 *
 * @author withqianqian@163.com
 * @create 2018-10-22 15:22
 */
public class WeexRouter {

    private static Map<String, PageView> router = Maps.newHashMap();

    public void putAndParams(String shorthand, String path) {
        PageView pageView = new PageView(shorthand, path);
        router.put(shorthand, pageView);

    }

    public void putAndParams(String shorthand, String path, String[] params) {
        PageView pageView = new PageView(shorthand, path, params);
        router.put(shorthand, pageView);
    }

    public String getAndSpellURL(String key, HttpServletRequest request) {
        PageView pageView = router.get(key);
        if(pageView == null)
            return "";
        String[] params = pageView.getParams();//参数
        String url = pageView.getUri();//路径
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (String param : params) {
                //获取全部参数
                if ("All".equals(param)) {
                    if (url.indexOf("?") > -1)
                        sb.append("&");
                    else
                        sb.append("?");
                    sb.append(request.getQueryString());
                    break;
                } else {

                    if(StringUtils.isNotEmpty(request.getParameter(param))){
                        if (url.indexOf("?") == -1) {
                            url += "?";
                        }
                        sb.append(param + "=" + request.getParameter(param) + "&");
                    }

                }
            }
            String str = sb.toString();
            if (str.endsWith("&")) {
                str.substring(0, str.length() - 1);
            }
            url += str;
        }
        return url;
    }

    private class PageView {
        String shorthand;
        String uri;
        String[] params;

        public String getShorthand() {
            return shorthand;
        }

        public String getUri() {
            return uri;
        }

        public String[] getParams() {
            return params;
        }

        public PageView(String shorthand, String uri) {
            this.shorthand = shorthand;
            this.uri = uri;
        }

        public PageView(String shorthand, String uri, String[] params) {
            this.shorthand = shorthand;
            this.uri = uri;
            this.params = params;
        }
    }

}
