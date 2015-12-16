package cz.muni.fi.pa165.deliveryservice.api.rest.util;

/**
 * Created by Matej Leško on 2015-12-16.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Raises during creation of resource that is already stored in the database.
 * @author Matej Leško
 */
public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
