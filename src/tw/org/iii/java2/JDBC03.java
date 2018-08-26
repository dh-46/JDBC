package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*	20180826PM1 getConnection V2
 * 	
 * 	有些伺服器會保持持續性的連接, 所以要抓到伺服器的特性.
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
			// 建立連線 (透過字串) 第二招  getConnection(String url, String user, String password)
			Connection conn = DriverManager.getConnection(url, user, passwd);
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);	// 這個是查詢語法 不可用insert
			stmt.execute(insert);	// 這個才是執行
			
			
			conn.close();
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
