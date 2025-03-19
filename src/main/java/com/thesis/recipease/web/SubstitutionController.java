package com.thesis.recipease.web;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.model.domain.substitution.SubstitutionEntry;
import com.thesis.recipease.util.normalizer.substitution.SubstitutionNormalizer;
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
    @Autowired
    private SubstitutionNormalizer substitutionNormalizer;

    @RequestMapping(value = "/substitution/view" , method = RequestMethod.GET)
    public String displayCommonSubstitition(Model model){
        List<SubstitutionEntry> substitutions = appService.getAllSubstitutionEntries();
        if(substitutions == null){
            model.addAttribute("error","There are no substitutions in the system.");
            return "substitution/commonSubstitutions";
        }
        substitutions = substitutionNormalizer.normalize(substitutions);
        model.addAttribute("substitutions",substitutions);
        return "substitution/commonSubstitutions";
    }


}
