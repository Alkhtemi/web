package servlet;

import lombok.Data;
import model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import db.H2Project;
import util.MustacheRenderer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 *get and setters milestones
 * @K
 */

public class ProjectServlet extends HttpServlet {
    static final Logger LOG = LoggerFactory.getLogger(ProjectServlet.class);

    private final H2Project h2Project;
    private final MustacheRenderer mustache;
    public ProjectServlet(H2Project h2Project) {
        mustache = new MustacheRenderer();
        this.h2Project = h2Project;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projects = h2Project.findProjects();
        String html = mustache.render("project.mustache", new Result(projects.size()));
        response.setContentType("text/html");
        response.setStatus(200);
        response.getOutputStream().write(html.getBytes(Charset.forName("utf-8")));
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nameProject = request.getParameter("name");
        Project projects = new Project(nameProject);
        h2Project.addProject(projects);
        response.sendRedirect("/index.html");
    }

    @Data
    class Result {
        private int count;
        Result(int count) { this.count = count; }
    }
}
