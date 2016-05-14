package com.ballacksave.crud.controller;

import com.ballacksave.crud.config.AppConfig;
import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Mock
    private EmployeeService employeeService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        mapper = new ObjectMapper();
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

    @Test
    public void should_returnEmployee_when_create() throws Exception {
        //given
        String id = "123-346";
        String name = "ronny";
        AjaxEmployee ajaxEmployee = new AjaxEmployee();
        ajaxEmployee.setId(id);
        ajaxEmployee.setName(name);
        given(employeeService.save(Mockito.any(AjaxEmployee.class))).willReturn(ajaxEmployee);


        //when
        mockMvc.perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ajaxEmployee)))

                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(name)))
        ;
    }

    @Test
    public void should_success_when_update() throws Exception {
        //given
        String id = "123-346";
        String name = "ronny";
        AjaxEmployee ajaxEmployee = new AjaxEmployee();
        ajaxEmployee.setId(id);
        ajaxEmployee.setName(name);
        doNothing().when(employeeService).update(eq(id), Mockito.any(AjaxEmployee.class));

        //when
        mockMvc.perform(put("/employee/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ajaxEmployee))
        )
                //then
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void should_success_when_delete() throws Exception {
        //given
        String id = "123-346";
        doNothing().when(employeeService).delete(eq(id));

        //when
        mockMvc.perform(delete("/employee/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                //then
                .andExpect(status().isOk())
        ;
    }
}