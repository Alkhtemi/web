/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import lombok.Data;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import java.util.List;
import db.H2Milestone;
import model.Milestones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.MustacheRenderer;


/**
 *
 * @author Iuri
 */
public class MilestoneServlet extends HttpServlet {

    static final Logger LOG = LoggerFactory.getLogger(MilestoneServlet.class);

    private final H2Milestone h2Milestone;
    private final MustacheRenderer mustache;
    public MilestoneServlet(H2Milestone h2Milestone) {
        mustache = new MustacheRenderer();
        this.h2Milestone = h2Milestone;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Milestones> milestones = h2Milestone.findMilestones();
        String html = mustache.render("milestone.mustache", new Result(milestones.size()));
        response.setContentType("text/html");
        response.setStatus(200);
        response.getOutputStream().write(html.getBytes(Charset.forName("utf-8")));
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameMilestone = request.getParameter("name");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        String finishDate = request.getParameter("finishDate");
        Milestones milestones = new Milestones(nameMilestone, description, dueDate, finishDate);
        h2Milestone.addMilestone(milestones);
        response.sendRedirect("/index.html");
    }

    @Data
    class Result {
        private int count;
        Result(int count) { this.count = count; }
    }

}
