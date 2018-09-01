package tw.org.iii.java2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*	20180901PM1 資料應用
 * 	
 * 	1.	匯入農委會OpenData
 * 	2.	在表格上直接修改資料庫的資料
 * 	3.	
 * 
 * 	關鍵: ResultSet
 * 
 * 	注意區域變數與全域變數的使用
 */

public class JDBC12_2 extends JFrame {

	// ----------------Scrollable and auto Update Table------------------

	private JTable jTable;
	private int dataCount;
	private String[] fields;
	private ResultSet rs;
	private ResultSet rs2;
	private Connection conn;

	public JDBC12_2() {
		super("農委會推薦農村優良伴手禮");

		setLayout(new BorderLayout());

		initData();
		jTable = new JTable(new MyTableModel());
		// for scrollable diplay
		JScrollPane jsp = new JScrollPane(jTable);
		add(jsp, BorderLayout.CENTER);

		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initData() {
		String url = "jdbc:mysql://localhost:3306/iii";

		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");

		// SQL Command
		String query = "select id as `編號`, name as `產品名稱`, feature as `產品特色`, place as `商家地址`, imgurl as `圖片位址`  from `gifts`";

		try {
			conn = DriverManager.getConnection(url, prop);
			
			PreparedStatement pstmt0 = conn.prepareStatement("select count(*) as count from `gifts`");
			
			rs = pstmt0.executeQuery();
			rs.next();
			dataCount = rs.getInt("count");
			
			
			PreparedStatement pstmt = conn.prepareStatement(query,
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_UPDATABLE); // 參數for 同步更新
			rs = pstmt.executeQuery();
			
			// fileds在這裡初始化
			ResultSetMetaData metadata = rs.getMetaData();
			fields = new String[metadata.getColumnCount()];
			for (int i = 0; i < fields.length;i++) {
				fields[i] = metadata.getColumnLabel(i+1); // columnlabel 從1 開始計算
			}
			
			
			System.out.println("OK");
		} catch (SQLException e) {
			System.out.println(e);
		}

	}

	private class MyTableModel extends DefaultTableModel {

		@Override
		public int getRowCount() {
			// how many Rows
			return dataCount;
		}
			
		@Override
		public int getColumnCount() {
			// how many columns
			return fields.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			try {
				rs.absolute(row+1); 
				// model的row為0開始算
				
				return rs.getString(column+1);
			} catch (Exception e) {
				System.out.println(e);
				
				return "--------";
			} 
		}

		@Override
		public String getColumnName(int column) {
			return fields[column];
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			//	是否可更新欄位
			//	若回傳回來的column欄位為id則false => 不可修改
			//	equals的字串要等於最後SQL撈回來的欄位名稱
			return fields[column].equals("編號")?false:true;
		}
		
		@Override
		public void setValueAt(Object aValue, int row, int column) {
			// 修改該欄位
			super.setValueAt(aValue, row, column);
			
			try {
				//	移動到正確的位置
				rs.absolute(row+1);
				rs.updateString(fields[column], aValue.toString());
				//	執行update
				rs.updateRow();
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public static void main(String[] args) {
		new JDBC12_2();
	}

}
