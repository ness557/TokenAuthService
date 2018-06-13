package simple.microservices.tokenauthservice.security;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.*;
import java.util.stream.Collectors;

@Service("theTokenService")
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret}")
    private String secret;

    private UserDetailsService userService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public TokenServiceImpl(@Qualifier("theUserDetailsService") UserDetailsService userDetailsService) {
        this.userService = userDetailsService;
    }

    @Override
    public String getToken(String username, String password) throws AuthenticationException {
        logger.info("Trying to get token for user: " + username);

        if (username == null || password == null) {
            logger.error("Empty string");
            return null;
        }
        User user = (User) userService.loadUserByUsername(username);
        if (ObjectUtils.allNotNull(user) &&
                user.getPassword().equals(password)) {

            Map<String, Object> tokenData = new HashMap<>();

            tokenData.put("clientType", "user");
            tokenData.put("username", user.getUsername());
            tokenData.put("password", user.getPassword());
            tokenData.put("authorities", user.getAuthorities());
            tokenData.put("token_create_time", new Date().getTime());

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 5);
            Date expDate = calendar.getTime();

            tokenData.put("token_expiration_time", calendar.getTime());
            JwtBuilder jwtBuilder = Jwts.builder();

            jwtBuilder.setExpiration(expDate);
            jwtBuilder.setClaims(tokenData);

            logger.info("Got token");
            return jwtBuilder.signWith(SignatureAlgorithm.HS512, secret).compact();
        } else {
            logger.error("Authentication error");
            throw new AuthenticationException("Authentication error");
        }
    }

    @Override
    public User getUser(String token) {

        Map<String, Object> tokenData;
        try {
            tokenData = (Map) Jwts.parser().setSigningKey(secret).parse(token).getBody();
        } catch (Exception e) {
            logger.info("Wrong token");
            return null;
        }

        if(tokenData == null){
            logger.info("token is empty");
            return null;
        }

        User user = (User) userService.loadUserByUsername(((String) tokenData.get("username")));

        if(!user.getPassword().equals(tokenData.get("password"))){
            logger.info("wrong credits");
            return null;
        }

        return user;

    }
}
