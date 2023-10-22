package ru.sber.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageOrder {
    private int page;
    private int pageSize;
}
