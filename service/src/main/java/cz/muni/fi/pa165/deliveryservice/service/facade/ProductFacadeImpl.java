package cz.muni.fi.pa165.deliveryservice.service.facade;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public class ProductFacadeImpl implements ProductFacade {

    @Inject
    private ProductService productService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createProduct(ProductCreateDTO p) {
        Product mappedProduct = beanMappingService.mapTo(p, Product.class);
        Product newProduct = productService.createProduct(mappedProduct);
        return newProduct.getId();
    }

    @Override
    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return beanMappingService.mapTo(productService.findAll(), ProductDTO.class);
    }

    @Override
    public ProductDTO getProductWithId(Long id) {
        return beanMappingService.mapTo(productService.findById(id), ProductDTO.class);
    }
}