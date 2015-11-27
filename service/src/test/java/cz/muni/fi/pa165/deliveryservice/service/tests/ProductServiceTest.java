package cz.muni.fi.pa165.deliveryservice.service.tests;

import cz.muni.fi.pa165.deliveryservice.persist.dao.ProductDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.service.ProductService;
import cz.muni.fi.pa165.deliveryservice.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Created by Pavel on 27. 11. 2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ProductServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ProductDao productDao;

    @Autowired
    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createProducts() {
        product1 = new Product();
        product1.setId(1l);
        product1.setName("Car");
        product1.setPrice(1000000);
        product1.setAddedDate(LocalDate.of(2015, 1, 1));

        product2 = new Product();
        product1.setId(2l);
        product2.setName("Plane");
        product2.setPrice(100000000);
        product2.setAddedDate(LocalDate.of(2014, 12, 24));
    }

    @Test
    public void create() {
        productService.createProduct(product1);
        verify(productDao).create(product1);
    }

    @Test
    public void delete() {
        when(productDao.findById(product1.getId())).thenReturn(product1);
        productService.deleteProduct(product1.getId());
        verify(productDao).remove(product1);
    }

    @Test
    public void findById() {
        when(productDao.findById(product1.getId())).thenReturn(product1);
        Product found = productService.findById(product1.getId());
        assertEquals(found, product1);
    }

    @Test
    public void findAll() {
        List<Product> expected = new ArrayList<>();
        expected.add(product1);
        expected.add(product2);
        when(productDao.findAll()).thenReturn(expected);

        List<Product> found = productService.findAll();
        assertEquals(found, expected);
    }
}
