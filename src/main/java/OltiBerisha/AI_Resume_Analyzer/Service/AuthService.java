package OltiBerisha.AI_Resume_Analyzer.Service;

import org.springframework.http.ResponseEntity;

public interface AuthService  {

    ResponseEntity<?> register(String username, String password, String email);
    ResponseEntity<?> login(String username, String password);


}
