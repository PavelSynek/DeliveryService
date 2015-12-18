package cz.muni.fi.deliveryservice.data;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidWeightException;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Product;
import cz.muni.fi.pa165.deliveryservice.service.CustomerService;
import cz.muni.fi.pa165.deliveryservice.service.EmployeeService;
import cz.muni.fi.pa165.deliveryservice.service.OrderService;
import cz.muni.fi.pa165.deliveryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Random;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @Override
    public void loadData() {
        Employee e = employee("Admin", "Admin", "admin@admin.cz", "112567000");
        Customer c = customer("Pavel", "Synek", "pavel.synek@gmail.com", "112567000");

        Product car = product("Car", 10000, 10000);
        Product plane = product("Plane", 1000000, 10000000);
        Product train = product("Train", 1000, 1000000);

        Order order = order(c, e, car, plane, train);
    }

    private Employee employee(String firstname, String surname, String email, String phone) {
        Employee employee = new Employee();
        employee.setFirstName(firstname);
        employee.setSurname(surname);
        employee.setEmail(email);
        employee.setRegistrationDate(getRandomDate());
        employee.setPhone(phone);
        return employee;
    }

    private Customer customer(String firstname, String surname, String email, String phone) {
        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setRegistrationDate(getRandomDate());
        customer.setPhone(phone);
        return customer;
    }

    private Product product(String name, long price, long weight) {
        Product product = new Product();
        product.setAddedDate(getRandomDate());
        product.setName(name);
        try {
            product.setPrice(price);
            product.setWeight(weight);
        } catch (InvalidPriceException | InvalidWeightException e) {
            // ignore
        }
        return product;
    }

    private Order order(Customer customer, Employee employee, Product... products) {
        Order order = new Order();
        for (Product product : products) {
            order.addProduct(product);
        }
        order.setCreated(getRandomDate());
        order.setState(OrderState.SHIPPED);
        order.setEmployee(employee);
        order.setCustomer(customer);

        try {
            orderService.createOrder(order);
        } catch (AlreadyExistsException e) {
            // ignore
        }

        return order;
    }

    private static Random randomGenerator = new Random();

    private LocalDate getRandomDate() {
        return LocalDate.of(random(2000, 2014), random(1, 12), random(1, 28));
    }

    public static int random(int min, int max) {
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

}
