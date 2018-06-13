package simple.microservices.tokenauthservice.controller;

import io.jsonwebtoken.MalformedJwtException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import simple.microservices.tokenauthservice.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    private TokenService tokenService;

    @Autowired
    public TokenController(@Qualifier("theTokenService") TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> getUser(@RequestParam String token) {

        User user = tokenService.getUser(token);

        if (user != null) {
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("password", user.getPassword());
            map.put("authorities", user.getAuthorities().toString());

            return new ResponseEntity<>(new JSONObject(map), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    public ResponseEntity<String> getToken(@RequestParam String username,
                                           @RequestParam String password) {

        try {
            String token = tokenService.getToken(username, password);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

