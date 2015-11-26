package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.persist.dao.access.DBHandler;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Order;
import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 21. 10. 2015.
 *
 * @author Pavel
 * @author Matej Leško
 * @version 0.1
 */
@Repository
@Transactional
public class OrderDaoImpl extends Entity<Order> implements OrderDao {

    private DBHandler<Customer> customerDBHandler;
    private DBHandler<Employee> employeeDBHandler;

    public OrderDaoImpl(Class<Order> orderClass) {
        super(orderClass);
    }

    public OrderDaoImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findByCustomer(long customerId) {
        Customer customer = customerDBHandler.findById(customerId);
        return customer.getOrders();
    }

    @Override
    public List<Order> findByEmployee(long employeeId) {
        Employee employee = employeeDBHandler.findById(employeeId);
        return employee.getOrders();
    }

    @Override
    public List<Order> getOrdersWithState(OrderState state) {
        CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(eClass);
        Root<Order> from = criteria.from(eClass);
        criteria.select(from);
        criteria.where(builder.equal(from.get("state"), state));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Order> getOrdersCreatedBetween(LocalDate start, LocalDate end) {
        CriteriaBuilder builder = em.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(eClass);
        Root<Order> from = criteria.from(eClass);
        criteria.select(from);
        criteria.where(builder.between(from.get("created"), start, end));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Order> getOrdersCreatedBetweenWithState(LocalDate start, LocalDate end, OrderState state) {
        List<Order> rList = new ArrayList<>();
        List<Order> temp = getOrdersCreatedBetween(start, end);

        for (Order order : temp) {
            if (order.getState().equals(state))
                rList.add(order);
        }
        return rList;
    }


    @Override
    public void initDBAccessHandlers() {
        super.initDBAccessHandlers();
        customerDBHandler = new DBHandler<>(em, Customer.class);
        employeeDBHandler = new DBHandler<>(em, Employee.class);
    }
}
