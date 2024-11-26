package unitea.dao;


import java.sql.Connection;
import java.sql.DriverManager;


public class MysqlConnection {

	public Connection getConnection() {
		Connection conn= null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UNITEA", "root", "adsdeveloper885!");
			System.out.println("conexão estabelecida!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
