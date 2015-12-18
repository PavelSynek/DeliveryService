package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.ViolentDataAccessException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.FailedUpdateException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
import cz.muni.fi.pa165.deliveryservice.persist.dao.ProductDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 *
 * @author Pavel
 * @author Matej Leško
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;

    @PostConstruct
    public void initDBAccessHandlers() {
        productDao.initDBAccessHandlers();
    }

    @Override
    public Product createProduct(Product product) throws AlreadyExistsException {
        try {
            productDao.create(product);
        } catch (ViolentDataAccessException e) {
            throw new AlreadyExistsException("Product with ID: " + product.getId() + " already exists in the database");
        }
        return product;
    }

    @Override
    public void deleteProduct(Long id) throws NotFoundException {
        productDao.remove(findById(id));
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) throws NotFoundException {
        Product product;
        try {
            product = productDao.findById(id);
        } catch (ViolentDataAccessException e) {
            throw new NotFoundException("Product with ID: " + id + " was not found in the database");
        }
        return product;
    }

    @Override
    public void updateProduct(Product updatedProduct) throws FailedUpdateException {
        try {
            productDao.update(updatedProduct);
        } catch (ViolentDataAccessException e) {
            throw new FailedUpdateException();
        }
    }
}
