package com.ballacksave.crud.service.impl;

import com.ballacksave.crud.repository.EmployeeRepository;
import com.ballacksave.crud.entity.Employee;
import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<AjaxEmployee> findAll() {
        List<AjaxEmployee> ajaxEmployees = new ArrayList<>();

        AjaxEmployee ajaxEmployee;
        for (Employee e : employeeRepository.findAll()) {
            ajaxEmployee = new AjaxEmployee();
            ajaxEmployee.setId(e.getId());
            ajaxEmployee.setName(e.getName());
            ajaxEmployees.add(ajaxEmployee);
        }

        return ajaxEmployees;
    }
}
