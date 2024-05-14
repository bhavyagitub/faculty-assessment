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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Display Courses</title><style>body {\r\n"
        		+ "  min-height: 100vh;\r\n"
        		+ "  display: flex;\r\n"
        		+ "  align-items: center;\r\n"
        		+ "  justify-content: center;\r\n"
        		+ "  padding: 20px;\r\n"
        		+ "  background: rgb(130, 106, 251);\r\n"
        		+ "}</style></head><body>");
        out.println("<h2>Subjects and Course Outcomes</h2>");
        out.println("<table border='1'><tr><th>ID</th><th>Subject</th><th>Outcome 1</th><th>Outcome 2</th><th>Outcome 3</th><th>Outcome 4</th><th>Outcome 5</th></tr>");

        try {
        	// Load the MySQL JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Create a connection to the database
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","system","varma");
         // Prepare the SQL query
            String subjectcode=request.getParameter("subjectcode");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM OUTCOMES where subjectcode=?");
            pstmt.setString(1,subjectcode);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("Subjectcode") + "</td>");
                out.println("<td>" + rs.getString("subject") + "</td>");
                out.println("<td>" + rs.getString("outcome1") + "</td>");
                out.println("<td>" + rs.getString("outcome2") + "</td>");
                out.println("<td>" + rs.getString("outcome3") + "</td>");
                out.println("<td>" + rs.getString("outcome4") + "</td>");
                out.println("<td>" + rs.getString("outcome5") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }

        out.close();
    }
}
