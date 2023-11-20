package com.daanielowsky.crm.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String surname;

    private String email;

    private Long phoneNumber;

    private String city;

    private String postCode;

    private String note;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private List<Activity> activities = new LinkedList<>();

    @ManyToOne
    private Employee employee;


}
