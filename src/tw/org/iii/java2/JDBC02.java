package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*	20180826PM1 
 * 	
 * 	���Ǧ��A���|�O������ʪ��s��, �ҥH�n�����A�����S��.
 * 
 */

public class JDBC02 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii?user=root&password=root"; 
		// �w�]port �i�ٲ� (:3306) / iii (��Ʈw�W)
		// port���n�`�N�O�_���T
		// https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-url-format.html
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('John', '123456', '1999-10-10')";
		
		try {
			// �إ߳s�u (�z�L�r��) �Ĥ@��
			Connection conn = DriverManager.getConnection(url);
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
