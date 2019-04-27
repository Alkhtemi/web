/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import models.Menu;





/**
 *
 * @author Iuri
 */
public class MenuServlet extends BaseServlet {
    

   //good practice to declare the template that is populated as a constant, why?
    //declare your template here
    private static final String MENU_TEMPLATE = "menu.mustache";
    //servlet can be serialized
    private static final long serialVersionUID = 687117339002032958L;
    Menu m;

    public MenuServlet()  {
   

    }

    //right now, setting the data for the page by hand, later that comes from a data store
    //helper method to create a MessageBoard object, which provides the data shown on the message board page
    private Object getObject() {
       
      
        return m;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showView(response, MENU_TEMPLATE, getObject());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String topic = request.getParameter("topic");
        LOG.info("###new topic: " + topic);
       
        response.sendRedirect("/messages");
    }
}
