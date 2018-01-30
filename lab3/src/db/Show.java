package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Show {

	public final String movieName;
	public final String theater;
	public final String date;
	public final String free_seats;

	public Show(ResultSet rs) throws SQLException {
		this.movieName = rs.getString("movie");
		this.theater = rs.getString("theater");
		this.date = rs.getString("date");
		this.free_seats = rs.getString("free_seats");
	}

	@Override
	public String toString() {
		return movieName + " @ " + theater + " | " + date;
	}
}
