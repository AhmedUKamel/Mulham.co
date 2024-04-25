package org.ahmedukamel.mulham.dto.service;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceResponse {
    private Integer id;

    private String name;

    private String description;

    private BigDecimal cost;

    private Integer categoryId;

    private String categoryName;
}