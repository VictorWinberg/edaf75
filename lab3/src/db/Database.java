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
        User user = getUser(username);
        return user != null;
    }

    public User getUser(String username) {
        User user = null;
        String query =
            "SELECT * FROM users\n" +
            "WHERE  username = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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

    public List<String> getDates(String movieTitle) {
        List<String> dates = new LinkedList<String>();
        String query =
            "SELECT date FROM shows\n" +
			"WHERE movie_title = ?\n" +
            "ORDER BY date\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movieTitle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public Show getShow(String movieTitle, String date) {
        Show show = null;
        String query =
            "SELECT movie_title, theater_name, date,\n" +
            "       seats - COUNT(reservation_id) free_seats\n" +
            "FROM shows\n" +
            "JOIN theaters\n" +
            "ON theater_name = theaters.name\n" +
            "LEFT JOIN reservations\n" +
            "USING (movie_title, date)\n" +
            "WHERE movie_title = ? AND date = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movieTitle);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                show = new Show(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return show;
    }

    public int makeReservation(String username, String movieTitle, String date) {
        int reservation_id = -1;

        // Check if there are free seats
        String query =
            "SELECT seats - COUNT(reservation_id) free_seats\n" +
            "FROM   shows\n" +
            "JOIN   theaters\n" +
            "ON     theaters.name = theater_name\n" +
            "LEFT JOIN reservations\n" +
            "USING  (movie_title, date)\n" +
            "WHERE  movie_title = ? AND date = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movieTitle);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int free_seats = rs.getInt("free_seats");
                if (free_seats <= 0) {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        // Make the reservation
        query =
            "INSERT INTO reservations(username, movie_title, date)\n" +
            "VALUES (?, ?, ?)\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, movieTitle);
            ps.setString(3, date);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                reservation_id = rs.getInt("last_insert_rowid()");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return reservation_id;
    }
}
