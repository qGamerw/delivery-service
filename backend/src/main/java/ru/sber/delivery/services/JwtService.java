package ru.sber.delivery.services;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    String getSubClaim();
    String getEmailClaim();
    String getPhoneNumberClaim();
    String getPreferredUsernameClaim();
    Long getCreatedTimestampClaim();
    Jwt getUserJwtTokenSecurityContext();

    /**
     * Возвращает id user из security context
     *
     * @return идентификатор пользователя
     */
    String getUserIdSecurityContext();
}
