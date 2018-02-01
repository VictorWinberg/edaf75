package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Show {

	public final String movieTitle;
	public final String theaterName;
	public final String date;
	public final String freeSeats;

	public Show(ResultSet rs) throws SQLException {
		this.movieTitle = rs.getString("movie_title");
		this.theaterName = rs.getString("theater_name");
		this.date = rs.getString("date");
		this.freeSeats = rs.getString("free_seats");
	}

	@Override
	public String toString() {
		return movieTitle + " @ " + theaterName + " | " + date;
	}
}
