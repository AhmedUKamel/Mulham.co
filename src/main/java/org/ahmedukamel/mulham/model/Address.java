package org.ahmedukamel.mulham.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
    public Double latitude;
    public Double longitude;
    public String country;
    public String city;
    public String street;
    public String zip;
}