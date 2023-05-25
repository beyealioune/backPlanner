package planner.com.planner.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
        private final JwtTokenUtil jwtTokenUtil;
        private final UserDetailsService userDetailsService;



 
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
        private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    
        // Injectez les dépendances nécessaires dans le constructeur
    
        public SecurityConfig(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService,
                              JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                              JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                              JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler) {
            this.jwtTokenUtil = jwtTokenUtil;
            this.userDetailsService = userDetailsService;
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
            this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
            this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        }
    
        // Autres configurations...
    
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .authorizeRequests()
                        .antMatchers("/auth/login","/users/register")
                        .permitAll()
                        .anyRequest().authenticated()
                    .and()
                    .formLogin()
                        .successHandler(jwtAuthenticationSuccessHandler)
                        .failureHandler(jwtAuthenticationFailureHandler)
                    .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        }


        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    
  
    
        // @Override
        // protected void configure(HttpSecurity http) throws Exception {
        //     http.csrf().disable()
        //             .authorizeRequests()
        //                 .antMatchers("/auth/login").permitAll()
        //                 .anyRequest().authenticated()
        //             .and()
        //             .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        // }
    
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }
    
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
    

 


