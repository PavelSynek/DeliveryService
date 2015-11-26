package cz.muni.fi.pa165.deliveryservice.api.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public interface ProductFacade {

    Long createProduct(ProductCreateDTO p);

    void deleteProduct(Long productId);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductWithId(Long id);
}
