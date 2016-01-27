package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.*;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.facade.CustomerFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.OrderFacade;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.FailedUpdateException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import java.util.ArrayList;
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

    @Autowired
    private CustomerFacade customerFacade;

    @Autowired
    private BeanMappingService beanMappingService;

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newOrder(Model model, HttpServletRequest req, HttpServletResponse response) {
        log.debug("new()");

        HttpSession session = req.getSession();
        if (session.getAttribute("products") == null) {
            session.setAttribute("products", new ArrayList<ProductCreateDTO>());
        }
        model.addAttribute("products", session.getAttribute("products"));
        //model.addAttribute("orderCreate", new OrderCreateDTO());

        return "order/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        OrderCreateDTO formBean = new OrderCreateDTO();
        log.debug("create(orderCreate={})", formBean);

        HttpSession session = req.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() == EmployeeDTO.class) {
            // TODO
        }
        if (user.getClass() == CustomerDTO.class) {
            CustomerDTO customer = customerFacade.findById(user.getId());
            formBean.setCustomer(customer);
        }

        List<ProductCreateDTO> products = (List<ProductCreateDTO>) session.getAttribute("products");
        List<ProductDTO> productDTOs = beanMappingService.mapTo(products, ProductDTO.class);
        formBean.setProducts(productDTOs);

        //create order
        Long id = null;
        try {
            id = orderFacade.createOrder(formBean);
        } catch (AlreadyExistsException e) {
            e.printStackTrace(); // TODO
        }

        for (ProductCreateDTO p : products) {
            try {
                p.setOrder(orderFacade.findById(id));
                productFacade.createProduct(p);
            } catch (AlreadyExistsException | NotFoundException e) {
                e.printStackTrace();
            }
        }

        CustomerDTO c = formBean.getCustomer();
        List<OrderDTO> customersOrders = c.getOrders();
        try {
            customersOrders.add(orderFacade.findById(id));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        c.setOrders(customersOrders);

        customerFacade.update(c);

        session.removeAttribute("products");

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

        OrderDTO orderDTO = null;
        try {
            orderDTO = orderFacade.findById(id);
        } catch (NotFoundException e) {
            e.printStackTrace(); // TODO
        }

        HttpSession session = request.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user.getClass() != EmployeeDTO.class) {
            if (!orderFacade.findByCustomer(user.getId()).contains(orderDTO))
                return "home";  // TODO
        }

        model.addAttribute("order", orderDTO);
        model.addAttribute("price", orderFacade.getTotalPrice(id));
        model.addAttribute("weight", orderFacade.getTotalWeight(id));
        model.addAttribute("products", orderDTO.getProducts());
        model.addAttribute("isEmployee", user instanceof EmployeeDTO);
        model.addAttribute("canShip", orderDTO.getEmployee() != null && orderDTO.getState() == OrderState.PROCESSING);
        model.addAttribute("canClose", orderDTO.getState() == OrderState.SHIPPED);
        model.addAttribute("isClosed", orderDTO.getState() == OrderState.CLOSED);
        return "order/detail";
    }

    @RequestMapping(value = "/assign/{id}", method = RequestMethod.POST)
    public String assign(@PathVariable long id, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("assign()");
        HttpSession session = req.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user instanceof EmployeeDTO) {
            OrderDTO orderDTO;
            try {
                orderDTO = orderFacade.findById(id);
                orderDTO.setEmployee((EmployeeDTO) user);
                orderFacade.updateOrder(orderDTO);
                //report success
                redirectAttributes.addFlashAttribute("alert_info", "order " + id + " successfully assigned");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();

            } catch (NotFoundException | FailedUpdateException e) {
                //report failure
                redirectAttributes.addFlashAttribute("alert_danger", "assign of order " + id + " failed");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
            }

        } else {
            //report failure
            redirectAttributes.addFlashAttribute("alert_danger", "unauthorized");
            return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
        }
    }

    @RequestMapping(value = "/ship/{id}", method = RequestMethod.POST)
    public String ship(@PathVariable long id, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("ship()");
        HttpSession session = req.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user instanceof EmployeeDTO) {
            OrderDTO orderDTO;
            try {
                orderDTO = orderFacade.findById(id);
                orderFacade.shipOrder(orderDTO);
                //report success
                redirectAttributes.addFlashAttribute("alert_info", "order " + id + " successfully shipped");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();

            } catch (NotFoundException | FailedUpdateException e) {
                //report failure
                redirectAttributes.addFlashAttribute("alert_danger", "shipping of order " + id + " failed");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
            }

        } else {
            //report failure
            redirectAttributes.addFlashAttribute("alert_danger", "unauthorized");
            return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
        }
    }

    @RequestMapping(value = "/complete/{id}", method = RequestMethod.POST)
    public String close(@PathVariable long id, RedirectAttributes redirectAttributes,
                        UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("ship()");
        HttpSession session = req.getSession();
        PersonDTO user = (PersonDTO) session.getAttribute("authenticatedUser");
        if (user instanceof EmployeeDTO) {
            OrderDTO orderDTO;
            try {
                orderDTO = orderFacade.findById(id);
                orderFacade.closeOrder(orderDTO);
                //report success
                redirectAttributes.addFlashAttribute("alert_info", "order " + id + " successfully closed");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();

            } catch (NotFoundException | FailedUpdateException e) {
                //report failure
                redirectAttributes.addFlashAttribute("alert_danger", "closing of order " + id + " failed");
                return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
            }

        } else {
            //report failure
            redirectAttributes.addFlashAttribute("alert_danger", "unauthorized");
            return "redirect:" + uriBuilder.path("/order/detail/id={id}").buildAndExpand(id).encode().toUriString();
        }
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
        if (user.getClass() != EmployeeDTO.class) {
            if (!orderFacade.findByCustomer(user.getId()).contains(orderDTO))
                return "home";  // TODO
        }


        List<ProductDTO> backup = orderDTO.getProducts();
        orderFacade.cancelOrder(orderDTO);
        return "order/list";
    }
}
