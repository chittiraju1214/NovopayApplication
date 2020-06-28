package com.example.novopay.util;

//import java.sql.Date;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.novopay.enums.AppPropertiesEnum;
import com.example.novopay.enums.TokenType;
import com.example.novopay.exception.TokenException;
import com.example.novopay.model.TokenPair;
import com.example.novopay.model.UserToken;
import com.example.novopay.model.Users;
import com.example.novopay.repository.UserTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil {

  /**
   * The Environment.
   */
  @Autowired
  Environment environment;

  /**
   * The User token repository.
   */
  @Autowired
  UserTokenRepository userTokenRepository;

  /**
   * Create refresh and access token token pair.
   *
   * @param user the user
   * @return the token pair
   */
  public TokenPair createRefreshAndAccessToken(Users user) {
    String refreshToken = RandomStringUtils.randomAlphanumeric(128);
    UserToken rToken = new UserToken();
    rToken.setTokenType(TokenType.REFRESH_TOKEN.getTokenTypeId());
    rToken.setToken(refreshToken);
    rToken.setUser(user);
//		userTokenRepository.save(rToken);
    String accessToken = generateToken(user);
    UserToken acessToken = new UserToken();
    acessToken.setTokenType(TokenType.AUTHORIZATION_TOKEN.getTokenTypeId());
    acessToken.setToken(accessToken);
    acessToken.setUser(user);
    userTokenRepository.saveAll(Arrays.asList(acessToken, rToken));
    return new TokenPair(accessToken, refreshToken);
  }

  /**
   * Generate token string.
   *
   * @param user the user
   * @return the string
   */
// generate token for user
  public String generateToken(Users user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("orgId", user);
    return doGenerateToken(claims, user.getUserId() + "");
  }

  // while creating the token -
  // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
  // 2. Sign the JWT using the HS512 algorithm and secret key.
  // 3. According to JWS Compact
  // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
  // compaction of the JWT to a URL-safe string
  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()
            + Long.parseLong(environment.getProperty
            (AppPropertiesEnum.ACCESS_TOKEN_EXPIRY_IN_SECS_KEY.getPropName())) * 1000))
        .signWith(SignatureAlgorithm.HS512,
            environment.getProperty(AppPropertiesEnum.JWT_TOKEN_SECRET_KEY.getPropName()))
        .compact();
  }


  /**
   * Gets expiration date from token.
   *
   * @param token the token
   * @return the expiration date from token
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Gets claim from token.
   *
   * @param <T>            the type parameter
   * @param token          the token
   * @param claimsResolver the claims resolver
   * @return the claim from token
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Gets all claims from token.
   *
   * @param token the token
   * @return the all claims from token
   */
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(environment.getProperty
        (AppPropertiesEnum.JWT_TOKEN_SECRET_KEY.getPropName())).parseClaimsJws(token).getBody();
  }

  /**
   * Is token expired boolean.
   *
   * @param token the token
   * @return the boolean
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * Gets user id from token.
   *
   * @param token the token
   * @return the user id from token
   */
  public String getUserIdFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Validate token boolean.
   *
   * @param token       the token
   * @param userDetails the user details
   * @return the boolean
   * @throws TokenException the token exception
   */
  public Boolean validateToken(String token, UserDetails userDetails) throws TokenException {
    final String userId = getUserIdFromToken(token);
    return (userId.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Gets user from token.
   *
   * @return the user from token
   */
  public Users getUserFromToken() {
    return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }


  public Integer getOrgIdFromToken(String token) {
    final Claims claims = getAllClaimsFromToken(token);
    Integer orgId = (Integer) claims.get("orgId");
    return orgId;
  }

}
