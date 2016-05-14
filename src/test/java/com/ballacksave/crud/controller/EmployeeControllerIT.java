package com.ballacksave.crud.controller;

import com.ballacksave.crud.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@DatabaseSetup("classpath:/dbunit/dummy_employee.xml")
public class EmployeeControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before

    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void should_returnAllEmployee_when_findAll() throws Exception {
        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("123")))
                .andExpect(jsonPath("$[0].name", is("ronny")))
                .andExpect(jsonPath("$[1].id", is("234")))
                .andExpect(jsonPath("$[1].name", is("ballack")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void should_returnStatusCreated_when_createNewEmployee() throws Exception {
        //given
        String name = "ballacksave";
        String employee = String.format("{\"name\":\"%s\"}", name);

        //when
        mockMvc.perform(post("/employee")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee)
        )
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("ballacksave")))
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void should_returnStatusOk_when_updateEmployee() throws Exception {
        //given
        int id = 234;
        String name = "ballacksave";
        String employee = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id, name);

        //when
        mockMvc.perform(put("/employee/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee)
        )
                //then
                .andExpect(status().isOk())
        ;
    }

    @Test
    @Transactional
    @Rollback
    public void should_returnStatusOk_when_deleteEmployee() throws Exception {
        //given
        int id = 234;

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