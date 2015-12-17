package cz.muni.fi.pa165.deliveryservice.api.rest.util;

/**
 * Created by Matej Leško on 2015-12-16.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p>
 * Project: DeliveryService
 */

/**
 * Raises during updating of resource that was not found in the database or transaction failed.
 * @author Matej Leško
 */
public class ResourceUpdateException extends RuntimeException {
    public ResourceUpdateException(Long id) {
        super("Resource with id: " + id + " can't be updated");
    }
}
