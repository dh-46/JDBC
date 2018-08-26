package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 �R���y�k
 * 	
 * 
 */

public class JDBC06 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	�z�Lproperties��Ƶ��c�Ӧs���ݩʸ�� => �u�ʤ�@�G�ا�j
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		// String del = "delete from cust where id = 3";
		// String del = "delete from cust where name = 'Ben'";
		String del = "delete from cust";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);	// �o�ӬO�d�߻y�k ���i��insert
			stmt.execute(del);	// �o�Ӥ~�O����
			
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
