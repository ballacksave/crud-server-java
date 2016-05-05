package com.ballacksave.crud.dao;


import com.ballacksave.crud.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, String> {
}
