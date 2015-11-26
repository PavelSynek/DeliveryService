package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.dao.ProductDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;

    @Override
    public Product createProduct(Product product) {
        productDao.create(product);
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productDao.remove(findById(id));
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id);
    }
}
