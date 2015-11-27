package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.service.dao.EmployeeDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Service
public interface EmployeeService extends PersonService<Employee, EmployeeDao> {
}
