package com.softwareproject.backend.service;

import com.softwareproject.backend.model.Dump;
import com.softwareproject.backend.repository.DumpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DumpService {

    @Autowired
    DumpRepository dumpRepository;

    public List<Dump> getAllDumps(){

        return dumpRepository.findAll();
    }
}
