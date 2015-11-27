package cz.muni.fi.pa165.deliveryservice.service.util;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Occurs when there is an attempt to ship cancelled order
 * @author Matej Leško
 */
public class CancelledOrderException extends IllegalStateException {
    public CancelledOrderException(String desc) {
        super(desc);
    }
}
