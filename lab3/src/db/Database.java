package db;

import java.sql.*;
import java.util.*;

/**
 * Database is an interface to the college application database, it
 * uses JDBC to connect to a SQLite3 file.
 */
public class Database {

    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the
     * database is performed later.
     */
    public Database() {
        conn = null;
    }

    /**
     * Opens a connection to the database, using the specified
     * filename (if we'd used a traditional DBMS, such as PostgreSQL
     * or MariaDB, we would have specified username and password
     * instead).
     */
    public boolean openConnection(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection to the database has been established
     * 
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }

    public boolean hasUser(String username) {
        User user = null;
        String query =
            "SELECT username, name, address, phone\n" +
            "FROM   users\n" +
            "WHERE  username = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return user != null;
    }

    public List<String> getMovies() {
        List<String> movies = new LinkedList<String>();
        String query = "SELECT * FROM movies";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                movies.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<String> getDates(String movieName) {
        List<String> dates = new LinkedList<String>();
        String query =
            "SELECT date FROM shows\n" +
			"WHERE movie = ?\n" +
            "ORDER BY date";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movieName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public Show getShow(String movieName, String date) {
        Show show = null;
        String query =
            "SELECT * FROM shows\n" +
            "WHERE movie = ? AND date = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movieName);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show = new Show(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return show;
    }

    /*
    public List<...> ...(...) {
        List<...> found = new LinkedList<>();
        String query =
            "SELECT  ...\n" +
            "FROM    ...\n" +
            "...\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ...);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(new ...(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }
    */
}
