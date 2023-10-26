package ru.sber.delivery.entities.response;

import lombok.Data;
import ru.sber.delivery.entities.enum_model.EStatusCourier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDateTime dateRegistration;
    EStatusCourier statusCourier;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean isNotify;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, String phoneNumber, LocalDateTime dateRegistration,
                       EStatusCourier statusCourier, BigDecimal latitude, BigDecimal longitude, Boolean isNotify, List<String> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateRegistration = dateRegistration;
        this.statusCourier = statusCourier;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isNotify = isNotify;

        this.roles = roles;
    }
}