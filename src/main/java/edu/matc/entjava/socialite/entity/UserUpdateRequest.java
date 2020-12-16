package edu.matc.entjava.socialite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* User update request for updating info
*/
@Getter
@Setter
@ToString
public class UserUpdateRequest {

    private String password;
    private String lastName;
    private String firstName;
}
