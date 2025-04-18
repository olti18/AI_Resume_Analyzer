package OltiBerisha.AI_Resume_Analyzer.Controller;


import OltiBerisha.AI_Resume_Analyzer.Service.Impl.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthServiceImpl authService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {
        return authService.register(username, password, email);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String username,
            @RequestParam String password) {
        return authService.login(username, password);
    }

//    @GetMapping
//    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
//        List<UserRepresentation> users = authService.fetchAllUsers();
//        return ResponseEntity.ok(users);
//    }

    @GetMapping
    public ResponseEntity<List> getAllUsers() {
        List users = authService.fetchAllUsers().getBody();
        return ResponseEntity.ok(users);
    }

}

//
//public class AuthController {
//}
