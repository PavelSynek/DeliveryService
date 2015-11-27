package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
@Service
public interface ProductService {

    /**
     * Creates new product
     *
     * @param product product to be added
     */
    Product createProduct(Product product);

    /**
     * Removes product with given id
     *
     * @param id id of product to be deleted
     */
    void deleteProduct(Long id);

    /**
     * Gets all products
     */
    List<Product> findAll();

    /**
     * Gets product with given id
     *
     * @param id id of product to be found
     */
    Product findById(Long id);
}
