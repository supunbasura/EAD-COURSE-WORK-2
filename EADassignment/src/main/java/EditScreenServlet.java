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

@SuppressWarnings("serial")
@WebServlet("/editurl")
public class EditScreenServlet extends HttpServlet {
    private final static String query = "select name,nic,department,designation,joinedDate from employee where id=?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        
        //set content type
        res.setContentType("text/html");

        //get the id
        //get the values
        
        int id = Integer.parseInt(req.getParameter("id"));
        
        //link the bootstrap
        pw.println("<link rel='stylesheet' href='css/boostrap.css'></link>");
        pw.println("<link rel='stylesheet' href='css/emp.css'></link>");
        pw.println("<img class='backgrou-im' src='img/1.jpg'>");
        
        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp","root","");
                PreparedStatement ps = con.prepareStatement(query);){
        	
            //set value 
        	
            ps.setInt(1, id);
            //resultSet
            
            ResultSet rs = ps.executeQuery();
            rs.next();
            pw.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
            pw.println("<form action='edit?id="+id+"' method='post'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<td>Name</td>");
            pw.println("<td><input type='text' name='name' value='"+rs.getString(1)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>NIC</td>");
            pw.println("<td><input type='text' name='nic' value='"+rs.getString(2)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Department</td>");
            pw.println("<td><input type='text' name='department' value='"+rs.getString(3)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Designation</td>");
            pw.println("<td><input type='text' name='designation' value='"+rs.getString(4)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>JoinedDate</td>");
            pw.println("<td><input type='text' name='joinedDate' value='"+rs.getString(5)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><button type='submit' class='btn btn-outline-success'>Edit</button></td>");
            pw.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<a href='test.html'><button class='btnHome'>Home</button></a>");
        pw.println("</div>");
        
        //close the stream
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}

