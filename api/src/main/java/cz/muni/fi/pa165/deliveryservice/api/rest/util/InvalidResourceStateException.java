package cz.muni.fi.pa165.deliveryservice.api.rest.util;

/**
 * Created by Matej Leško on 2015-12-16.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Throw if resource is in illegal state
 * @author Matej Leško
 */
public class InvalidResourceStateException extends RuntimeException {
    public InvalidResourceStateException(Long id, String property) {
        super("Property " + property + " of object with ID: " + id + " is in illegal state");
    }
}
