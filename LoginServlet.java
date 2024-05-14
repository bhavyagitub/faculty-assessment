import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String username =request.getParameter("name1");
			String password = request.getParameter("name2");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","system","varma");
		     
			String sql="SELECT * FROM USERS WHERE username=? and password=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			
			ResultSet rs=pstmt.executeQuery();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("Course.html");
	}

}
