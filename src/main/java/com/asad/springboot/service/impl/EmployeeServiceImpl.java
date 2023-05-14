package com.asad.springboot.service.impl;

import com.asad.springboot.exception.ResourceNotFoundException;
import com.asad.springboot.model.Employee;
import com.asad.springboot.repository.EmployeeRepository;
import com.asad.springboot.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {

//        Optional<Employee> employee = employeeRepository.findById(id);
//
//        if(employee.isPresent()) {
//            return employee.get();
//        } else {
//            throw new ResourceNotFoundException("Employee", "Id", id);
//        }

        // lambda expression
        return employeeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {

        // we need to check whether employee with given id is existed in DB or not
        Employee exisitngEmployee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Id", id)
        );

        exisitngEmployee.setFirstName(employee.getFirstName());
        exisitngEmployee.setLastName(employee.getLastName());
        exisitngEmployee.setEmail(employee.getEmail());

        // save existing employee to DB
        employeeRepository.save((exisitngEmployee));
        return exisitngEmployee;
    }
}
