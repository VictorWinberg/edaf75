package db;

import java.sql.*;

public class User {

	public final String username;
	public final String name;
	public final String address;
	public final String phone;

	public User(ResultSet rs) throws SQLException {
		this.username = rs.getString("username");
		this.name = rs.getString("name");
		this.address = rs.getString("address");
		this.phone = rs.getString("phone");
	}

	@Override
	public String toString() {
		return username + " | " + name;
	}
}
