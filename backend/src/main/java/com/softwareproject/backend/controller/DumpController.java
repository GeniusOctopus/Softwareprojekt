package com.softwareproject.backend.controller;

import com.softwareproject.backend.model.Dump;
import com.softwareproject.backend.service.DumpService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(name = "Dump", description = "Beispielendpunkte", group = "Dump")
@CrossOrigin(origins = "http://localhost:4200/")
@Controller
@RequestMapping("/dump")
public class DumpController {

    @Autowired
    DumpService dumpService;

    @ApiMethod(description = "Gibt nur Beispiele zurück")
    @RequestMapping(value = "/dumps", method = RequestMethod.GET)
    public @ResponseBody List<Dump> getAllProducts(){

        return dumpService.getAllDumps();
    }
}
