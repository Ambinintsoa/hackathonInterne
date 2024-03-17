package com.apollo.art.UserAuth.Request;

import java.sql.Date;

import com.apollo.art.UserAuth.Models.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Integer gender;
    private Date dtn;
    private Profile profil;
    private String pdp;
    private String telephone;

}
