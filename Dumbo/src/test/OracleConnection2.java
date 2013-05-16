package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class OracleConnection2 {
	private static final String QUERY = "select * from user_tables";
//	private static final String USER = "DELTEAM3";
//	private static final String PWD = "W00d5!oCk";
	private static final String USER = "giridhar";
	private static final String PWD = "password";

//	private static final String URL = "jdbc:oracle:thin:@10.11.23.110:1521:blrdb01";
	private static final String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=tcps)(HOST=10.11.23.14)(PORT=2484))(CONNECT_DATA=(SERVICE_NAME=orcl.blreng.scieng.local)))";
        // 	(ADDRESS=(PROTOCOL=TCP)(HOST=blrgiswin03.blreng.scieng.local)(PORT=1521))
	//private static final String URL = "jdbc:oracle:thin:@10.11.23.14:1521:orcl";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		System.out.println("Starting...");
		
		Properties props = new Properties();
		props.setProperty("user", USER);
		props.setProperty("password", PWD);
		props.setProperty("oracle.net.ssl_cipher_suites",
				"(SSL_DH_anon_WITH_3DES_EDE_CBC_SHA, SSL_DH_anon_WITH_RC4_128_MD5, SSL_DH_anon_WITH_DES_CBC_SHA)");

		props.setProperty("oracle.net.ssl_server_dn_match", "false");
		Connection conn = DriverManager.getConnection(URL,props);
		
		Statement stmnt = conn.createStatement();
		ResultSet rs = stmnt.executeQuery(QUERY);
		System.out.println("Rs["+rs+"]");
		int columns = rs.getMetaData().getColumnCount();
		while(rs.next()){
			for(int i = 1; i <= columns; i++){
				System.out.print(rs.getObject(i)+(i<= (columns-1) ? "--" : ""));
			}
			System.out.println();
		}
		stmnt.close();
		conn.close();
	}
}

