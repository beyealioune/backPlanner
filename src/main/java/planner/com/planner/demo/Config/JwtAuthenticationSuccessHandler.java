package planner.com.planner.demo.Config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Vous pouvez personnaliser la logique de réussite de l'authentification ici
        // Par exemple, vous pouvez ajouter des informations supplémentaires à la réponse
        response.setStatus(HttpServletResponse.SC_OK);
    }
}


