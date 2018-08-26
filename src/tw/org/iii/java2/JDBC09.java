package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM2 �d�ߥy�k
 * 	
 * 
 */

public class JDBC09 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	�z�Lproperties��Ƶ��c�Ӧs���ݩʸ�� => �u�ʤ�@�G�ا�j
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		// String query = "select name, id, tel, birthday from cust";
		String query = "insert into cust (name, tel, birthday)  values (?,?,?)";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "Mary"+(int)(Math.random()*50));
			pstmt.setString(2, "121212");
			pstmt.setString(3, "2008-08-08");
			
			
			int result = pstmt.executeUpdate();
			//	�^�Ǧ^�Ӫ��ȥN����N��?
			
			System.out.println("OK" + result);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
