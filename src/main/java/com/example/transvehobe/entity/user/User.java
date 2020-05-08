package com.example.transvehobe.entity.user;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "USERS")
@ToString(of = {"id", "email"})
@EqualsAndHashCode(of = {"id"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    // @Setter(AccessLevel.NONE)
    private String password;

    //    public void setPassword(@NotNull String password){
    //        this.password = new BCryptPasswordEncoder().encode(password);
    //    }
}
