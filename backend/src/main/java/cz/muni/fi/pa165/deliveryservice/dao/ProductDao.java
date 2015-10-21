package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.entity.Product;

import java.util.List;

/**
 * Created by Pavel on 21. 10. 2015.
 */

public interface ProductDao {

    void create(Product p);

    Product findById(Long id);

    List<Product> findAll();

    void remove(Product p);
}
