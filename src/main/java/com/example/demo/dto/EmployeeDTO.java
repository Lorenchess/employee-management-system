package com.example.demo.dto;

import lombok.*;

@Value
@Builder
public class EmployeeDTO {

    private Long id;

    private String name;

    private String department;

    private double salary;


}
