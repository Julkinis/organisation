package com.example.demo.application;


import com.example.demo.domain.Organisation;
import com.example.demo.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository ;

    @Transactional
    public Organisation createOrganisation(Organisation organisation){
        return organisationRepository.save(organisation);
    }
    @Transactional
    public void removeOrganisation(long id){
        organisationRepository.deleteById(id);
    }
    @Transactional
    public Organisation searchOrganisation(long id){
        return organisationRepository.findById(id).get();
    }
    @Transactional
    public List<Organisation> findByName(String name){
        return organisationRepository.findByName(name);
    }
    public Iterable<Organisation> all (){
        Iterable<Organisation> organisations = organisationRepository.findAll();
        return organisations;
    }
}
