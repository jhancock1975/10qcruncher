package com.rootser.qcruncher.crunch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CrunchController {
	private static final String CRUNCH_VIEW_NAME = "crunch/crunch";
	
	private static Logger logger = LoggerFactory.getLogger(CrunchController.class);
	
	@RequestMapping(value = "crunch", method = RequestMethod.POST)
	public String signup(Model model) {
		//model.addAttribute(new CrunchForm());
		logger.debug("crunchin' post");
        return CRUNCH_VIEW_NAME;
	}
	
	@RequestMapping(value = "crunch")
	public String crunchers(Model model) {
		//model.addAttribute(new CrunchForm());
		logger.debug("crunchin'");
        return CRUNCH_VIEW_NAME;
	}
}
