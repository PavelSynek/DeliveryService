/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.entity.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomas Milota
 */
@Transactional
@Repository
public class CustomerDaoImpl extends Person<Customer> implements CustomerDao {
    public CustomerDaoImpl(Class<Customer> CustomerClass) {
        super(CustomerClass);
    }

    public CustomerDaoImpl() {
        super(Customer.class);
    }
}
