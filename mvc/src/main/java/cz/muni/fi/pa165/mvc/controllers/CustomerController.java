package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by pavelsynek on 18/12/15.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("customers", customerFacade.getAll());
        return "customer/list";
    }
}
