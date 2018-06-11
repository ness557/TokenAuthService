package simple.microservices.tokenauthservice.security;

import org.springframework.security.core.userdetails.User;

import javax.security.sasl.AuthenticationException;

public interface TokenService {

    String getToken(String username, String password) throws AuthenticationException;
    User getUser(String token);
}
