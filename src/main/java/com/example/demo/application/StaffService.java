package com.example.demo.application;

import com.example.demo.domain.Staff;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Transactional
    public Staff createStaff(Staff staff){
       return staffRepository.save(staff);
    }
    @Transactional
    public void removeStaff(long id){
        staffRepository.deleteById(id);
    }
    @Transactional
    public Staff searchStaff(long id){
        return staffRepository.findById(id).get();
    }
    @Transactional
    public List<Staff> findByName(String name){
        return staffRepository.findByName(name);
    }
    public Iterable<Staff> all (){
        Iterable<Staff> staffs = staffRepository.findAll();
        return staffs;
    }
}
