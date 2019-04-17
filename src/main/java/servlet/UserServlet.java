/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;


import lombok.Data;
import db.H2User;
import model.User;
import util.MustacheRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class UserServlet  extends HttpServlet{
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(UserServlet.class);

    private final H2User h2User;
    private final MustacheRenderer mustache;
    public UserServlet(H2User h2User) {
        mustache = new MustacheRenderer();
        this.h2User = h2User;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = h2User.findUsers();
        String html = mustache.render("register.mustache", new Result(users.size()));
        response.setContentType("text/html");
        response.setStatus(200);
        response.getOutputStream().write(html.getBytes(Charset.forName("utf-8")));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = new User(firstName, lastName, email, password);
        h2User.addUser(user);
        response.sendRedirect("/index.html");
    }

    @Data
    class Result {
        private int count;
        Result(int count) { this.count = count; }
    }

    }



    

