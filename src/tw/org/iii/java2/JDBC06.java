package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 刪除句法
 * 	
 * 
 */

public class JDBC06 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	透過properties資料結構來存放屬性資料 => 彈性比一二種更大
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		// String del = "delete from cust where id = 3";
		// String del = "delete from cust where name = 'Ben'";
		String del = "delete from cust";
		
		try (Connection conn = DriverManager.getConnection(url, prop);) {
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);	// 這個是查詢語法 不可用insert
			stmt.execute(del);	// 這個才是執行
			
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
