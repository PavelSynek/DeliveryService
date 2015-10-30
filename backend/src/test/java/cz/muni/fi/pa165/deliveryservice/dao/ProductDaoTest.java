package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by Pavel on 21. 10. 2015.
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ProductDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ProductDao productDao;

    private Product car;
    private Product plane;

    @BeforeClass
    public void setUp() {
        car = new Product();
        car.setName("Audi");
        productDao.create(car);

        plane = new Product();
        plane.setName("Boeing");
        productDao.create(plane);

        productDao.initDBAccessHandlers();
    }

    @Test
    public void findById() {
        assertEquals(productDao.findById(car.getId()), car);
    }

    @Test
    public void findAll() {
        List<Product> expected = new ArrayList<>();
        expected.add(car);
        expected.add(plane);

        assertEquals(productDao.findAll(), expected);
    }

    @Test
    public void delete() {
        productDao.remove(plane);

        List<Product> expected = new ArrayList<>();
        expected.add(car);
        assertEquals(productDao.findAll(), expected);
    }
}
