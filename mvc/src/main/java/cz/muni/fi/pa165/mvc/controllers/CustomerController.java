package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.CustomerDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.PersonDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.OrderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by pavelsynek, tomasmilota on 18/12/15.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private OrderFacade orderFacade;

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newCustomer(Model model) {
        log.debug("new()");
        model.addAttribute("customerDTO", new CustomerDTO());
        return "customer/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("productCreate") CustomerDTO formBean, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("create(customerCreate={})", formBean);
        String pass = (String) req.getAttribute("password");
        if (pass.length() < 4) {
            model.addAttribute("password_error", true);
            log.trace("password_error");
            return "customer/new";
        }

        //create customer
        Long id = customerFacade.create(formBean, pass);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Customer " + id + " was created");
        return "redirect:" + uriBuilder.path("/customer/detail/id={id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        log.debug("list()");

        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() == EmployeeDTO.class) {
            model.addAttribute("customers", customerFacade.getAll());
        }
        if (user.getClass() == CustomerDTO.class) {
            ArrayList<CustomerDTO> customers = new ArrayList<>();
            customers.add(customerFacade.findById(user.getId()));
            model.addAttribute("customers", customers);
        }

        return "customer/list";
    }

    @RequestMapping(value = "/list/name/{firstName}+{surname}", method = RequestMethod.GET)
    public String listByName(@PathVariable String name, @PathVariable String surname, Model model) {
        log.debug("listByName({}+{})", name, surname);
        model.addAttribute("customers", customerFacade.findByName(name + " " + surname));
        return "customer/list";
    }

    @RequestMapping(value = "/detail/email/{email}", method = RequestMethod.GET)
    public String detailByEmail(@PathVariable String email, Model model) {
        log.debug("detailByEmail({})", email);
        CustomerDTO c = customerFacade.findByEmail(email);
        model.addAttribute("customer", c);
        model.addAttribute("orders", orderFacade.findByCustomer(c.getId()));
        return "customer/detail";
    }

    @RequestMapping(value = "/detail/id={id}", method = RequestMethod.GET)
    public String detailById(@PathVariable long id, Model model, HttpServletRequest request) {
        log.trace("detailById({})", id);

        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() != EmployeeDTO.class && !user.getId().equals(id)) {
            return "home";  // TODO
        }
        CustomerDTO c = customerFacade.findById(id);
        model.addAttribute("customer", c);
        model.addAttribute("orders", c.getOrders());
        return "customer/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        CustomerDTO customerDTO = customerFacade.findById(id);
        customerFacade.delete(id);
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Customer \"" +
                customerDTO.getFirstName() + " " + customerDTO.getSurname() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/customer/list").toUriString();
    }
}
