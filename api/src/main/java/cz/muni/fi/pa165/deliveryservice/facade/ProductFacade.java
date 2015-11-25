package cz.muni.fi.pa165.deliveryservice.facade;

import cz.muni.fi.pa165.deliveryservice.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.dto.ProductDTO;

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
