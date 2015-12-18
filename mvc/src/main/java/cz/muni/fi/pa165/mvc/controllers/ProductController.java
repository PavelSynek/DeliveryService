package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
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
import javax.validation.Valid;

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
    public String newproduct(Model model) {
        log.debug("new()");
        model.addAttribute("productDTO", new ProductDTO());
        return "product/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("productCreate") ProductCreateDTO formBean, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, HttpServletRequest req) {
        log.debug("create(productCreate={})", formBean);
        String pass = (String) req.getAttribute("password");
        if (pass.length() < 4) {
            model.addAttribute("password_error", true);
            log.trace("password_error");
            return "product/new";
        }

        //create product
        Long id = null;
        try {
            id = productFacade.createProduct(formBean);
        } catch (AlreadyExistsException e) {
            e.printStackTrace(); // TODO
        }
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "product " + id + " was created");
        return "redirect:" + uriBuilder.path("/product/detail/id={id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        log.debug("list()");

        productFacade.init();

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
