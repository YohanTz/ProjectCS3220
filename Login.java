package Cloud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/Cloud.jsp").forward(request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu111";
        String SQLusername = "cs3220stu111";
        String SQLpassword = "iMG2nvUI";
        Connection c = null;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
boolean hasError = false;
		
		if (username == null || username.trim().length() == 0) {
			hasError = true;
			request.setAttribute("nameError", "You must enter your username");
		}
		
		if (password == null || password.trim().length() == 0) {
			hasError = true;
			request.setAttribute("nameError", "You must a enter password");
		}
				
		if (hasError)
			doGet(request, response);
		
		else {
			// Insert the record into the database

				        try
				        {
				            c = DriverManager.getConnection( url, SQLusername, SQLpassword );
				            
				            Statement stmt = c.createStatement();
				            ResultSet r = stmt.executeQuery("SELECT count(*) FROM users WHERE username=\""+username+"\"  AND password= \"" + password + "\"");
				            r.next();
				            if(r.getInt("count(*)") != 1)
				            {
				            	
				            	hasError = true;
				            	request.setAttribute("nameError", "Username or password is invalid");
				            	doGet(request, response);
				            }
				            
				            else {
				            
				            stmt = c.createStatement();
				            ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE username = \""+username+"\"");
				            rs.next();
				            request.setAttribute("id",  rs.getString("id"));
				            
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
						response.sendRedirect("Session.jsp");
					}
	}
		
	}


