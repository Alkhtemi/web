

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.MustacheRenderer;

/**
 *
 * @author Iuri
 */
public class BaseServlet extends HttpServlet {
    

    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(BaseServlet.class);

    public static final String HTML_UTF_8 = "text/html; charset=UTF-8";
    public static final String PLAIN_TEXT_UTF_8 = "text/plain; charset=UTF-8";
    public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
    
        static final Set<String> PROTECTED_PAGES = new HashSet<>(Arrays.asList("/private"));
    static final String LOGIN_PAGE = "/login";


    private final MustacheRenderer mustache;

    protected BaseServlet() {
        mustache = new MustacheRenderer();
    }

    //method to set the properties of a response and send it out
    protected void issue(String mimeType, int returnCode, byte[] output, HttpServletResponse response) throws IOException {
        response.setContentType(mimeType);
        response.setStatus(returnCode);
        response.getOutputStream().write(output);
    }

    //method to render a response page from a template and data object
    //set the properties of the response and send it out
    protected void showView(HttpServletResponse response, String templateName, Object model) throws IOException {
        String html = mustache.render(templateName, model);
        issue(HTML_UTF_8, HttpServletResponse.SC_OK, html.getBytes(CHARSET_UTF8), response);
    }
    
      boolean authOK(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String userName = UserFuncs.getCurrentUser(request);
        if (PROTECTED_PAGES.contains(uri) && "".equals(userName)) {
            UserFuncs.setLoginRedirect(request);
            response.sendRedirect(response.encodeRedirectURL(LOGIN_PAGE));
            return false;
        }
        return true;
    }

}

