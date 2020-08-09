package com.abdelatif.contactsapi.security;

import static io.jsonwebtoken.Jwts.parser;

import com.abdelatif.contactsapi.exception.ContactApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  private KeyStore keyStore;

  @PostConstruct
  public void init() {
    try {
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceAsStream = getClass().getResourceAsStream("/contactApiSecret.jks");
      keyStore.load(resourceAsStream, "secretOWT".toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      throw new ContactApiException("Exception occurred while loading keystore", e);
    }
  }

  public String generateToken(Authentication authentication) {
    User principal = (User) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .signWith(getPrivateKey())
        .compact();
  }


  private PrivateKey getPrivateKey() {
    try {
      return (PrivateKey) keyStore.getKey("contactApiSecret", "secretOWT".toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      throw new ContactApiException("Exception occured while retrieving private key from keystore",
          e);
    }
  }

  private PublicKey getPublicKey() {
    try {
      return keyStore.getCertificate("contactApiSecret").getPublicKey();
    } catch (KeyStoreException e) {
      throw new ContactApiException("Exception occured while retrieving public key from keystore",
          e);
    }
  }

  public boolean validateToken(String jwtFromReq){
    parser().setSigningKey(getPublicKey()).parseClaimsJws(jwtFromReq);
    return true;
  }

  public String getUsernameFromJwt(String token){
    Claims claims = parser().setSigningKey(getPublicKey())
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }
}