package ProjectCS3220;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );

        try
        {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        }
        catch( ClassNotFoundException e )
        {
            throw new ServletException( e );
        }
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Register.jsp").forward(request,  response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu111";
        String SQLusername = "cs3220stu111";
        String SQLpassword = "iMG2nvUI";
        Connection c = null;
        
        
		String username = request.getParameter("newusername");
		String password = request.getParameter("newpassword");
		
		boolean hasError = false;
		
		if (username == null || username.trim().length() == 0) {
			hasError = true;
			request.setAttribute("nameError", "You must enter your username");
		}
		
		if (password == null || password.trim().length() == 0) {
			hasError = true;
			request.setAttribute("nameError", "You must have a password");
		}
		
		
		if (hasError)
			doGet(request, response);
		else {
			// Insert the record into the database

				        try
				        {
				            c = DriverManager.getConnection( url, SQLusername, SQLpassword );

				            Statement stmt = c.createStatement();
				            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM users WHERE  username = \"" + username + "\"");
				            
				            rs.next();
				            if(rs.getInt("count(*)") > 0)
				            {
				            	hasError = true;
				            	request.setAttribute("nameError", "Username already taken");
				            	doGet(request, response);
				            }
				            else {
					            stmt = c.createStatement();
					            String sql = "INSERT INTO users VALUES (0, \""+ username +"\",\"" + password + "\")";
					            stmt.executeUpdate(sql);
					            
					            
				            }
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
				        if(!hasError)
						// Send the User (Client) back to the GuestBook page
						response.sendRedirect("Cloud.jsp");
					}
	}

}
