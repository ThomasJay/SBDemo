package com.thomasjayconsulting.sbdemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="id", length = 64)
    private String id;

    @Column(name="email", length = 200)
    private String email;

    @Column(name="first_name", length = 200)
    private String firstName;

    @Column(name="last_name", length = 200)
    private String lastName;

    @Column(name="password", length = 64)
    private String password;
}
