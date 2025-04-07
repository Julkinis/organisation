package com.example.demo.controller;

import com.example.demo.application.DepartmentService;
import com.example.demo.application.OrganisationService;
import com.example.demo.application.StaffService;
import com.example.demo.domain.Department;
import com.example.demo.domain.Organisation;
import com.example.demo.domain.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StaffService staffService;
    @GetMapping("/department")
    public String mainDep(@RequestParam(required = false) String filter,
                          @RequestParam(required = false)  Long depId,
                          @RequestParam(required = false)  Long staffId,
                          Model model) {
        Iterable<Department> departments = departmentService.all();
        Iterable<Staff> staff = staffService.all();
        if (filter != null && !filter.isEmpty()) {
            departments = departmentService.findByName(filter);
        } else {
            departments = departmentService.all();
        }
        if (depId != null && staffId != null) {
            Staff boss = staffService.searchStaff(staffId);
            Department department = departmentService.searchDepartment(depId);
            department.setBoss(boss);
            departmentService.createDepartment(department);
        }
        model.addAttribute("departments", departments);
        model.addAttribute("filter", filter);
        model.addAttribute("staff", staff);
        return "department";
    }

    @PostMapping("/department")
    public String addDep(@RequestParam String name,
                      @RequestParam String rooms,
                      Map<String, Object> model) {
        Department department = new Department(name, rooms);
        departmentService.createDepartment(department);
        Iterable<Department> departments = departmentService.all();
        model.put("departments", departments);
        Iterable<Staff> staff = staffService.all();
        model.put("staff", staff);
        return "department";
    }
    @PostMapping("/deleteDepartment")
    public String delOrg(@RequestParam(required = false) String name,
                         Map<String, Object> model) {
        List<Department> departments = departmentService.findByName(name);
        for (Department department: departments){
            if (!departmentService.removeDepartment(department.getId())){
                model.put("message", "Удаление невозможно");
                return "deletionError";
            }

        }
        Iterable<Department> dep = departmentService.all();
        model.put("departments", dep);
        Iterable<Staff> staff = staffService.all();
        model.put("staff", staff);
        return "department";
    }

}
