package tw.org.iii.java2;

import java.lang.reflect.Method;

/*	20180826AM2 JDBC
 * 
 */

public class JDBC01 {

	public static void main(String[] args) {
		// ���w���J�S�w�����O�W�� () [���F���J�X�ʵ{��]
		//Class.forName(arg0);
		
//		String str1 = new String();
//		String str2 = "";
//		Class class1 = str1.getClass();
//		System.out.println(class1.getName());
//		
//		Class class2 = class1.getSuperclass(); // ���o�����O
//		System.out.println(class2.getName());
//		
//		//class1.getModifiers(); // ���o�׹��r
//		Method[] methods = class1.getDeclaredMethods();
//		for (Method method : methods) {
//			System.out.println(method.getName());
//		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("OK"); // reference�]�w����, ���OK�N��M�פw�g�۰ʸ��JDriver
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		}
		
	}

}
