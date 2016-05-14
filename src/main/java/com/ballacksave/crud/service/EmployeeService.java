package com.ballacksave.crud.service;

import com.ballacksave.crud.model.AjaxEmployee;

import java.util.List;

public interface EmployeeService {
    List<AjaxEmployee> findAll();

    AjaxEmployee save(AjaxEmployee ajaxEmployee);

    void update(String id, AjaxEmployee ajaxEmployee);

    void delete(String id);
}
