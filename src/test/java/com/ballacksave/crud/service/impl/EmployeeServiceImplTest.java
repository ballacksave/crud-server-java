package com.ballacksave.crud.service.impl;

import com.ballacksave.crud.config.AppConfig;
import com.ballacksave.crud.entity.Employee;
import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.repository.EmployeeRepository;
import com.ballacksave.crud.service.EmployeeService;
import com.ballacksave.utils.UUIDGen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeServiceImplTest {

    @Autowired
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UUIDGen uuidGen;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_returnAllEmployee_when_findAll() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setId("345");
        employee.setName("employee one");
        employees.add(employee);

        employee = new Employee();
        employee.setId("456");
        employee.setName("employee two");
        employees.add(employee);
        given(employeeRepository.findAll()).willReturn(employees);

        //when
        List<AjaxEmployee> ajaxEmployees = employeeService.findAll();

        //then
        assertEquals(2, ajaxEmployees.size());
        assertEquals("345", ajaxEmployees.get(0).getId());
        assertEquals("employee one", ajaxEmployees.get(0).getName());
        assertEquals("456", ajaxEmployees.get(1).getId());
        assertEquals("employee two", ajaxEmployees.get(1).getName());
    }

    @Test
    public void should_returnEmployee_when_create() {
        //given
        String id = "123-456";
        String name = "ballack";
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);
        given(uuidGen.generate()).willReturn(id);

        AjaxEmployee ajaxEmployeeInput = new AjaxEmployee();
        ajaxEmployeeInput.setName(name);

        //when
        AjaxEmployee ajaxEmployeeOutput = employeeService.save(ajaxEmployeeInput);

        //then
        assertEquals(id, ajaxEmployeeOutput.getId());
        assertEquals(name, ajaxEmployeeOutput.getName());
    }

}