package com.ballacksave.crud.controller;

import com.ballacksave.crud.config.AppConfig;
import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void should_returnAllEmployee_when_findAll() throws Exception {
        //given
        ArrayList<AjaxEmployee> ajaxEmployees = new ArrayList<>();
        AjaxEmployee ajaxEmployee = new AjaxEmployee();
        ajaxEmployee.setId("123");
        ajaxEmployee.setName("ronny");
        ajaxEmployees.add(ajaxEmployee);

        ajaxEmployee = new AjaxEmployee();
        ajaxEmployee.setId("234");
        ajaxEmployee.setName("ballacksave");
        ajaxEmployees.add(ajaxEmployee);
        given(employeeService.findAll()).willReturn(ajaxEmployees);

        //when
        mockMvc.perform(get("/employee"))

                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("123")))
                .andExpect(jsonPath("$[0].name", is("ronny")))
                .andExpect(jsonPath("$[1].id", is("234")))
                .andExpect(jsonPath("$[1].name", is("ballacksave")))
        ;

    }

}