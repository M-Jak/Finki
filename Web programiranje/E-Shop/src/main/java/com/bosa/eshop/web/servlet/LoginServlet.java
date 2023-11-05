package com.bosa.eshop.web.servlet;

import com.bosa.eshop.model.User;
import com.bosa.eshop.model.exception.InvalidUserCredentialsException;
import com.bosa.eshop.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(urlPatterns="/login")
@AllArgsConstructor
public class LoginServlet extends HttpServlet {
    private final AuthService authService;
    private final SpringTemplateEngine templateEngine;


    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req,resp);
        WebContext context = new WebContext(webExchange);
        templateEngine.process("login.html",context,resp.getWriter());
    }
    @Override
    protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = null;
        try{
            user = authService.login(username,password);
        } catch( InvalidUserCredentialsException e){
            IWebExchange webExchange = JakartaServletWebApplication
                    .buildApplication(getServletContext())
                    .buildExchange(req,resp);
            WebContext context = new WebContext(webExchange);
            context.setVariable("invalidCredentials",e.getMessage());
            templateEngine.process("login.html",context,resp.getWriter());
            resp.sendRedirect("/login");
        }
        req.getSession().setAttribute("user",user);
        resp.sendRedirect("/");
    }
}
