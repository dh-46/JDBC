package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180826PM1 getConnection V3 00:42:22
 * 	
 * 	建立連線 (透過字串) 第三招  [推薦!!]
 * 	getConnection(String url, Properties info)
 * 		=> properties 
 * 			=> https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
 * 			=> 看一下 Properties and Descriptions (可設定的key)
 * 		=> 彈性更大, 自訂屬性設定
 * 	Properties
 * 	=> https://docs.oracle.com/javase/7/docs/api/java/util/Properties.html
 * 	=> 實作Map介面 (key, value)
 * 	=> 都是字串
 * 	=> setProperty : 設定
 * 	=> getProperty : 取值
 * 	
 * 	注意import的package => java.sql.Connection
 */

public class JDBC04 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/iii"; 
		
		//	透過properties資料結構來存放屬性資料 => 彈性比一二種更大
		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");
		
		
		// SQL Command
		String insert = "insert into cust (name, tel, birthday)" + 
						"values ('Ben', '232323', '2018-01-01')";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, prop);
			Statement stmt = conn.createStatement();
			
			//	stmt.executeQuery(insert);
			stmt.execute(insert);
			
			
			conn.close();
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
