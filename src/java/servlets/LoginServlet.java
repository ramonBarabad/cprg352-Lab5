package servlets;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import service.AccountService;


public class LoginServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String logout = request.getParameter("logout");
        
        if(logout!=null){
            session.invalidate();
            session = request.getSession();            
            request.setAttribute("message", "You have successfully logged out");
        }else if(session.getAttribute("username")!=null){
            response.sendRedirect("home");
            return;
        }
                
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);        
        return;
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user;
        
        if(!username.equals("") && !password.equals("")){
            AccountService accountService = new AccountService();
            user = accountService.login(username, password);
            
            if(user!=null){
                session.setAttribute("username", user.getUsername());
                response.sendRedirect("home");
                return;
            }
        }
            
        
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("message", "Invalid login");
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
        return;
    }
}
