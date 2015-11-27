package cz.muni.fi.pa165.deliveryservice.service.util;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Occurs when there is aan attempt to ship already shipped order
 * @author Matej Leško
 */
public class ShippedOrderException extends IllegalStateException {
    public ShippedOrderException(String desc) {
        super(desc);
    }
}
