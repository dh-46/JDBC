package tw.org.iii.java2;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/*	20180901PM2
 * 	
 * 	BLOB
 * 	LONGBLOB
 */

public class JDBC19 {

	public static void main(String[] args) {
		Student s1 = new Student(50, 80, 100);
		
		// Connect to DB
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password", "root");

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/iii", info); 
				FileInputStream fin = new FileInputStream("dir1/chef.png");) {
			
			String sql = "UPDATE `accounts` SET img=?, student=? WHERE id =?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			//	把圖片讀進來
			
			pstmt.setBinaryStream(1, fin);
			pstmt.setObject(2, s1);
			pstmt.setString(3, "14");
			pstmt.executeUpdate();
			System.out.println("Save Success");
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
}

class Student implements Serializable {
	int ch, math, eng;
	public Student(int ch, int math, int eng) {
		this.ch = ch;
		this.math = math;
		this.eng = eng;
	}
	
	public int calScore() {
		return this.ch+this.math+this.eng;
	}
}
