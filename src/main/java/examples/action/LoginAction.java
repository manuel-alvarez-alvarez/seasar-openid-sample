package examples.action;

import static org.seasar.framework.container.factory.SingletonS2ContainerFactory.getServletContext;

import examples.dto.LoginDto;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.BeanValidatorForm;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.sreg.SRegRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoginAction extends Action {

    private final ConsumerManager manager;

    public LoginAction() {
        try {
            manager = new ConsumerManager();
            manager.setAssociations(new InMemoryConsumerAssociationStore());
            manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
        } catch (ConsumerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginDto dto = (LoginDto) ((BeanValidatorForm) form).getInstance();
        authRequest(dto.getUrl(), request, response);
        return null;
    }

    public void authRequest(String userSuppliedString, HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException, ServletException {
        try {
            String returnToUrl = httpReq.getRequestURL().toString() + "?is_return=true";
            List<?> discoveries = manager.discover(userSuppliedString);
            DiscoveryInformation discovered = manager.associate(discoveries);
            httpReq.getSession().setAttribute("openid-disc", discovered);
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
            FetchRequest fetch = FetchRequest.createFetchRequest();
            SRegRequest sregReq = SRegRequest.createFetchRequest();
            if ("1".equals(httpReq.getParameter("nickname"))) {
                sregReq.addAttribute("nickname", false);
            }
            if ("1".equals(httpReq.getParameter("email"))) {
                fetch.addAttribute("email", "http://schema.openid.net/contact/email", false);
                sregReq.addAttribute("email", false);
            }
            if ("1".equals(httpReq.getParameter("fullname"))) {
                fetch.addAttribute("fullname", "http://schema.openid.net/contact/fullname", false);
                sregReq.addAttribute("fullname", false);
            }
            if ("1".equals(httpReq.getParameter("dob"))) {
                fetch.addAttribute("dob", "http://schema.openid.net/contact/dob", true);
                sregReq.addAttribute("dob", false);
            }
            if ("1".equals(httpReq.getParameter("gender"))) {
                fetch.addAttribute("gender", "http://schema.openid.net/contact/gender", false);
                sregReq.addAttribute("gender", false);
            }
            if ("1".equals(httpReq.getParameter("postcode"))) {
                fetch.addAttribute("postcode", "http://schema.openid.net/contact/postcode", false);
                sregReq.addAttribute("postcode", false);
            }
            if ("1".equals(httpReq.getParameter("country"))) {
                fetch.addAttribute("country", "http://schema.openid.net/contact/country", false);
                sregReq.addAttribute("country", false);
            }
            if ("1".equals(httpReq.getParameter("language"))) {
                fetch.addAttribute("language", "http://schema.openid.net/contact/language", false);
                sregReq.addAttribute("language", false);
            }
            if ("1".equals(httpReq.getParameter("timezone"))) {
                fetch.addAttribute("timezone", "http://schema.openid.net/contact/timezone", false);
                sregReq.addAttribute("timezone", false);
            }

            if (!sregReq.getAttributes().isEmpty()) {
                authReq.addExtension(sregReq);
            }

            if (!discovered.isVersion2()) {
                httpResp.sendRedirect(authReq.getDestinationUrl(true));
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                httpReq.setAttribute("parameterMap", httpReq.getParameterMap());
                httpReq.setAttribute("message", authReq);
                dispatcher.forward(httpReq, httpResp);
            }
        } catch (OpenIDException e) {
            throw new RuntimeException(e);
        }
    }
}



