package planner.com.planner.demo.Config;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import planner.com.planner.demo.model.User;


@Data
@Builder
@NoArgsConstructor
public class JwtResponse {

        private String token;
        private User user ; 
    
        public JwtResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }
}
