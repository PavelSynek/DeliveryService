package cz.muni.fi.pa165.deliveryservice.rest.controllers;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.api.rest.util.ResourceAlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.rest.util.ResourceNotFoundException;
import cz.muni.fi.pa165.deliveryservice.api.rest.util.UriSpecification;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;


import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * REST Controller for Products
 *
 * @author brossi
 */
@RestController
@RequestMapping(UriSpecification.ROOT_URI_PRODUCTS)
public class ProductsController {

    final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    @Inject
    private ProductFacade productFacade;

    /**
     * Get list of Products curl -i -X GET
     * http://localhost:8080/pa165/rest/products
     *
     * @return ProductDTO
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ProductDTO> getAllProducts() {
        logger.debug("rest getAllProducts()");
        return Collections.unmodifiableList(productFacade.getAllProducts());
    }

    /**
     * Get Product by identifier id curl -i -X GET
     * http://localhost:8080/pa165/rest/products/1
     *
     * @param id identifier for a product
     * @return ProductDTO
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO getProduct(@PathVariable("id") long id) throws ResourceNotFoundException {
        logger.debug("rest getProduct({})", id);
        try {
            return productFacade.getProductWithId(id);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    /**
     * Delete one product by id curl -i -X DELETE
     * http://localhost:8080/pa165/rest/products/1
     *
     * @param id identifier for product
     * @throws ResourceNotFoundException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteProduct(@PathVariable("id") long id) throws ResourceNotFoundException {
        logger.debug("rest deleteProduct({})", id);
        try {
            productFacade.deleteProduct(id);
        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException(id);
        }
    }

    /**
     * Create a new product by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"name":"test", "price":"200", "weight":"20.5"}'
     * http://localhost:8080/pa165/rest/products/create
     *
     * @param product ProductCreateDTO with required fields for creation
     * @return the created product ProductDTO
     * @throws ResourceAlreadyExistsException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO createProduct(@RequestBody ProductCreateDTO product) throws ResourceAlreadyExistsException {

        logger.debug("rest createProduct()");

        Long id = null;

        try {
            id = productFacade.createProduct(product);
            return productFacade.getProductWithId(id);
        } catch (NotFoundException ex) {
            throw new ResourceNotFoundException(id);
        } catch (AlreadyExistsException e) {
            throw new ResourceAlreadyExistsException(id);
        }
    }

    /**
     * Update the price for one product by PUT method curl -X PUT -i -H
     * "Content-Type: application/json" --data '{"price":"16.33"}'
     * http://localhost:8080/pa165/rest/products/4
     *
     * @param id       identified of the product to be updated
     * @param newPrice va
     * @return the updated product ProductDTO
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO changePrice(@PathVariable("id") long id, @RequestBody Long newPrice) throws Exception {

        logger.debug("rest changePrice({})", id);

        try {
            ProductDTO productDTO = productFacade.getProductWithId(id);
            productDTO.setPrice(newPrice);
            return productFacade.getProductWithId(id);
        } catch (EshopServiceException esse) {
            throw new InvalidParameterException();
        }

    }

    /**
     * Add a new category by POST Method
     *
     * @param id       the identifier of the Product to have the Category added
     * @param category the category to be added
     * @return the updated product as defined by ProductDTO
     * @throws InvalidParameterException
     */
    @RequestMapping(value = "/{id}/categories", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ProductDTO addCategory(@PathVariable("id") long id, @RequestBody CategoryDTO category) throws Exception {

        logger.debug("rest addCategory({})", id);

        try {
            productFacade.addCategory(id, category.getId());
            return productFacade.getProductWithId(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}
