package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;

public interface SimpleMapper {

    EmployeeDTO entityToDTO(Employee employee);

    Employee dtoToEntity(EmployeeDTO employeeDTO);
}
