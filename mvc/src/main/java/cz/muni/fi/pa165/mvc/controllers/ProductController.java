package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by pavelsynek, tomasmilota on 18/12/15.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    final static Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductFacade productFacade;

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
        log.debug("new()");
        model.addAttribute("productCreate", new ProductCreateDTO());

        return "product/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("productCreate") ProductCreateDTO formBean,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("create(productCreate={})", formBean);

        formBean.setAddedDate(LocalDate.now());
        HttpSession session = req.getSession();
        ArrayList<ProductCreateDTO> products = (ArrayList<ProductCreateDTO>) session.getAttribute("products");
        products.add(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "product " + formBean.getName() + " was created");
        return "redirect:" + uriBuilder.path("/order/new").build().encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");

        model.addAttribute("products", productFacade.getAllProducts());
        return "product/list";
    }

    @RequestMapping(value = "/detail/id={id}", method = RequestMethod.GET)
    public String detailById(@PathVariable long id, Model model) {
        log.trace("detailById({})", id);

        ProductDTO c = null;
        try {
            c = productFacade.getProductWithId(id);
        } catch (NotFoundException e) {
            e.printStackTrace(); // TODO
        }
        model.addAttribute("product", c);
        return "product/detail";
    }
}
