// Copyright (c) 2018 Cilogi. All Rights Reserved.
//
// File:        LoginServlet.java
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


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



public class LoginServlet extends BaseServlet{
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    
      private final H2User h2User;
    private final MustacheRenderer mustache;
    public LoginServlet(H2User h2User) {
        mustache = new MustacheRenderer();
        this.h2User = h2User;
    }


    private final String LOGIN_TEMPLATE = "login.mustache";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = UserFuncs.getCurrentUser(request);
        showView(response, LOGIN_TEMPLATE, userName);
    }
    
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String emailInput = request.getParameter("email");
        if (h2User.Login(emailInput) == true) {
               UserFuncs.setCurrentUser(request, emailInput);
            String targetURL = UserFuncs.getLoginRedirect(request);
            response.sendRedirect(response.encodeRedirectURL(targetURL));
          
            
         
        }
        // do nothing, we stay on the page,
        // could also display a warning message by passing parameter to /login on redirect
    }
    
    
    }
    

