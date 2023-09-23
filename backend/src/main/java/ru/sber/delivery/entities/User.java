package ru.sber.delivery.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;

    @Column(name = "date_registration")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank
    private LocalDateTime dateRegistration;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotBlank
    private EStatusCourier status;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(name = "notify")
    private boolean isNotify;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(long id, String username, String email,
                    String password, LocalDateTime dateRegistration,
                    EStatusCourier status, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateRegistration = dateRegistration;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
