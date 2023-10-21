package ru.sber.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageOrder {
    int page;
    int pageSize;
}
