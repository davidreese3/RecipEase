package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.glossary.GlossaryEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class GlossaryController {
    @Autowired
    AppService appService;

    @RequestMapping(value ="/glossary/view", method = RequestMethod.GET)
    public String displayGlossary(Model model){
        List<GlossaryEntry> glossary = appService.getAllGlossaryEntries();
        model.addAttribute("glossary",glossary);
        return "glossary/viewGlossary";
    }
}
