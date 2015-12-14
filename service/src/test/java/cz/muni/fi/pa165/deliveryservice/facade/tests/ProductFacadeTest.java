package cz.muni.fi.pa165.deliveryservice.facade.tests;

import cz.muni.fi.pa165.deliveryservice.api.dto.ProductCreateDTO;
import cz.muni.fi.pa165.deliveryservice.api.dto.ProductDTO;
import cz.muni.fi.pa165.deliveryservice.api.facade.ProductFacade;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingService;
import cz.muni.fi.pa165.deliveryservice.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.deliveryservice.service.ProductService;
import cz.muni.fi.pa165.deliveryservice.service.facade.ProductFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * Created by Pavel on 27. 11. 2015.
 */
public class ProductFacadeTest {

    @InjectMocks
    private ProductFacade productFacade;

    @Mock
    private ProductService productService;

    @Mock
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private Product car;
    private Product plane;
    private ProductDTO carDTO;
    private ProductCreateDTO carCreateDTO;

    @BeforeClass
    public void setUp() {
        productFacade = new ProductFacadeImpl();

        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createProducts() {
        car = new Product();
        car.setId(1l);
        car.setName("Car");
        car.setPrice(1000000);
        car.setAddedDate(LocalDate.of(2015, 1, 1));

        carDTO = new ProductDTO();
        carDTO.setId(1l);
        carDTO.setName("Car");
        carDTO.setPrice(1000000);
        carDTO.setAddedDate(LocalDate.of(2015, 1, 1));

        carCreateDTO = new ProductCreateDTO();
        carCreateDTO.setName("Car");
        carCreateDTO.setPrice(1000000);
        carCreateDTO.setAddedDate(LocalDate.of(2015, 1, 1));

        plane = new Product();
        plane.setId(2l);
        plane.setName("Plane");
        plane.setPrice(100000000);
        plane.setAddedDate(LocalDate.of(2014, 12, 24));
    }

    @Test
    public void create() {
        productFacade.createProduct(carCreateDTO);
    }
}
