package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.EmployeeDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.EmployeeFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.OrderFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Tomas Milota on 18.12.2015.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    final static Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeFacade employeeFacade;

    @Autowired
    private OrderFacade orderFacade;

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newEmployee(Model model) {
        log.debug("new()");
        model.addAttribute("employeeDTO", new EmployeeDTO());
        return "employee/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("employeeCreate") EmployeeDTO formBean, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("create(employeeCreate={})", formBean);
        String pass = (String) req.getAttribute("password");
        if (pass.length() < 4) {
            model.addAttribute("password_error", true);
            log.trace("password_error");
            return "employee/new";
        }

        Long id = employeeFacade.create(formBean, pass);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Employee " + id + " was created");
        return "redirect:" + uriBuilder.path("/employee/detail/id={id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");

        model.addAttribute("employees", employeeFacade.getAll());
        return "employee/list";
    }

    @RequestMapping(value = "/list/name={firstName}+{surname}", method = RequestMethod.GET)
    public String listByName(@PathVariable String firstName, @PathVariable String surname, Model model) {
        log.debug("listByName({}+{})", firstName, surname);
        model.addAttribute("employees", employeeFacade.findByName(firstName + " " + surname));
        return "employee/list";
    }

    @RequestMapping(value = "/detail/email={email}", method = RequestMethod.GET)
    public String detailByEmail(@PathVariable String email, Model model) {
        log.debug("detailByEmail({})", email);
        EmployeeDTO e = employeeFacade.findByEmail(email);
        model.addAttribute("employee", e);
        model.addAttribute("orders", orderFacade.findByEmployee(e.getId()));
        return "employee/detail";
    }

    @RequestMapping(value = "/detail/id={id}", method = RequestMethod.GET)
    public String detailById(@PathVariable long id, Model model) {
        log.trace("detailById({})", id);

        EmployeeDTO e = employeeFacade.findById(id);
        model.addAttribute("employee", e);
        model.addAttribute("orders", orderFacade.findByEmployee(e.getId()));
        return "employee/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        EmployeeDTO employeeDTO = employeeFacade.findById(id);
        employeeFacade.delete(id);
        log.debug("delete({})", id);
        redirectAttributes.addFlashAttribute("alert_success", "Employee \"" +
                employeeDTO.getFirstName() + " " + employeeDTO.getSurname() + "\" was deleted.");
        return "redirect:" + uriBuilder.path("/employee/list").toUriString();
    }
}
