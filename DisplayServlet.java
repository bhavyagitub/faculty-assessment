package test;


import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	float a = 0,g=0,c=0;
	int attainment;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
 
            String jdbcURL = "jdbc:mysql://localhost:3306/anil";
            String dbUser = "root";
            String dbPassword = "21bq1a1207";
            String e=request.getParameter("exam");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL,dbUser,dbPassword);
            String sql="SELECT count(REGDNO) AS total FROM MARKS";
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()) {
                a  = rs.getInt("total");
               }

            if (e.equalsIgnoreCase("descriptive")) {
            	String sql1="SELECT count(REGDNO) AS total FROM MARKS where Q1>5";
                PreparedStatement ps1=conn.prepareStatement(sql1);
                ResultSet rs1=ps1.executeQuery();
                if(rs1.next()) {
                     g = rs1.getInt("total");
                   }
                c=(g/a)*100;
				if(c>70)
                	attainment=3;
                else if(c>60 && c<70)
                	attainment=2;
                else if(c>=50 && c<60)
                	attainment=1;
                else
                	attainment=0;
				
          }
          else if(e.equalsIgnoreCase("assignment")) {
         	
          }
          else if(e.equalsIgnoreCase("objective")) {
         	
          }
		}catch(Exception e) {
			
		}
	}
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h1>Data Table</h1>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Details</th>"); // Replace with your column names
        out.println("<th>Q1</th>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>No of Students attempted</td>");
        out.println("<td>"+a+"</td");
        out.println("<td>No of Students scored >50% of marks</td>");
        out.println("<td>"+g+"</td");
        out.println("<td>% of Students scored >=50% of marks</td>");
        out.println("<td>"+c+"</td");
        out.println("<td>Attainment</td>");
        out.println("<td>"+attainment+"</td");
        out.println("</tr>");
        out.println("</table>");
        out.println("</body></html>");

	}
	
}
