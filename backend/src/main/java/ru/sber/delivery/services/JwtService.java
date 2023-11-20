package ru.sber.delivery.services;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    public String getSubClaim(Jwt jwt);
    public String getEmailClaim(Jwt jwt);
    public String getPhoneNumberClaim(Jwt jwt);
    public String getPreferredUsernameClaim(Jwt jwt);
    
}
