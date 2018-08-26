package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 getConnection V4 連線方式 (自動關閉)
 * 	getConnection(String url, Properties info)
 * 		=> properties 
 * 			https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
 * 		=> 彈性更大, 自訂屬性設定
 * 	Properties
 * 		Map架構
 * 		
 * 	有些伺服器會保持持續性的連接, 所以要抓到伺服器的特性.
 * 
 */

public class JDBC05 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	透過properties資料結構來存放屬性資料 => 彈性比一二種更大
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('Martin', '226366', '1987-12-18')";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			//	為甚麼可以使用autoclose => 因Connection有實作autoclosebale介面
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);	// 這個是查詢語法 不可用insert
			stmt.execute(insert);	// 這個才是執行
			
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
