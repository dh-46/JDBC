package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*	20180826PM1 建立連線
 * 	
 * 	有些伺服器會保持持續性的連接, 所以要抓到伺服器的特性.
 * 
 */

public class JDBC02 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii?user=root&password=root"; 
		// 預設port 可省略 (:3306) / iii (資料庫名)
		// port號要注意是否正確
		// https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-url-format.html
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('John', '123456', '1999-10-10')";
		
		try {
			// 建立連線 (透過字串) 第一招
			Connection conn = DriverManager.getConnection(url);
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
