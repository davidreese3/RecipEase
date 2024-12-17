package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.SubstitutionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SubstitutionController {
    @Autowired
    private AppService appService;

    @RequestMapping(value = "/substitution/view" , method = RequestMethod.GET)
    public String displayKnownSubstitition(Model model){
        List<SubstitutionEntry> substitutions = appService.getAllSubstitutionEntries();
        model.addAttribute("substitutions",substitutions);
        return "substitution/knownSubstitutions";
    }


}
