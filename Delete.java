package ProjectCS3220;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hw2.Card;
import lab5.Todo;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );
        try
        {
            Class.forName( "com.mysql.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
    
    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	int id = Integer.parseInt(request.getParameter("id"));
		
		// JDBC driver name and database URL
        String url="jdbc:mysql://cs3.calstatela.edu/cs3220stu111";

        //  Database credentials
        String username = "cs3220stu111";
        String password = "iMG2nvUI";
		
		Connection c = null;
        try
        {	        
            c = DriverManager.getConnection( url, username, password );
            
        String sql = "DELETE FROM files where id =" + id + ";";
            
        System.out.println("Query: " + sql);
            
        PreparedStatement pstmt = c.prepareStatement(sql);
            
        int numberOfRowsAffected = pstmt.executeUpdate();
        
        }
        catch( SQLException e )
        {
            throw new ServletException( e );
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                throw new ServletException( e );
            }
            	            
        }
        
        
        response.sendRedirect("Session.jsp");
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
