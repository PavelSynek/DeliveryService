package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 * @author Pavel
 * @author Matej Leško
 */
public interface ProductFacade {

    /**
     * Creates new product
     *
     * @param p product to be added
     */
    Long createProduct(ProductCreateDTO p) throws AlreadyExistsException;

    /**
     * Removes product with given id
     *
     * @param productId id of product to be deleted
     */
    void deleteProduct(Long productId) throws NotFoundException;

    /**
     * Gets all products
     */
    List<ProductDTO> getAllProducts();

    /**
     * Gets product with given id
     *
     * @param id id of product to be found
     */
    ProductDTO getProductWithId(Long id) throws NotFoundException;

}
