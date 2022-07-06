package com.ailian.crud.controller;

import com.ailian.crud.bean.Department;
import com.ailian.crud.bean.Msg;
import com.ailian.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author aiLian
 * @date on 2022-07-04 23:59:41
 * @describe
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDepts() {
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
