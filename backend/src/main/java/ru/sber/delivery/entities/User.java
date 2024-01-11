package ru.sber.delivery.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.delivery.entities.enum_model.EStatusCourier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Пользователь
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id_user")

    private String id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EStatusCourier status;

    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    public User(String id) {
        this.id = id;
    }

    public User(EStatusCourier status, BigDecimal latitude, BigDecimal longitude) {

        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
