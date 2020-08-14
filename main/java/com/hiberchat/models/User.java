package com.hiberchat.models;

import com.hiberchat.models.enums.UserStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "USER_ID")
    private Long id;

    private String nickname;

    private String email;

    private String city;

    private String country;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "REGISTRATION_DATE")
    private LocalDate registrationDate;

    private String password;

    @Column(name = "STATUS_ID")
    private UserStatus userStatus;

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public String getPassword() {
        return password;
    }

    public class Builder {
        public Builder setId(Long id) {
            User.this.id = id;
            return this;
        }

        public Builder setNickname(String nickname) {
            User.this.nickname = nickname;
            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public Builder setCity(String city) {
            User.this.city = city;
            return this;
        }

        public Builder setCountry(String country) {
            User.this.country = country;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            User.this.birthDate = birthDate;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            User.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setUserStatus(UserStatus userStatus) {
            User.this.userStatus = userStatus;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public User build() {
            return User.this;
        }
    }

}