import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/MatrixServlet")
public class MatrixServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the CO-PO matrix values from the form
    	
    	String[][] matrix = new String[5][14]; // Fixed 5x14 matrix (12 POs, pso1, pso2)
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14; j++) {
                String paramName = "matrix[co" + (i + 1) + "][";
                if (j < 12) {
                    paramName += "po" + (j + 1);
                } else if (j == 12) {
                    paramName += "pso1";
                } else if (j == 13) {
                    paramName += "pso2";
                }
                paramName += "]";
                
                String value = request.getParameter(paramName);
                matrix[i][j] = value;
            }
        }

        // Save the matrix data to the database
        
        PreparedStatement preparedStatement = null;

        try {
        	// Load the MySQL JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Create a connection to the database
            Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","system","varma");
         // Prepare the SQL query

            // Create the table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS copomatrix ("
                    + "co VARCHAR(10) NOT NULL,"
                    + "po1 VARCHAR(10),"
                    + "po2 VARCHAR(10),"
                    + "po3 VARCHAR(10),"
                    + "po4 VARCHAR(10),"
                    + "po5 VARCHAR(10),"
                    + "po6 VARCHAR(10),"
                    + "po7 VARCHAR(10),"
                    + "po8 VARCHAR(10),"
                    + "po9 VARCHAR(10),"
                    + "po10 VARCHAR(10),"
                    + "po11 VARCHAR(10),"
                    + "po12 VARCHAR(10),"
                    +"pso1 VARCHAR(10),"
                    +"pso2 VARCHAR(10"
                    + "))";
            preparedStatement = connection.prepareStatement(createTableSQL);
            preparedStatement.executeUpdate();

            // Insert the matrix data into the database
            for (int i = 0; i < 5; i++) {
                String insertSQL = "INSERT INTO copomatrix (co, po1, po2, po3, po4, po5, po6, po7, po8, po9, po10, po11, po12,pso1,pso2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(insertSQL);

                preparedStatement.setString(1, "co" + (i + 1)); // Set CO as "co1," "co2," etc.

                for (int j = 0; j < 14; j++) {
                    preparedStatement.setString(j + 2, matrix[i][j]); // Set PO values
                }
            
                preparedStatement.executeUpdate();
            }

            // Redirect back to a success page
            response.sendRedirect("displayco-po.js");
           
        } catch (Exception e) {
            e.printStackTrace();
            // Handle database errors or validation errors as needed
            // Redirect to an error page
            //response.sendRedirect("error.jsp");
           
        } 
        }
    }

------------------------------------------------------------------------------------------------------------------------------------------------------------
co-po display jsp file

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>CO-PO Articulation Matrix</title>
</head>
<body>
    <h1>CO-PO Articulation Matrix</h1>

    <%-- Retrieve data from the database --%>
    <table border="1">
        <thead>
            <tr>
                <th>CO / PO</th>
                <th>PO1</th>
                <th>PO2</th>
                <th>PO3</th>
                <th>PO4</th>
                <th>PO5</th>
                <th>PO6</th>
                <th>PO7</th>
                <th>PO8</th>
                <th>PO9</th>
                <th>PO10</th>
                <th>PO11</th>
                <th>PO12</th>
                <th>PSO1</th>
                <th>PSO2</th>
                
            </tr>
        </thead>
        <tbody>
            <% 
            try {
            	Class.forName("oracle.jdbc.driver.OracleDriver");

                // Create a connection to the database
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","system","varma");
             // Prepare the SQL query
                
                String selectSQL = "SELECT * FROM co_po_matrix";
                PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                while (resultSet.next()) {
                    String co = resultSet.getString("co");
                    String po1 = resultSet.getString("po1");
                    String po2 = resultSet.getString("po2");
                    String po3 = resultSet.getString("po3");
                    String po4 = resultSet.getString("po4");
                    String po5 = resultSet.getString("po5");
                    String po6 = resultSet.getString("po6");
                    String po7 = resultSet.getString("po7");
                    String po8 = resultSet.getString("po8");
                    String po9 = resultSet.getString("po9");
                    String po10 = resultSet.getString("po10");
                    String po11 = resultSet.getString("po11");
                    String po12 = resultSet.getString("po12");
                    String pso1 = resultSet.getString("pso1");
                    String pso2 = resultSet.getString("pso2");
            %>
            <tr>
                <td><%= co %></td>
                <td><%= po1 %></td>
                <td><%= po2 %></td>
                <td><%= po3 %></td>
                <td><%= po4 %></td>
                <td><%= po5 %></td>
                <td><%= po6 %></td>
                <td><%= po7 %></td>
                <td><%= po8 %></td>
                <td><%= po9 %></td>
                <td><%= po10 %></td>
                <td><%= po11 %></td>
                <td><%= po12 %></td>
                <td><%= pso1 %></td>
                <td><%= pso2 %></td>
            </tr>
            <%
                }
                resultSet.close();
                preparedStatement.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            %>
        </tbody>
    </table>

    <!-- Add other content or links as needed -->
</body>
</html>
