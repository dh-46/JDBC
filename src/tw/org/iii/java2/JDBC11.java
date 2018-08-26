package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM2 PreparedStatement UPDATE
 * 	
 * 
 */

public class JDBC11 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		// String query = "select name, id, tel, birthday from cust";
		String query = "UPDATE `cust` SET `tel` = ? WHERE name like ?";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "000000");
			pstmt.setString(2, "Mary%");
			
			
			int result = pstmt.executeUpdate();
			//	for update
			
			System.out.println("OK" + result);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
