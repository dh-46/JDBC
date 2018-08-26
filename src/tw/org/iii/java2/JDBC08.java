package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 �d�ߥy�k
 * 	
 * 
 */

public class JDBC08 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	�z�Lproperties��Ƶ��c�Ӧs���ݩʸ�� => �u�ʤ�@�G�ا�j
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		// String del = "delete from cust where id = 3";
		// String del = "delete from cust where name = 'Ben'";
		String query = "select * from cust";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);	// �o�ӬO�d�߻y�k ���i��insert
			
			while (rs.next()) {
				String f1 = rs.getString(1);
				String f2 = rs.getString(2);
				String f3 = rs.getString(3);
				String f4 = rs.getString(4);
				System.out.println(f1 + ":" + f2 + ":" + f3 + ":" + f4);
			}
			
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
