package by.epam.training.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by Ќастенька on 11/12/2015.
 */
public class RequestFilter implements Filter {

    private ServletContext context;

    private static final String COD="SHA-1";

    @Override
    public void destroy(){
        context = null;
    }

    @Override
    public void init(FilterConfig fConfig){
        context = fConfig.getServletContext();
        context.log("RequestFilter is initialized.");
        System.out.println("RequestFilter is initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            Enumeration<String> params = req.getParameterNames();
            while (params.hasMoreElements()) {
                String param = params.nextElement();
                if (!validateParameter(req.getParameter(param))) {
                    //что-то сделать, если неподход€щие параметры
                } else {
                    ////
                }
                if (param.equals("password")) {

                    System.out.println("Filter catch password.");
                    String hash = getHash(req.getParameter(param));
                    request.setAttribute("pass", hash);
                    req.setAttribute("pass", hash);
                }

            }
        } catch (NoSuchAlgorithmException e) {
            //обработка событи€
            System.err.print("Can not getHash in Filter.");
        }
        System.out.println("RequestFilter doFilter.");
        chain.doFilter(req, response);
    }

    private static String getHash( String string) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(COD);
        StringBuffer  hexString = new StringBuffer();

        sha.reset();
        sha.update(string.getBytes());
        byte[] array = sha.digest();

        for (int i = 0; i < array.length; i++) {
            hexString.append(Integer.toHexString(0xFF & array[i]));
        }
        System.out.println("=============================\nHASH: " + hexString + "\n========================================");
        return hexString.toString();
    }

    private static boolean validateParameter(String string){
        if(!string.isEmpty() ){
            return true;
        } else {
            return false;
        }
    }
}
