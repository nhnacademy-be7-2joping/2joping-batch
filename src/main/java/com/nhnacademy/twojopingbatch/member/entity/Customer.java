package com.nhnacademy.twojopingbatch.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Setter
    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String email;


}
