package lk.ijse.gdse.springboot.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lk.ijse.gdse.springboot.jwt.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class JwtUtil implements Serializable {
    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 12;

    @Value("${jwt.secret}")
    private String secretKey;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public Date getExpirationDateFromToken(String token) {
        return (Date) getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateToken(UserDto userDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",userDto.getRole());
        return doGenerateToken(claims,userDto.getEmail());
    }
    //while creating the token - 1.Define claims of the token,like Issuer,Expiration
    //2.Sign the JWT using the HS512 algorithm and secret
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }
    //validate token
    public Boolean validateToken (String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
