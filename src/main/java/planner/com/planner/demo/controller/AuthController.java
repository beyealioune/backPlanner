package planner.com.planner.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import planner.com.planner.demo.Config.JwtResponse;
import planner.com.planner.demo.Config.JwtTokenUtil;
import planner.com.planner.demo.Config.LoginRequest;
import planner.com.planner.demo.model.User;
import planner.com.planner.demo.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

  
    @Autowired
    private UserRepository userRepository;
    

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            UserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    // @PostMapping("/login")
    // public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
    //     try {
    //         // Vérifiez les informations d'identification de l'utilisateur
            
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    //         // System.out.println('');
    //         // Générez le jeton d'accès
    //         UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
    //         // Optional<User> user = UserRepository.findByEmail(loginRequest.getEmail());
    //         String token = jwtTokenUtil.generateToken(userDetails);

    //         // Renvoyez le jeton dans la réponse
    //         return ResponseEntity.ok(new JwtResponse(token, null));
        

            
            
    //     } catch (AuthenticationException e) {
    //         // Identifiants invalides
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            
    //     }
    // }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Vérifiez les informations d'identification de l'utilisateur
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            
            // Générez le jeton d'accès
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtTokenUtil.generateToken(userDetails);
    
            // Récupérez les informations de l'utilisateur
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
    
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                
                // Créez la réponse contenant le token et les informations de l'utilisateur
                JwtResponse loginResponse = new JwtResponse(token, user);
    
                // Renvoyez la réponse
                return ResponseEntity.ok(loginResponse);
            } else {
                // Utilisateur introuvable
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (AuthenticationException e) {
            // Identifiants invalides
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    

    // private void authenticate(String email, String password) {
    //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    // }
}


