package cz.muni.fi.deliveryservice.data;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidWeightException;
import cz.muni.fi.pa165.deliveryservice.api.enums.OrderState;
import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.FailedUpdateException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;
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
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Pavel
 * @author Matej Leško
 */
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
    public void loadData() throws AlreadyExistsException, NotFoundException, FailedUpdateException {
        Employee e = employee("Admin", "Admin", "admin@admin.cz", "112567000");
        Customer c = customer("Pavel", "Synek", "pavel.synek@gmail.com", "112567000");
        customer("Customer", "Customer", "customer@customer.cz", "112567000");

        Product car = product("Car", 4000, 10720);
        Product plane = product("Plane", 10098600, 800700250);
        Product train = product("Train", 1520, 1073100);

        Order order = order(c, e, car, plane, train);
    }

    private Employee employee(String firstname, String surname, String email, String phone) {
        Employee employee = new Employee();
        String password = firstname + "_employee";
        employee.setFirstName(firstname);
        employee.setSurname(surname);
        employee.setEmail(email);
        employee.setRegistrationDate(getRandomDate());
        employee.setPhone(phone);
        employeeService.create(employee, password);
        return employeeService.findById(employee.getId());
    }

    private Customer customer(String firstname, String surname, String email, String phone) {
        String password = firstname + "_customer";
        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setRegistrationDate(getRandomDate());
        customer.setPhone(phone);
        customerService.create(customer, password);
        return customerService.findById(customer.getId());
    }

    private Product product(String name, long price, long weight) throws AlreadyExistsException, NotFoundException {
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

    private Order order(Customer customer, Employee employee, Product... products) throws NotFoundException, AlreadyExistsException, FailedUpdateException {
        Order order = new Order();

        order.setCreated(getRandomDate());
        order.setState(OrderState.SHIPPED);
        order.setEmployee(employee);
        order.setCustomer(customer);

//        try {
        orderService.createOrder(order);
        for (Product product : products) {
            order.addProduct(product);
            productService.createProduct(product);
//            Product ret = productService.findById(product.getId());
        }
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        employee.setOrders(orders);
        employeeService.update(employee);
        customer.setOrders(orders);
        customerService.update(customer);
        orderService.updateOrder(order);
//        } catch (AlreadyExistsException e) {
//            // ignore
//        }

        return orderService.findById(order.getId());
    }

    private static Random randomGenerator = new Random();

    private LocalDate getRandomDate() {
        return LocalDate.of(random(2000, 2014), random(1, 12), random(1, 28));
    }

    public static int random(int min, int max) {
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

}
