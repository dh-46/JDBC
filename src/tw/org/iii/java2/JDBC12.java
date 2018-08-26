package tw.org.iii.java2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.sound.sampled.LineListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*	20180826PM2 JTable Tryout
 * 	
 * 	Use Table to show the data from DB to user	
 * 
 * 	Data --> Model --> JTable
 * 
 * 	Reference: 
 * 	https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 * 
 * 	Abstract Table
 * 	https://docs.oracle.com/javase/7/docs/api/javax/swing/table/AbstractTableModel.html
 * 
 */

public class JDBC12 extends JFrame {
//	private JTable jTable;

	// first try => fixed table not good enough
//	private String[] columnNames = {"First Name",
//            "Last Name",
//            "Sport",
//            "# of Years",
//            "Vegetarian"};
//	
//	private Object[][] data = {
//		    {"Kathy", "Smith",
//		     "Snowboarding", new Integer(5), new Boolean(false)},
//		    {"John", "Doe",
//		     "Rowing", new Integer(3), new Boolean(true)},
//		    {"Sue", "Black",
//		     "Knitting", new Integer(2), new Boolean(false)},
//		    {"Jane", "White",
//		     "Speed reading", new Integer(20), new Boolean(true)},
//		    {"Joe", "Brown",
//		     "Pool", new Integer(10), new Boolean(false)}
//		};
//	
//	public JDBC12() {
//		super("JDBC12 Practice");
//		
//		setLayout(new BorderLayout());
//		
//		jTable = new JTable(data, columnNames);
//		
//		// scrollable table by using JScrollPane
//		JScrollPane jsp = new JScrollPane(jTable);
//		
//		add(jsp, BorderLayout.CENTER);
//		
//		setSize(800,600);
//		setVisible(true);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}

	// ----------------Scrollable and auto Update Table------------------

	private JTable jTable;

	// row data
	private LinkedList<HashMap<String, String>> data;

	public JDBC12() {
		super("TABLE");

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
		// 模擬資料傳入
		data = new LinkedList<>();

		// 測試用資料
//		for (int i = 0; i < 100; i++) {
//			HashMap<String, String> row = new HashMap<>();
//			row.put("id", "" + i);
//			row.put("name", "John" + (int)(Math.random()*100));
//			row.put("tel", "123");
//			row.put("birthday", "2000-01-01");
//			data.add(row);
//		}

		String url = "jdbc:mysql://localhost:3306/iii";

		Properties prop = new Properties();
		prop.setProperty("user", "root");
		prop.setProperty("password", "root");

		// SQL Command
		// String query = "select name, id, tel, birthday from cust";
		String query = "select * from cust";

		try (Connection conn = DriverManager.getConnection(url, prop);) {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String f1 = rs.getString("id"); // Label depends on your result given by sql command
				String f2 = rs.getString("name");
				String f3 = rs.getString("tel");
				String f4 = rs.getString("birthday");
				
				HashMap<String, String> row = new HashMap<>();
				row.put("id", f1);
				row.put("name", f2);
				row.put("tel",f3);
				row.put("birthday",f4);
				data.add(row);
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
			return data.size();
		}

		@Override
		public int getColumnCount() {
			// how many columns
			return 4;
		}

		@Override
		public Object getValueAt(int row, int column) {
			String ret = "";
			switch (column) {
			case 0:
				ret = data.get(row).get("id");
				break;
			case 1:
				ret = data.get(row).get("name");
				break;
			case 2:
				ret = data.get(row).get("tel");
				break;
			case 3:
				ret = data.get(row).get("birthday");
				break;
			}

			return ret;
		}

		@Override
		public String getColumnName(int column) {
			String ret = "";
			switch (column) {
			case 0:
				ret = "id";
				break;
			case 1:
				ret = "name";
				break;
			case 2:
				ret = "tel";
				break;
			case 3:
				ret = "birthday";
				break;
			}
			return ret;
		}
	}

	public static void main(String[] args) {
		new JDBC12();
	}

}
