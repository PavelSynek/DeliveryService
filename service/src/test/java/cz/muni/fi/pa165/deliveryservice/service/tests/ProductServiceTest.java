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

    private Product car;
    private Product plane;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createProducts() {
        car = new Product();
        car.setId(1l);
        car.setName("Car");
        car.setPrice(1000000);
        car.setAddedDate(LocalDate.of(2015, 1, 1));

        plane = new Product();
        plane.setId(2l);
        plane.setName("Plane");
        plane.setPrice(100000000);
        plane.setAddedDate(LocalDate.of(2014, 12, 24));
    }

    @Test
    public void create() {
        productService.createProduct(car);
        verify(productDao).create(car);
    }

    @Test
    public void delete() {
        when(productDao.findById(car.getId())).thenReturn(car);
        productService.deleteProduct(car.getId());
        verify(productDao).remove(car);
    }

    @Test
    public void findById() {
        when(productDao.findById(car.getId())).thenReturn(car);
        Product found = productService.findById(car.getId());
        assertEquals(found, car);
    }

    @Test
    public void findAll() {
        List<Product> expected = new ArrayList<>();
        expected.add(car);
        expected.add(plane);
        when(productDao.findAll()).thenReturn(expected);

        List<Product> found = productService.findAll();
        assertEquals(found, expected);
    }
}
