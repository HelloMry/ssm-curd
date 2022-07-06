package com.ailian.crud.service;

import com.ailian.crud.bean.Department;
import com.ailian.crud.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author aiLian
 * @date on 2022-07-05 00:00:43
 * @describe
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;


    public List<Department> getDepts() {
       return departmentMapper.selectByExample(null);
    }
}
