package com.ballacksave.crud.controller;

import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<List<AjaxEmployee>> findAll() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity<AjaxEmployee> create(@RequestBody AjaxEmployee requestBody) {
        AjaxEmployee save = employeeService.save(requestBody);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}",
            method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ResponseEntity update(
            @PathVariable("id") String id,
            @RequestBody AjaxEmployee requestBody
    ) {
        employeeService.update(id, requestBody);
        return new ResponseEntity(HttpStatus.OK);
    }

}
