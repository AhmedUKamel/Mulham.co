package org.ahmedukamel.mulham.constant;

public interface RegexConstants {
    String PASSWORD = "^(?=.*[A-Z].)(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,32}$";
    String NAME = "^[A-Z][a-zA-ZÀ-ÿ '.-]{2,32}$";
    String PHONE = "^\\+?[0-9]\\d{1,20}$";
}