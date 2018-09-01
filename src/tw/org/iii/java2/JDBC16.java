package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/*	20180901AM2
 * 	
 * 	帳號密碼登入
 */

public class JDBC16 {

	public static void main(String[] args) {
		String account = "tom" , password = "222222", realname="Tom Cruise";
		
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			if (!isDataRepeat(account, conn)) {
				String sql = "INSERT INTO accounts (account, password, realname)" + "VALUES (?,?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, account);
				pstmt.setString(2, password);
				pstmt.setString(3, realname);
				pstmt.executeUpdate();
				
				System.out.println("UPDATE Success");
			} else {
				System.out.println("資料重複");
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	static boolean isDataRepeat(String account, Connection conn) throws SQLException{
		// 傳入account & conn 讓方法可以存取
		// 為什麼拋出例外?	=> 如果這裡出現例外, 然後處理完, 那這個方法的回傳值true/false就沒解
		//	拋出讓上面的程式去接, 才會達到我們要的效果
		String sql ="SELECT count(*) AS count FROM accounts WHERE account = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, account);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int count = rs.getInt("count"); // 如果有一樣的 回傳值大於0
		
		return count != 0;
	}

}
