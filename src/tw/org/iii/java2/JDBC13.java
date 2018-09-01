package tw.org.iii.java2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*	20180901AM1
 * 	將網路資料撈到本機資料庫
 * 	
 * 	狀況:
 * 	1. 接收資料有亂碼	=> 字元編碼問題 <查詢官網文件/調整伺服器組態檔的字元設定>
 * 		https://dev.mysql.com/doc/refman/5.7/en/charset-applications.html
 */

public class JDBC13 {

	public static void main(String[] args) {
		// step 1: download the json format data (String) from opendata source
		// pesudocode
//		String source = fetchopendata();
//		if (source != null) {
//			toMyDB(source);
//		}
		
		String source = fetchOpendata();
		if (source != null) {
			toMyDB(source);
		} else {
			System.out.println("No Data");
		}
		
	}

	// divide the problem --> finish structure first --> how to link data?
	static String fetchOpendata() {
		String ret = null;
		try {
			// 連結只是個字串
			URL url = new URL("http://data.coa.gov.tw/Service/OpenData/ODwsv/ODwsvAgriculturalProduce.aspx");
			// 透過connection連線
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			
			
			// 接收文字資料 (一列一列接/ 適用CSV)
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line; // 暫存一個列
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				// 為什麼寫while迴圈?
				// 來源資料(頁面原始碼)有換列格式(農委會)
				// 一般來說 如果為單列無換列格式, 可不用寫while迴圈
				// 複習: 串流下載的是檔案(原始碼)
				sb.append(line);
			}
			reader.close();
			
			ret = sb.toString();
			
		} catch (Exception e) {
			// 在這支程式中 對開發者任何例外都是一件事
			System.out.println(e);
		}
		
		return ret;
	}
	
	static void toMyDB(String json) {
		
		
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user","root");
		info.setProperty("password", "root");
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info)) {
			// SQL Prepared Statement
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO `gifts` (name, feature, place, imgurl) VALUES (?,?,?,?)");
			
			
			// 解析JSON 再將資料放到資料庫
			JSONArray root = new JSONArray(json); // runtime exception
			//System.out.println(root.length()); // 檢查是否有解析成功 (知道有幾個元素)
			
			for (int i = 0; i<root.length(); i++) {
				try {
					// 解析JSON
					JSONObject row = root.getJSONObject(i);	// 如果前面沒錯後有一筆有錯 => 在迴圈中捕捉
					String name = row.getString("Name");
					String feature = row.getString("Feature");
					String place = row.getString("SalePlace");
					String imgurl = row.getString("Column1");
					//System.out.println(name + ":" + feature + ":" + place + ":" + imgurl); // 檢查資料是否有抓對
				
					pstmt.setString(1, name);
					pstmt.setString(2, feature);
					pstmt.setString(3, place);
					pstmt.setString(4, imgurl);
					pstmt.execute();
				} catch (JSONException e) {
					// 有問題的不算 continue!
					System.out.println("JSON Exception: Line" + i);
				}
			}
			System.out.println("OK");
		} catch (Exception e) {
			// 捕捉SQL & JSON Exception
			System.out.println(e.toString());
		}
		
		
		
		
		
	}
}
