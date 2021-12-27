package com.example.login.util;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CookieUtil {
    private static final String COOKIE_DOMAIN = "localhost";
    private static final String COOKIE_NAME = "top_ideas_token";

    /**
     * extract cookie
     * @param request
     * @return
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for (Cookie cookie : cks){
                log.info("read cookieName:{}, cookieValue:{}",cookie.getName(),cookie.getValue());
                if (StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{}, cookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * assign cookie
     * @param response
     * @param token
     */
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/"); //代表根目录，根目录以下的代码和页面可以获取到cookie

        //单位秒，如果不设置maxage，cookie就不会写入硬盘，而是写入内存，只在当前页面有效
        cookie.setMaxAge(10);
        log.info("write cookieName:{}, cookieValue:{}", cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * delete token
     * @param request
     * @param response
     */
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for (Cookie cookie : cks){
                if (StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true); //无法用脚本访问cookie。当然不能全面防止，但可以提高安全性
                    cookie.setMaxAge(0);//设置为0，代表删除此cookie
                    log.info("del cookieName:{}, cookieValue:{}", cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }
}
