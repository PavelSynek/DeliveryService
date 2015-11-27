package cz.muni.fi.pa165.deliveryservice.service;

import cz.muni.fi.pa165.deliveryservice.persist.dao.CustomerDao;
import cz.muni.fi.pa165.deliveryservice.persist.entity.Customer;
import org.springframework.stereotype.Service;

/**
 * Created by Tomas Milota on 27.11.2015.
 */
@Service
public interface CustomerService extends PersonService<Customer, CustomerDao> {

}
