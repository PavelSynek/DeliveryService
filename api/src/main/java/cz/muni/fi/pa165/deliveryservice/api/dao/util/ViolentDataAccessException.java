package cz.muni.fi.pa165.deliveryservice.api.dao.util;

import org.springframework.dao.DataAccessException;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Exception for DAO layer
 *
 * @author Matej Leško
 */
public class ViolentDataAccessException extends DataAccessException {
    public ViolentDataAccessException(String message) {
        super(message);
    }
}
