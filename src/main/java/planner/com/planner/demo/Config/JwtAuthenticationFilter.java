package planner.com.planner.demo.Config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

        private final JwtTokenUtil jwtTokenUtil;
        private final UserDetailsService userDetailsService;
    
        public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
            this.jwtTokenUtil = jwtTokenUtil;
            this.userDetailsService = userDetailsService;
        }
    
        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain chain
        ) throws ServletException, IOException {
            String authorizationHeader = request.getHeader("Authorization");
    
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String email = jwtTokenUtil.extractEmail(token);
    
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
    
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
    
            chain.doFilter(request, response);
        }
    }
    

