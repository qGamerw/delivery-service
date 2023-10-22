package ru.sber.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatus {
    private Long id;

    private String status;
}
