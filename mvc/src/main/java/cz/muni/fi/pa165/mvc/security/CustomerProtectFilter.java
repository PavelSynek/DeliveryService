package cz.muni.fi.pa165.mvc.security;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonAuthenticateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.persistence.NoResultException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 * Created by Tomas Milota on 22.1.2016.
 */
@WebFilter(urlPatterns = { "/order/*", "/customer/detail/*", "/customer/list", "/product/*"})
public class CustomerProtectFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(CustomerProtectFilter.class);

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            response401(response);
            return;
        }
        String[] creds = parseAuthHeader(auth);
        String logname = creds[0];
        String password = creds[1];

        //get Spring context and UserFacade from it
        CustomerFacade customerFacade = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext()).getBean(CustomerFacade.class);
        EmployeeFacade employeeFacade = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext()).getBean(EmployeeFacade.class);


        PersonDTO matchingUser = null;
        try {
            matchingUser = employeeFacade.findByEmail(logname);
        } catch (NoResultException | ViolentDataAccessException ex) {
            // nothing
        }
        try {
            matchingUser = customerFacade.findByEmail(logname);
        } catch (NoResultException | ViolentDataAccessException e) {
            // nothing
        }

        if (matchingUser == null) {
            log.warn("no user with email {}", logname);
            response401(response);
            return;
        }
        PersonAuthenticateDTO personAuthenticateDTO = new PersonAuthenticateDTO();
        personAuthenticateDTO.setPersonId(matchingUser.getId());
        personAuthenticateDTO.setPassword(password);

        Boolean isCustomer = false;
        Boolean isEmployee = false;
        try{
            isCustomer = customerFacade.authenticate(personAuthenticateDTO);
        } catch(ViolentDataAccessException e) {}
        try{
            isEmployee = employeeFacade.authenticate(personAuthenticateDTO);
        } catch(ViolentDataAccessException e) {}

        if (!isCustomer && !isEmployee) {
            log.warn("wrong credentials: user={} password={}", creds[0], creds[1]);
            response401(response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("authenticatedUser", matchingUser);
        request.setAttribute("authenticatedUser", matchingUser);
        chain.doFilter(request, response);
    }


    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Try again</body></html>");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}