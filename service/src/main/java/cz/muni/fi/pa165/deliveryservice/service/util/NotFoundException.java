package cz.muni.fi.pa165.deliveryservice.service.util;

/**
 * Created by Matej Leško on 2015-11-27.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Thrown when entity is not found in the system
 * @author Matej Leško
 */
public class NotFoundException extends Exception {
    public NotFoundException(String desc) {
        super(desc);
    }
}
