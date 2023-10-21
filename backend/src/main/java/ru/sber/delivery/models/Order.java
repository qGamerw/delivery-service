package ru.sber.delivery.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Заказ
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private Long courierId;
    private String clientName;
    private String description;
    private String clientPhone;
    private String eStatusOrders;
    private LocalDateTime orderTime;
    private String address;
    private String branchAddress;
    private Integer flat;
    private Integer frontDoor;
    private Integer floor;
    private Integer weight;
    private LocalDateTime endCookingTime;
    private List<?> dishesOrders;

}