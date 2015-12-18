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
        Employee ee = employee("Employee", "Ne-admin", "admin@admin.cz", "112567000");
        Customer c = customer("Pavel", "Synek", "pavel.synek@gmail.com", "112567000");

        Product car = product("Car", 10000, 10000);
        Product plane = product("Plane", 1000000, 10000000);
        Product train = product("Train", 1000, 1000000);

        //Order order = order(c, e, car, plane, train);
    }

    private Employee employee(String firstname, String surname, String email, String phone) {
        Employee employee = new Employee();
        employee.setFirstName(firstname);
        employee.setSurname(surname);
        employee.setEmail(email);
        employee.setRegistrationDate(getRandomDate());
        employee.setPhone(phone);
        employeeService.create(employee, "dsadas");
        return employee;
    }

    private Customer customer(String firstname, String surname, String email, String phone) {
        Customer customer = new Customer();
        customer.setFirstName(firstname);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setRegistrationDate(getRandomDate());
        customer.setPhone(phone);
        customerService.create(customer, "password");
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
        try {
            productService.createProduct(product);
        } catch (AlreadyExistsException e) {
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

    /*@Override
    public void loadData() throws IOException {

        Product amber = product("Amber", "", "amber.jpg", JPEG, 10000, Currency.CZK, Color.UNDEFINED, presents);
        Product blackberries = product("Blackberries", "", "blackberries.jpg", JPEG, 20, Currency.CZK, Color.BLACK, food);
        Product blueberries = product("Blueberries", "", "blueberries.jpg", JPEG, 25, Currency.CZK, Color.BLUE, food);
        Product chilli = product("Chilli", "", "chilli.jpg", JPEG, 15, Currency.CZK, Color.RED, food);
        Product clamps = product("Clamps", "", "clamps.jpg", JPEG, 5, Currency.CZK, Color.UNDEFINED, office);
        Product cofee = product("Coffee", "", "coffee.jpg", JPEG, 100, Currency.CZK, Color.BROWN, food, office, presents);
        Product mouse = product("Mouse", "", "computer-mouse.jpg", JPEG, 200, Currency.CZK, Color.BLACK, office);
        Product cow = product("Cow", "", "cow.jpg", JPEG, 199, Currency.CZK, Color.BROWN, toys);
        Product crayons = product("Crayons", "", "crayons.jpg", JPEG, 10, Currency.CZK, Color.BLACK, office);
        Product diamonds = product("Diamond", "Diamonds are forever.", "diamond.jpg", JPEG, 50000, Currency.EUR, Color.WHITE, presents);
        Product figs = product("Figs", "", "figs.jpg", JPEG, 100, Currency.CZK, Color.BROWN, food);
        Product gold = product("Gold", "", "gold.jpg", JPEG, 15000, Currency.CZK, Color.YELLOW, presents);
        Product horse = product("Horse", "", "horse.jpg", JPEG, 299, Currency.CZK, Color.BROWN, toys);
        Product limes = product("Limes", "", "limes.jpg", JPEG, 60, Currency.CZK, Color.GREEN, food);
        Product mixedFlowers = product("Mixed flowers", "", "mixed-flowers.jpg", JPEG, 300, Currency.CZK, Color.UNDEFINED, flowers);
        Product monitor = product("Monitor", "", "monitor.jpg", JPEG, 10000, Currency.CZK, Color.BLACK, office);
        Product narcissus = product("Narcissus", "", "narcissus.jpg", JPEG, 250, Currency.CZK, Color.YELLOW, flowers);
        Product notebook = product("Notebook", "", "notebook.jpg", JPEG, 20000, Currency.CZK, Color.BLACK, office);
        Product oranges = product("Oranges", "", "oranges.jpg", JPEG, 70, Currency.CZK, Color.ORANGE, food);
        Product pears = product("Pears", "", "pears.jpg", JPEG, 85, Currency.CZK, Color.GREEN, food);
        Product peppers = product("Peppers", "", "peppers.jpg", JPEG, 60, Currency.CZK, Color.UNDEFINED, food);
        Product pins = product("Pins", "", "pins.jpg", JPEG, 30, Currency.CZK, Color.UNDEFINED, office);
        Product raspberries = product("Raspberries", "", "raspberries.jpg", JPEG, 90, Currency.CZK, Color.PINK, food);
        Product duck = product("Rubber ducks", "", "rubber-duckies.jpg", JPEG, 99, Currency.CZK, Color.YELLOW, toys);
        Product strawberries = product("Strawberries", "Very tasty and exceptionally red strawberries.", "strawberries.jpg", JPEG, 80, Currency.CZK, Color.RED, food);
        Product tulip = product("Tulip", "", "tulip.jpg", JPEG, 220, Currency.CZK, Color.RED, flowers);
        log.info("Loaded eShop categories and products.");
        User pepa = user("heslo", "Pepa", "Novák", "pepa@novak.cz", "603123456", toDate(2015, 5, 12), "Horní Kotěhůlky 12");
        User jiri = user("heslo", "Jiří", "Dvořák", "jiri@dvorak.cz", "603789123", toDate(2015, 3, 5), "Dolní Lhota 56");
        User eva = user("heslo", "Eva", "Adamová", "eva@adamova.cz", "603457890", toDate(2015, 6, 5), "Zadní Polná 44");
        User admin = user("admin", "Josef", "Administrátor", "admin@eshop.com", "9999999999", toDate(2014, 12, 31), "Šumavská 15, Brno");
        log.info("Loaded eShop users.");
        order(pepa, daysBeforeNow(10), OrderState.DONE, orderItem(duck, 5), orderItem(diamonds, 1));
        order(pepa, daysBeforeNow(6), OrderState.SHIPPED, orderItem(horse, 3), orderItem(cow, 3));
        order(pepa, daysBeforeNow(3), OrderState.CANCELED, orderItem(duck, 10), orderItem(horse, 1));
        order(pepa, daysBeforeNow(2), OrderState.RECEIVED, orderItem(duck, 10), orderItem(horse, 1));
        order(jiri, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 1), orderItem(horse, 1), orderItem(cow, 1));
        order(eva, daysBeforeNow(1), OrderState.RECEIVED, orderItem(duck, 15), orderItem(horse, 7), orderItem(cow, 2));
        log.info("Loaded delivery service items");
    }

    private static LocalDate daysBeforeNow(int days) {
        return LocalDate.now().minusDays(days);
    }

    private static LocalDate toDate(int year, int month, int day) {
        return LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toLocalDate();
    }

    private Order order(User user, Date created, OrderState state, OrderItem... items) {
        Order o = new Order();
        o.setUser(user);
        o.setCreated(created);
        o.setState(state);
        for (OrderItem item : items) {
            o.addOrderItem(item);
        }
        orderService.createOrder(o);
        return o;
    }

    private OrderItem orderItem(Product product, int amount) {
        OrderItem oi = new OrderItem();
        oi.setProduct(product);
        oi.setPricePerItem(product.getCurrentPrice());
        oi.setAmount(amount);
        return oi;
    }

    private User user(String password, String givenName, String surname, String email, String phone, Date joined, String address) {
        User u = new User();
        u.setGivenName(givenName);
        u.setSurname(surname);
        u.setEmail(email);
        u.setPhone(phone);
        u.setAddress(address);
        u.setJoinedDate(joined);
        if (password.equals("admin")) u.setAdmin(true);
        userService.registerUser(u, password);
        return u;
    }

    private Category category(String name) {
        Category c = new Category();
        c.setName(name);
        categoryService.create(c);
        return c;
    }

    private static Random random = new Random();

    private Price price(long price, ZonedDateTime priceStart, Currency currency) {
        Price p = new Price();
        p.setCurrency(currency);
        p.setPriceStart(Date.from(priceStart.toInstant()));
        p.setValue(BigDecimal.valueOf(price));
        return p;
    }

    private Product product(String name, String description, String imageFile, String mimeType, long price, Currency currency, Color color, Category... categories) throws IOException {
        Product pr = new Product();
        pr.setAddedDate(da);
        pr.setName(name);
        pr.setDescription(description);

        //set curent price as 7 days ago
        ZonedDateTime day = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(7);
        Price p = price(price, day, currency);
        pr.setCurrentPrice(p);
        pr.addHistoricalPrice(p);
        //generate randomly higher historical prices
        for (int i = 0, n = 1 + random.nextInt(8); i <= n; i++) {
            day = day.minusMonths(1);
            price = price + 1 + random.nextInt((int) (price / 5l));
            pr.addHistoricalPrice(price(price, day, currency));
        }
        pr.setAddedDate(Date.from(day.toInstant()));

        pr.setImage(readImage(imageFile));
        pr.setImageMimeType(mimeType);
        productService.createProduct(pr);
        return pr;
    }*/
}
