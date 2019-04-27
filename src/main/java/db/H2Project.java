package db;

import model.Project;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class H2Project implements AutoCloseable {

    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(H2Project.class);

    public static final String MEMORY = "jdbc:h2:mem:shop";
    public static final String FILE = "jdbc:h2:~/testdb";

    private Connection connection;

    static Connection getConnection(String db) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");  // ensure the driver class is loaded when the DriverManager looks for an installed class. Idiom.
        return DriverManager.getConnection(db, "iuri", "");  // default password, ok for embedded.
    }

    public H2Project() {
        this(FILE);
    }

    public H2Project(String db) {
        try {
            connection = getConnection(db);
            loadResource("/projects.sql");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProject(Project project){
        final String ADD_PROJECT_QUERY = "INSERT INTO project (nameProject) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(ADD_PROJECT_QUERY)) {
            ps.setString(1, project.getNameProject());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProject(Project project){

        final String DELETE_PROJECT_QUERY = "DELETE project (nameProject) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(DELETE_PROJECT_QUERY)) {
            ps.setString(1, project.getNameProject());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Project> findProjects() {
        final String LIST_PROJECTS_QUERY = "SELECT nameProject  FROM project";
        List<Project> out = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(LIST_PROJECTS_QUERY)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(new Project(rs.getString(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    private void loadResource(String name) {
        try {
            String cmd = new Scanner(getClass().getResource(name).openStream()).useDelimiter("\\Z").next();
            PreparedStatement ps = connection.prepareStatement(cmd);
            ps.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }




}
