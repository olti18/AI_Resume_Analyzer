package OltiBerisha.AI_Resume_Analyzer.Service.Impl;


import OltiBerisha.AI_Resume_Analyzer.Service.AuthService;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret:}")
    private String clientSecret;

    @Value("${keycloak.admin.username:default-admin}")
    private String adminUsername;

    @Value("${keycloak.admin.password:default-password}")
    private String adminPassword;



    private Keycloak getKeycloakAdminInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm("master")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    public ResponseEntity<?> register(String username, String password, String email) {
        try {
            Keycloak keycloak = getKeycloakAdminInstance();

            // Create user representation
            UserRepresentation user = new UserRepresentation();
            user.setUsername(username);
            user.setEmail(email);
            user.setEnabled(true);

            // Set user credentials
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);
            user.setCredentials(Collections.singletonList(credential));

            // Create the user in Keycloak
            Response response = keycloak.realm(realm).users().create(user);

            if (response.getStatus() == 201) {
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
            } else {
                return ResponseEntity.status(response.getStatus()).body(Map.of("error", response.getStatusInfo().toString()));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred: " + ex.getMessage()));
        }
    }

    public ResponseEntity<?> login(String username, String password) {
        String tokenEndpoint = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakServerUrl, realm);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = String.format(
                "grant_type=password&client_id=%s&username=%s&password=%s",
                clientId, username, password
        );

        if (!clientSecret.isEmpty()) {
            body += "&client_secret=" + clientSecret;
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, entity, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of("error", "Keycloak service unavailable"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred: " + ex.getMessage()));
        }



    }
    // Correctly placed fetchAllUsers() method
    public ResponseEntity<List> fetchAllUsers() {
        try {
            Keycloak keycloak = getKeycloakAdminInstance();
            List users = keycloak.realm(realm).users().list();
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @Override
//    public List<UserRepresentation> fetchAllUsers() {
//        Keycloak keycloak = getKeycloakAdminInstance();
//        return keycloak.realm(realm).users().list();
//    }
}


//public class AuthServiceImpl {
//
//
//
//}
