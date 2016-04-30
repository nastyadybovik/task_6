package by.epam.training.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Настенька on 11/13/2015.
 */
public class LocaleFilter implements Filter {

    private static final String ATTR_LOCALE = "locale";

    private String locale;
    private ServletContext context;

    @Override
    public void destroy(){
        context = null;
    }

    @Override
    public void init(FilterConfig fConfig){
        locale = "ru";
        context = fConfig.getServletContext();
        context.log("LocaleFilter is initialized.");
        System.out.println("LocaleFilter is initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        req.getSession(false).setAttribute(ATTR_LOCALE, locale);

        System.out.println("LocaleFilter doFilter.");
        chain.doFilter(request, response);
    }
}
