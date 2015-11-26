package cz.muni.fi.pa165.deliveryservice.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);

    Mapper getMapper();
}
