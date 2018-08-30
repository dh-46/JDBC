package tw.org.iii.java2;

import java.lang.reflect.Method;

/*	20180826AM2 JDBC 基本設定
 * 
 */

public class JDBC01 {

	public static void main(String[] args) {
		// 指定載入特定的類別名稱 () [為了載入驅動程式]
		//Class.forName(arg0);
		
		// Class類別物件方法介紹
//		String str1 = new String();
//		String str2 = "";
//		Class class1 = str1.getClass();
//		System.out.println(class1.getName());
//		
//		Class class2 = class1.getSuperclass(); // 取得父類別
//		System.out.println(class2.getName());
//		
//		//class1.getModifiers(); // 取得修飾字方法
//		
//		// .getDeclaredMethods()
//		Method[] methods = class1.getDeclaredMethods();
//		for (Method method : methods) {
//			System.out.println(method.getName());
//		}
		
		
		// 載入JDBC Driver
		// https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("OK"); 
			// reference設定完後, 顯示OK代表專案已經自動載入Driver
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		}
		
	}

}
