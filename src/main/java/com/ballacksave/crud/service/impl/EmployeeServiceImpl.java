package com.ballacksave.crud.service.impl;

import com.ballacksave.crud.model.AjaxEmployee;
import com.ballacksave.crud.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public List<AjaxEmployee> findAll() {
        throw new UnsupportedOperationException(); // Bos ini harus diimplement donk. Brani buat hrs brani bertanggung jawab.
    }
}
