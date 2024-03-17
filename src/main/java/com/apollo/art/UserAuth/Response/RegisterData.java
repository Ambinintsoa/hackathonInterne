package com.apollo.art.UserAuth.Response;

import com.apollo.art.UserAuth.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterData {

    Role[] roles;
    // List<Service> services;
    // List<Supplier> supplier;

}
