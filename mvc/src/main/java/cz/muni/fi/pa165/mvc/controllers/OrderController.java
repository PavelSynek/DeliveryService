package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.*;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.facade.OrderFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.FailedUpdateException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import java.util.List;

/**
 * Created by pavelsynek, tomasmilota, Matej Lesko on 18/12/15.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductFacade productFacade;

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String neworder(Model model) {
        log.debug("new()");
        model.addAttribute("orderDTO", new OrderDTO());
        return "order/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("orderCreate") OrderCreateDTO formBean, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("create(orderCreate={})", formBean);
        String pass = (String) req.getAttribute("password");
        if (pass.length() < 4) {
            model.addAttribute("password_error", true);
            log.trace("password_error");
            return "order/new";
        }

        //create order
        Long id = null;
        try {
            id = orderFacade.createOrder(formBean);
        } catch (AlreadyExistsException e) {
            e.printStackTrace(); // TODO
        }
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "order " + id + " was created");
        return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        log.debug("list()");
        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() == EmployeeDTO.class) {
            model.addAttribute("orders", orderFacade.findAll());
        }
        if (user.getClass() == CustomerDTO.class) {
            model.addAttribute("orders", orderFacade.findByCustomer(user.getId()));
        }
        return "order/list";
    }

    @RequestMapping(value = "/detail/id={id}", method = RequestMethod.GET)
    public String detailById(@PathVariable long id, Model model, HttpServletRequest request) throws NotFoundException {
        log.trace("detailById({})", id);

        OrderDTO c = null;
        try {
            c = orderFacade.findById(id);
        } catch (NotFoundException e) {
            e.printStackTrace(); // TODO
        }

        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() != EmployeeDTO.class){
            if(!orderFacade.findByCustomer(user.getId()).contains(c))
                return "home";  // TODO
        }

        model.addAttribute("order", c);
        model.addAttribute("price", orderFacade.getTotalPrice(id));
        model.addAttribute("weight", orderFacade.getTotalWeight(id));
        model.addAttribute("products", c.getProducts());
        return "order/detail";
    }

    @DELETE
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String cancelById(@PathVariable long id, Model model, HttpServletRequest request) throws NotFoundException, FailedUpdateException {
        log.trace("cancelById({})", id);

        OrderDTO orderDTO = null;
        try {
            orderDTO = orderFacade.findById(id);
        } catch (NotFoundException e) {
            e.printStackTrace(); // TODO
        }

        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() != EmployeeDTO.class){
            if(!orderFacade.findByCustomer(user.getId()).contains(orderDTO))
                return "home";  // TODO
        }


        List<ProductDTO> backup = orderDTO.getProducts();
        orderFacade.cancelOrder(orderDTO);
        return "order/list";
    }
}
