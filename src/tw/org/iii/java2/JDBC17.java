package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/*	20180901PM1 
 * 	帳號密碼驗證通過後建立該帳號的物件實體
 * 	
 * 	基本流程
 * 	1.	連線資料庫
 * 	2.	檢查帳號密碼
 * 	3.	驗證通過後建立物件實體
 * 	
 */

public class JDBC17 {

	public static void main(String[] args) {
		String account = "John";
		String pwd = "12345";
		
		
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			Member member = null;
			if ((member = checkMember(account, pwd, conn)) != null) {
				System.out.println("Welcome " + member.realname);
			} else {
				System.out.println("Login Failed!");
			}
			
		} catch (Exception e) {
			System.out.println("Server busy");
		}
	}
	
	static Member checkMember(String account, String pwd, Connection conn) throws SQLException{
		String sql = "SELECT * FROM accounts WHERE account = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, account);
		pstmt.setString(2, pwd);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			// 有資料建立出物件實體
			return new Member(rs.getString("account"), rs.getString("realname"));
		}else {
			// 沒資料
			return null;
		}
	}

}

class Member {
	String account, realname;
	public Member(String account, String realname) {
		this.account = account;
		this.realname = realname;
	}
	
	
}