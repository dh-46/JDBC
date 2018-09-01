package tw.org.iii.java2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.json.JSONStringer;
import org.json.JSONWriter;

/*	20180901AM2 26:00
 * 	
 * 	從資料庫撈資料並以JSON格式輸出
 * 	=> JSONStringer ()
 * 	=> JSONWriter (I/O)
 * 	
 * 	
 * 	設計程式觀念:
 * 	=> 使用JSON前先思考為何要JSON? (團隊?/ 程式所需?)
 * 	
 */

public class JDBC15 {

	public static void main(String[] args) {
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			String sql = "SELECT * FROM `gifts` LIMIT 0,10";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(); // 取得查詢回來的result
			
//			JSONStringer js = new JSONStringer();
//			JSONWriter jw = js.array();
//			jw.endArray(); // 結束array
//			JSONWriter jw = js.object();
//			jw.key("key1").value("value1");
//			jw.endObject();
			
//			JSONWriter jw = js.array();
//				jw.object();
//					jw.key("key1").value("value1");
//					jw.key("key2").value("value2");
//				jw.endObject();
//				jw.object();
//					jw.key("key1").value("value1");
//					jw.key("key2").value("value2");
//				jw.endObject();
//			jw.endArray(); // 結束array
//			System.out.println(jw);
			
			// 產生JSON字串
			JSONStringer js = new JSONStringer();
			JSONWriter jw = js.array();
			
			// 將資料放入JSON字串, 並設計格式
			while (rs.next()) {
				String name = rs.getString("name");
				String feature = rs.getString("feature");
				//String img = rs.getString("imgurl");
				jw.object();
				jw.key("產品名稱").value(name);
				jw.key("特色").value(feature);
				//jw.key("產品圖").value(img);
				jw.endObject();
			}
			js.endArray();
			System.out.println(js);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
