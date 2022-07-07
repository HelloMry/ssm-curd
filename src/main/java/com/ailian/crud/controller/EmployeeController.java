package com.ailian.crud.controller;

import com.ailian.crud.bean.Employee;
import com.ailian.crud.bean.Msg;
import com.ailian.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aiLian
 * @date on 2022-07-04 00:42:14
 * @describe
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @DeleteMapping("/emp/{id}")
    @ResponseBody
    public Msg deleteEmp(@PathVariable("id") String ids) {
        if (ids.contains("-")) {
            List<Integer> delIds = new ArrayList<>();
            String[] split = ids.split("-");
            for (String id : split) {
                delIds.add(Integer.parseInt(id));
            }
            employeeService.deleteBatch(delIds);
        } else {
            employeeService.deleteEmp(Integer.parseInt(ids));
        }
        return Msg.success();
    }

    @PutMapping("/emp/{empId}")
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    @GetMapping("/emp/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    @RequestMapping("/checkUser")
    @ResponseBody
    public Msg checkUser(String empName) {
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母组合，或者2-6位中文");
        }
        boolean b = employeeService.checkUser(empName);
        return b ? Msg.success().add("va_msg", "用户名可用") : Msg.fail().add("va_msg", "用户名不可用");
    }

    @PostMapping("/emp")
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        System.out.println(result);
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError f : fieldErrors) {
                map.put(f.getField(), f.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    @GetMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        //在查询前调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 8);
        //后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以
        //封装了详细的分页信息，包括我们查出来的数据
        //传入连续显示的页数
        return Msg.success().add("pageInfo", new PageInfo<>(emps, 5));
    }

    //@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //在查询前调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        //后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以
        //封装了详细的分页信息，包括我们查出来的数据
        //传入连续显示的页数
        PageInfo<Employee> pageInfo = new PageInfo<>(emps, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }
}
