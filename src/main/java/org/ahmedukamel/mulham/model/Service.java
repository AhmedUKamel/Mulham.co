package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String englishName;

    @Column(nullable = false)
    private String arabicName;

    @Column(nullable = false)
    private String englishDescription;

    @Column(nullable = false)
    private String arabicDescription;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private Integer minutes;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;
}