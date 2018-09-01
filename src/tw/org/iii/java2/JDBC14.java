package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*	20180901AM2 存取資料庫資料
 * 	
 * 	prepareedStatement 使用場合
 * 	=> 指令需重複執行
 * 	=> SQL Injection
 */

public class JDBC14 {

	public static void main(String[] args) {
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user","root");
		info.setProperty("password", "root");
				
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) as nums FROM `gifts`");  // 利用 as nums 提升維護性
			rs.next(); // 移動指標
			
			// 取得回傳值
			int nums = rs.getInt("nums"); // getInt => 因為知道來源為整數且有要操作
			String numsString = rs.getString("nums"); // 回來的是字串
			System.out.println(nums + ";" + numsString);
			
			
			// 資料分頁處理
			int rpp = 10;	// 每頁有幾筆資料
			int page = 1;	// view page ?
			int start = (page-1)*rpp;	// page start index
			
			// 複雜的給SQL做
			rs = stmt.executeQuery("SELECT id, name FROM `gifts` LIMIT " + start + "," + rpp); 
			// LIMIT 0,10 從第0筆開始要10筆
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				System.out.println(id + ":" + name);
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

}
