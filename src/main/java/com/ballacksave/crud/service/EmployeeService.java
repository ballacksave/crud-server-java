package com.ballacksave.crud.service;

import com.ballacksave.crud.model.AjaxEmployee;

import java.util.List;

public interface EmployeeService {
    List<AjaxEmployee> findAll();
}
