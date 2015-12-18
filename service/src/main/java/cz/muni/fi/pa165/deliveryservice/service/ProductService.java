package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.FailedUpdateException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 *
 * @author Pavel
 * @author Matej Leško
 */
@Service
public interface ProductService {

    /**
     * Creates new product
     *
     * @param product product to be added
     */
    Product createProduct(Product product) throws AlreadyExistsException;

    /**
     * Removes product with given id
     *
     * @param id id of product to be deleted
     */
    void deleteProduct(Long id) throws NotFoundException;

    /**
     * Gets all products
     */
    List<Product> findAll();

    /**
     * Gets product with given id
     *
     * @param id id of product to be found
     */
    Product findById(Long id) throws NotFoundException;

    /**
     * Update product, use if product already exists in database, exception will be thrown otherwise
     *
     * @param updatedProduct product that has changed attributes to reflect to its entity
     */
    void updateProduct(Product updatedProduct) throws FailedUpdateException;

    /**
     * This method is mandatory for proper initialization of lower layers.
     * Need to be called manually (gosh ... no time)
     */
    void init();
}
