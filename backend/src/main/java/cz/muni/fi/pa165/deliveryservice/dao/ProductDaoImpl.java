package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Pavel on 21. 10. 2015.
 */

@Repository
@Transactional
public class ProductDaoImpl extends Entity<Product> implements ProductDao {

    public ProductDaoImpl(Class<Product> productClass) {
        super(productClass);
    }

    public ProductDaoImpl() {
        super(Product.class);
    }
}
