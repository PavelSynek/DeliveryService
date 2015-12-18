package cz.muni.fi.deliveryservice.data;

import cz.muni.fi.pa165.deliveryservice.api.service.util.AlreadyExistsException;
import cz.muni.fi.pa165.deliveryservice.api.service.util.NotFoundException;

public interface SampleDataLoadingFacade {

    void loadData() throws AlreadyExistsException, NotFoundException;
}
