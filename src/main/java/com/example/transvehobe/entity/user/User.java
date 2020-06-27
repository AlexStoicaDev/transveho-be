package com.example.transvehobe.entity.user;

import com.example.transvehobe.common.enums.UserStatus;
import com.example.transvehobe.entity.role.UserRole;
import com.example.transvehobe.entity.transfer.Transfer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @Email
    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Size(max = 12)
    private String lastName;

    @Size(max = 120)
    private String firstName;

    @Size(max = 12)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String drivingLicenseCategory;

    private String spokenLanguage;

    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Transfer> transfers = new ArrayList<>();
}
