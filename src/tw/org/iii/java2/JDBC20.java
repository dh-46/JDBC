package tw.org.iii.java2;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/*	20180901PM2
 * 	
 * 
 */

public class JDBC20 {

	public static void main(String[] args) {
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info);
				FileOutputStream fout = new FileOutputStream("dir2/chef.png");) {

			String sql = "SELECT * FROM  `accounts` WHERE id =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "14");
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			InputStream in = rs.getBinaryStream("img");
			int len = 0;
			byte[] buf = new byte[4096];
			
			while ((len = in.read(buf)) != -1) {
				fout.write(buf, 0,len);
			}
			fout.flush();
			in.close();
			
			InputStream in2 = rs.getBinaryStream("student");
			ObjectInputStream oin = new ObjectInputStream(in2); // 串流轉回物件
			Student s2 = (Student)oin.readObject();
			System.out.println(s2.calScore());
			System.out.println("Export Success");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
