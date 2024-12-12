package at.DiplomProjekt.Installateur.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;


@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Redirect based on authority. Adjust the role checks as per your application's roles.
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin-page");
                return;  // break from the method
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/user-page");
                return;  // break from the method
            }
        }

        // Default redirect if no roles match
        response.sendRedirect("/error");
    }
}