package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.dao.EmployeeDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;
import org.springframework.stereotype.Service;


/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Service
public class EmployeeServiceImpl extends PersonServiceImpl<Employee, EmployeeDao> implements EmployeeService {
}

