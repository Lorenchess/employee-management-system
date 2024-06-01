package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class SimpleMapperImpl implements SimpleMapper{
    @Override
    public EmployeeDTO entityToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }

    @Override
    public Employee dtoToEntity(EmployeeDTO employeeDTO) {
        return Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .salary(employeeDTO.getSalary())
                .department(employeeDTO.getDepartment())
                .build();
    }
}
