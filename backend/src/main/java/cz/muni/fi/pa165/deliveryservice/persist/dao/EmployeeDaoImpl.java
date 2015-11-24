package cz.muni.fi.pa165.deliveryservice.persist.dao;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Matej Leško on 2015-10-24.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */


/**
 * Implementation of {@link EmployeeDao}
 *
 * @author Matej Leško
 * @version 0.1
 * @see Person
 * @see Entity
 */
@Repository
@Transactional
public class EmployeeDaoImpl extends Person<Employee> implements EmployeeDao {

    public EmployeeDaoImpl(Class<Employee> employeeClass) {
        super(employeeClass);
    }

    public EmployeeDaoImpl() {
        super(Employee.class);
    }
}
