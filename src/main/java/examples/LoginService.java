package examples;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.sreg.SRegRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginService {

    private final ConsumerManager manager;

    public LoginService() {
        try {
            manager = new ConsumerManager();
            manager.setAssociations(new InMemoryConsumerAssociationStore());
            manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
        } catch (ConsumerException e) {
            throw new RuntimeException(e);
        }
    }

    public String authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String endpointUrl = request.getParameter("url");
        String returnToUrl = request.getRequestURL().toString() + "?is_return=true";
        List<?> discoveries = manager.discover(endpointUrl);
        DiscoveryInformation discovered = manager.associate(discoveries);
        AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
        FetchRequest fetch = FetchRequest.createFetchRequest();
        SRegRequest sregReq = SRegRequest.createFetchRequest();
        if ("1".equals(request.getParameter("nickname"))) {
            sregReq.addAttribute("nickname", false);
        }
        if ("1".equals(request.getParameter("email"))) {
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", false);
            sregReq.addAttribute("email", false);
        }
        if ("1".equals(request.getParameter("fullname"))) {
            fetch.addAttribute("fullname", "http://schema.openid.net/contact/fullname", false);
            sregReq.addAttribute("fullname", false);
        }
        if ("1".equals(request.getParameter("dob"))) {
            fetch.addAttribute("dob", "http://schema.openid.net/contact/dob", true);
            sregReq.addAttribute("dob", false);
        }
        if ("1".equals(request.getParameter("gender"))) {
            fetch.addAttribute("gender", "http://schema.openid.net/contact/gender", false);
            sregReq.addAttribute("gender", false);
        }
        if ("1".equals(request.getParameter("postcode"))) {
            fetch.addAttribute("postcode", "http://schema.openid.net/contact/postcode", false);
            sregReq.addAttribute("postcode", false);
        }
        if ("1".equals(request.getParameter("country"))) {
            fetch.addAttribute("country", "http://schema.openid.net/contact/country", false);
            sregReq.addAttribute("country", false);
        }
        if ("1".equals(request.getParameter("language"))) {
            fetch.addAttribute("language", "http://schema.openid.net/contact/language", false);
            sregReq.addAttribute("language", false);
        }
        if ("1".equals(request.getParameter("timezone"))) {
            fetch.addAttribute("timezone", "http://schema.openid.net/contact/timezone", false);
            sregReq.addAttribute("timezone", false);
        }

        if (!sregReq.getAttributes().isEmpty()) {
            authReq.addExtension(sregReq);
        }

        if (!authReq.isVersion2()) {
            response.sendRedirect(authReq.getDestinationUrl(true));
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/login.jsp");
            request.setAttribute("parameterMap", request.getParameterMap());
            request.setAttribute("message", authReq);
            dispatcher.forward(request, response);
        }
        return null;
    }
}
