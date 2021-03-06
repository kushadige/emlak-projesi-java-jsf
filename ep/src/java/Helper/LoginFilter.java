/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import Model.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kushadige
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String url = req.getRequestURI();
        
        User u = (User) req.getSession().getAttribute("valid_user");
        
        if(u == null) {
            if( url.contains("admin") || url.contains("logout") || url.contains("home") || url.contains("ilanver") || url.contains("ilanlarim") || url.contains("favoriler")){
                res.sendRedirect(req.getContextPath()+"/login.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if( url.contains("register") || url.contains("login") || url.contains("index") ) {
                res.sendRedirect(req.getContextPath()+"/home.xhtml");
            } else if( url.contains("logout") ) {
                req.getSession().invalidate();
                res.sendRedirect(req.getContextPath()+"/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

}
