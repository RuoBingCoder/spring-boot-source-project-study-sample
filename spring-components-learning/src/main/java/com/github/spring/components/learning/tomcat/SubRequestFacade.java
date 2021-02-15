package com.github.spring.components.learning.tomcat;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.core.ApplicationFilterChain;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jianlei.shi
 * @date 2021/2/8 10:23 上午
 * @description tomcat 处理 request
 */

public class SubRequestFacade extends RequestFacade {
    /**
     * Construct a wrapper for the specified request.
     *
     * @param request The request to be wrapped
     * @see HttpServlet
     * HttpServletRequest ->RequestFacade
     *
     */
    public SubRequestFacade(Request request) {
        super(request);
    }

    static class SimpleServlet extends HttpServlet {

        /**
         * 做得到
         *
         * @param req  要求的事情
         * @param resp 分别地
         * @return
         * @author jianlei.shi
         * @date 2021-02-08 10:41:33
         * @see //StandardWrapperValve#invoke(Request, Response)
         * @see ApplicationFilterChain#doFilter(ServletRequest, ServletResponse) 请求拦截器链
         * @see HttpServlet#service(HttpServletRequest, HttpServletResponse) 真正请求处理方法逻辑
         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doGet(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
        }
    }
}
