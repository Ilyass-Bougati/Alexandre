package alex.server.security;

import alex.server.utils.AuthFunctions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String path = request.getServletPath();
        // checking for sessions
        HttpSession session = request.getSession(true);

        // The authorized paths
        Pattern pattern = Pattern.compile("/auth/[A-Za-z]+");
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches() && method.equals("POST")) {
            // checking if already authenticated
            if (AuthFunctions.isAuthenticated(session))
            {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Already authenticated"
                );
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }


        if (!AuthFunctions.isAuthenticated(session)) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Not authorized"
            );
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
