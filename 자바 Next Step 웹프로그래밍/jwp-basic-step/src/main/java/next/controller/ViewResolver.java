package next.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewResolver {

    public static void service(String view, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            if(view.startsWith("redirect")){
                String redirectUrl = resolveRedirectPath(view);
                System.out.println("redirectUrl = " + redirectUrl);
                res.sendRedirect(redirectUrl);
            }
            else{
                RequestDispatcher rd = req.getRequestDispatcher(view);
                rd.forward(req,res);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String resolveRedirectPath(String view){
        return view.split("redirect:")[1];
    }
}
