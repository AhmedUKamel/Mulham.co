package org.ahmedukamel.mulham.dto.user;

import lombok.Data;
import org.ahmedukamel.mulham.model.enumeration.Gender;
import org.ahmedukamel.mulham.model.enumeration.Provider;
import org.ahmedukamel.mulham.model.enumeration.Role;

import java.util.Collection;

@Data
public class UserResponse {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Provider provider;
    private Role role;
    private String picture;
    private String phoneNumber;
    private String dateOfBirth;
    private boolean hasPicture;
    private boolean hasPhone;
    private boolean hasDateOfBirth;
    private boolean enabled;
    private boolean accountNonLocked;
    private Collection<String> authorities;
}