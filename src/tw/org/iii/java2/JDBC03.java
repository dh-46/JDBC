package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*	20180826PM1 getConnection V2
 * 	
 * 	���Ǧ��A���|�O������ʪ��s��, �ҥH�n�����A�����S��.
 * 
 */

public class JDBC03 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		String user = "root";
		String passwd = "root";
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('Tom', '123456', '2000-12-25')";
		
		try {
			// �إ߳s�u (�z�L�r��) �ĤG��  getConnection(String url, String user, String password)
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);	// �o�ӬO�d�߻y�k ���i��insert
			stmt.execute(insert);	// �o�Ӥ~�O����
			
			
			conn.close();
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
