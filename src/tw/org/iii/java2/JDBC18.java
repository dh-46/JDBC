package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*	20180901PM1
 * 	
 * 	即時更新資料庫的某筆資料
 * 	=> 不透過傳統取其ID下更新語法 (避免不停地傳送SQL語法)
 * 	=>	ResultSet.TYPE_FORWARD_ONLY
 * 		ResultSet.CONCUR_UPDATABLE
 * 	
 */

public class JDBC18 {

	public static void main(String[] args) {
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			DatabaseMetaData metadata = conn.getMetaData(); // 連線文件的資料
			//	詢問資料庫是否支援ResultSet的同步
			//	(若未支援則需查詢官方文件是否有參數可調整)
			boolean isOK = metadata.supportsResultSetConcurrency(
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_UPDATABLE);
//			System.out.println(isOK);
			
			String sql = "SELECT * FROM `accounts` WHERE id = 2";
			Statement stmt = conn.createStatement
					(ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			rs.next(); // 游標移動
			System.out.println(rs.getString("realname")+":"+ rs.getString("account"));	// 確認有無取得資料
		
			rs.updateString("realname", "Tim McDay");
			rs.updateRow();
			
			//--------同時修改所有帳號的密碼(不用重複下SQL 語法)----------
			String sql2 = "SELECT * FROM `accounts`";
			PreparedStatement pstmt = conn.prepareStatement(sql2, 
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rs.updateString("password", "11111");
				rs.updateRow();
			}
			
			//------------直接新增資料(不用SQL 語法)------------
			rs.moveToInsertRow(); // 讓指標跑到表中最下方(空row)
			rs.updateString("account", "gorege");
			rs.updateString("password", "2222222");
			rs.updateString("realname", "gorege Lin");
			rs.insertRow(); // 執行新增 (其實是到暫存?)
			
			//-----------目前ResultSet的指標位置-----------
			System.out.println(rs.getRow());
			
			// rs.previous(); 	//	回上一筆
			
			
			rs.beforeFirst(); //	
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("account");
				System.out.println(id + ":" + name);
				rs.deleteRow(); // 每跑一條砍一筆 
			}	// 離開迴圈時是在沒有下一筆的位置
			
			
			//rs.last(); // 倒數第二筆
			rs.afterLast(); 
			rs.deleteRow(); // 刪除當下指標指向的那一筆 (剛新增的無法刪除)
			
			
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
