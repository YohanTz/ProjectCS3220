package ProjectCS3220;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



@WebServlet("/Upload")
public class Upload extends HttpServlet {

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
    	request.getRequestDispatcher("/WEB-INF/views/session.jsp").forward(request,  response);
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
    	// JDBC driver name and database URL
        String url="jdbc:mysql://cs3.calstatela.edu/cs3220stu111";

        //  Database credentials
        String username = "cs3220stu111";
        String password = "iMG2nvUI";

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        
        //int id = (int) servletContext.getAttribute("id");
        
        File repository = (File) servletContext
            .getAttribute( "javax.servlet.context.tempdir" );
        
        factory.setRepository( repository );

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload( factory );

        // Count how many files are uploaded
        int count = 0;
        
        // The directory we want to save the uploaded files to.
        String fileDir = getServletContext().getRealPath( "/WEB-INF/uploads" );

        // Parse the request
        try
        {
            List<FileItem> items = upload.parseRequest( request );
            
            for( FileItem item : items )
            {
                // If the item is not a form field - meaning it's an uploaded
                // file, we save it to the target dir
                if( !item.isFormField() )
                {
                    // item.getName() will return the full path of the uploaded
                    // file, e.g. "C:/My Documents/files/test.txt", but we only
                    // want the file name part, which is why we first create a
                    // File object, then use File.getName() to get the file
                    // name.
                	// /var/usr/some/temp/dir/some-file.jpg
                	// /user/albert/3220/WEB-INF/uploads   some-file.jpg
                	
                    String fileName = (new File( item.getName() )).getName();
                    servletContext.setAttribute("fileName", fileName);
                    File file = new File( fileDir, fileName );
                    item.write( file );
                    ++count;
                }
            }
        }
        catch( Exception e )
        {
            throw new IOException( e );
        }

        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();
        out.println( "<html><head><title>Upload</title></head><body>" );
        out.println( "<p>" + count + " file(s) uploaded to " + fileDir );
        out.println( "</body></html>" );
        
        if(count >= 1) {
	        Connection c = null;
	        try
	        {	        
	            c = DriverManager.getConnection( url, username, password );
	            
	            // String sql = "INSERT INTO files (id, path, owner_id) VALUES (0," + fileDir + "/"  + (String) servletContext.getAttribute("fileName") + ", " + "1)";
            String sql = "INSERT INTO files(path, name, owner_id) VALUES(" + fileDir + "/" 
	        + (String) servletContext.getAttribute("fileName") + ", " 
	        + (String) servletContext.getAttribute("fileName") + ", "
	        + (int) servletContext.getAttribute("id");
	            
            System.out.println("Query: " + sql);
	            
	            PreparedStatement pstmt = c.prepareStatement(sql);
	            
	            
	            int numberOfRowsAffected = pstmt.executeUpdate();
	            out.println(numberOfRowsAffected);
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
	        try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        response.sendRedirect("session.jsp");
    }

}
