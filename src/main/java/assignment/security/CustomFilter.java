package assignment.security;

import assignment.model.User;
import assignment.service.impl.AssignmentServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by hbilici on 2017-03-01.
 */
public class CustomFilter implements Filter {

    private static String[] publicEndpoints = {"/register", "/login"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;


        if (!Arrays.asList(publicEndpoints).contains(httpRequest.getServletPath())) {
            try {
                String token = httpRequest.getHeader("Token");
                Jwt.parseToken(token);
                String usernameFromToken = Jwt.usernameFromToken(token);
                User u = AssignmentServiceImpl.getRegisteredUserMap().get(usernameFromToken);
                Authentication authentication = new UsernamePasswordAuthenticationToken(u, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
//                String responseToken = Jwt.generateToken(u);
//                httpResponse.setHeader("Token", responseToken);

                // continue thru the filter chain
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception e) {
                SecurityContextHolder.getContext().setAuthentication(null);
                servletRequest.setAttribute("authError", e.getMessage());

                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }


    @Override
    public void destroy() {

    }

}
