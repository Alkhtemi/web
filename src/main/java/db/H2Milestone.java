package db;

import model.Milestones;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.MilestoneServlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class H2Milestone implements AutoCloseable {

    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(H2Milestone.class);

    public static final String MEMORY = "jdbc:h2:mem:shop";
    public static final String FILE = "jdbc:h2:~/testdb";

    private Connection connection;

    static Connection getConnection(String db) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");  // ensure the driver class is loaded when the DriverManager looks for an installed class. Idiom.
        return DriverManager.getConnection(db, "iuri", "");  // default password, ok for embedded.
    }

    private String email;

    public H2Milestone() {
        this(FILE);
    }

    public H2Milestone(String db) {
        try {
            connection = getConnection(db);
            loadResource("/milestones.sql");
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

    public void addMilestone(Milestones milestones) {

        final String ADD_MILESTONES_QUERY = "INSERT INTO milestones (nameMilestone, description, dueDate, finishDate) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(ADD_MILESTONES_QUERY)) {
            ps.setString(1, milestones.getNameMilestone());
            ps.setString(2, milestones.getDescription());
            ps.setString(3, milestones.getDueDate());
            ps.setString(4, milestones.getFinishDate());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProject(Milestones milestones){

        final String DELETE_PROJECT_QUERY = "DELETE milestones (nameMilestone) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(DELETE_PROJECT_QUERY)) {
            ps.setString(1, milestones.getNameMilestone());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Milestones> findMilestones() {
        final String LIST_MILESTONES_QUERY = "SELECT nameMilestone  FROM milestones";
        List<Milestones> out = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(LIST_MILESTONES_QUERY)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(new Milestones(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public void editMilestone(Milestones milestones){

        final String EDIT_MILESTONE_QUERY = "ALTER milestones (nameMilestone, description, dueDate, finishDate) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(EDIT_MILESTONE_QUERY)) {
            ps.setString(1, milestones.getNameMilestone());
            ps.setString(2, milestones.getDescription());
            ps.setString(3, milestones.getDueDate());
            ps.setString(4, milestones.getFinishDate());
           ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

