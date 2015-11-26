package cz.muni.fi.pa165.deliveryservice.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    public <T> List<T> mapTo(Collection<?> objects, Class<T> targetClass) {
        List<T> result = new ArrayList<>();
        for (Object object : objects) {
            result.add(mapTo(object, targetClass));
        }
        return result;
    }

    public <T> T mapTo(Object o, Class<T> targetClass) {
        return dozer.map(o, targetClass);
    }

    public Mapper getMapper() {
        return dozer;
    }
}