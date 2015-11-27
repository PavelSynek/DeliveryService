package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public interface ProductFacade {

    /**
     * Create new product
     */
    Long createProduct(ProductCreateDTO p);

    /**
     * Remove product with given id
     */
    void deleteProduct(Long productId);

    /**
     * Get all products
     */
    List<ProductDTO> getAllProducts();

    /**
     * Gets product with given id
     */
    ProductDTO getProductWithId(Long id);
}
