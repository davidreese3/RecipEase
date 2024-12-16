package com.thesis.recipease.controllers;

import com.thesis.recipease.db.AppService;
import com.thesis.recipease.util.validator.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {
    @Autowired
    private ProfileValidator profileValidator;
    @Autowired
    private AppService appService;
}
