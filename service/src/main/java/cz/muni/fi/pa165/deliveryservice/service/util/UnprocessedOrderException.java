package cz.muni.fi.pa165.deliveryservice.service.util;

import cz.muni.fi.pa165.deliveryservice.persist.entity.Employee;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Occurs when there is an attempt to manipulate with an order,
 * which was not processed by {@link Employee}
 *
 * @author Matej Leško
 */
public class UnprocessedOrderException extends IllegalStateException {
    public UnprocessedOrderException(String desc) {
        super(desc);
    }
}
