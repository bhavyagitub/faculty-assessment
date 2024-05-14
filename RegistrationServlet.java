import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterFaculty")
public class RegisterFaculty extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Establish a database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String facultyid=request.getParameter("faculty-id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            String department = request.getParameter("department");
            String designation = request.getParameter("designation");
            String email =request.getParameter("email");
            
            Connection connection1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","system","varma");
            // Insert data into the database
            String sql = "INSERT INTO users (Facultyid,username, password, Name, department, designation, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection1.prepareStatement(sql);
            preparedStatement.setString(1,facultyid);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, fullName);
            preparedStatement.setString(5, department);
            preparedStatement.setString(6, designation);
            preparedStatement.setString(7, email);
            
            ResultSet rs=preparedStatement.executeQuery();
          
            request.setAttribute("Registered Successfully",true);
            response.sendRedirect("login.html");
            
        } catch (Exception e) {
            e.printStackTrace();
          
        }
      }
    }
