package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
@Service
public interface ProductService {

    Product createProduct(Product product);

    void deleteProduct(Long id);

    List<Product> findAll();

    Product findById(Long id);
}
