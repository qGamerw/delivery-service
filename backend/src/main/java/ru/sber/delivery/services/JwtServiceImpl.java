package ru.sber.delivery.services;

import org.springframework.stereotype.Service;

import ru.sber.delivery.exceptions.UserNotFound;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Service
public class JwtServiceImpl implements JwtService{

    @Override
    public String getSubClaim() {
        Jwt token = getUserJwtTokenSecurityContext();
        String claimName = JwtClaimNames.SUB;
        return token.getClaim(claimName);
    }

    @Override
    public String getEmailClaim() {
        Jwt token = getUserJwtTokenSecurityContext();
        return token.getClaim("email");
    }

    @Override
    public String getPhoneNumberClaim() {
        Jwt token = getUserJwtTokenSecurityContext();
        return token.getClaim("phone_number");
    }

    @Override
    public String getPreferredUsernameClaim() {
        Jwt token = getUserJwtTokenSecurityContext();
        return token.getClaim("preferred_username");
    }

    @Override
    public Long getCreatedTimestampClaim() {
        Jwt token = getUserJwtTokenSecurityContext();
        return token.getClaim("created_timestamp");
    }

    @Override
    public Jwt getUserJwtTokenSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
            Jwt jwt = jwtAuthenticationToken.getToken();

            return jwt;
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }

    @Override
    public String getUserIdSecurityContext() {
        return getSubClaim();
    }
}
