package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 getConnection V3 �s�u�覡
 * 	getConnection(String url, Properties info)
 * 		=> properties 
 * 			https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
 * 		=> �u�ʧ�j, �ۭq�ݩʳ]�w
 * 	Properties
 * 		Map�[�c
 * 		
 * 	���Ǧ��A���|�O������ʪ��s��, �ҥH�n�����A�����S��.
 * 
 */

public class JDBC04 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	�z�Lproperties��Ƶ��c�Ӧs���ݩʸ�� => �u�ʤ�@�G�ا�j
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('Ben', '232323', '2018-01-01')";
		
		try {
			// �إ߳s�u (�z�L�r��) �ĤG��  getConnection(String url, String user, String password)
			Connection conn = DriverManager.getConnection(url, prop);
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
