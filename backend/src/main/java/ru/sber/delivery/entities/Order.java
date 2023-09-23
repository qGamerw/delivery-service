package ru.sber.delivery.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.delivery.entities.enum_model.EStatusOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 120)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "status_order")
    @NotBlank
    private EStatusOrder statusOrder;

    @Column(name="restaurant_latitude")
    @NotBlank
    BigDecimal restaurantLatitude;

    @Column(name="restaurant_longitude")
    @NotBlank
    BigDecimal restaurantLongitude;

    @Column(name="destination_latitude")
    @NotBlank
    BigDecimal destinationLatitude;

    @Column(name="destination_longitude")
    @NotBlank
    BigDecimal destinationLongitude;

    @Column(name = "pick_up_time")
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime pickUpTime;

    @Column(name = "delivery_time")
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deliveryTime;

    @NotBlank
    @Size(max = 11)
    String clientPhone;

    @NotBlank
    @Size(max = 50)
    String clientName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
