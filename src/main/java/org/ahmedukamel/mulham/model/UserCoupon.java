package org.ahmedukamel.mulham.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(value = UserCoupon.UserCouponId.class)
public class UserCoupon {
    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Coupon coupon;

    @OneToOne
    private Booking booking;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCouponId implements Serializable {
        private User user;
        private Coupon coupon;
    }
}