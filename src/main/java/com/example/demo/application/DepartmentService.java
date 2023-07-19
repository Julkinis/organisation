package com.example.demo.application;

import com.example.demo.domain.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository ;
    @Transactional
    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }
    @Transactional
    public boolean removeDepartment(long id){
        Department dep = departmentRepository.findById(id).get();
        if (dep != null && dep.getCountStaff()==0){
            departmentRepository.deleteById(id);
            return true;
        }
        else { return false;}
    }
    @Transactional
    public Department searchDepartment(long id){
        return departmentRepository.findById(id).get();
    }

    @Transactional
    public List<Department> findByName(String name){
        return departmentRepository.findByName(name);
    }

    public Iterable<Department> all (){
        Iterable<Department> departments = departmentRepository.findAll();
        return departments;
    }
}
