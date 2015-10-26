package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.entity.Employee;
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
 * @author Matej Leško
 * @version 0.1
 * @see Person
 * @see Entity
 */
@Repository
@Transactional
public class EmployeeImpl extends Person<Employee> implements EmployeeDao {

    public EmployeeImpl(Class<Employee> employeeClass) {
        super(employeeClass);
    }
}
