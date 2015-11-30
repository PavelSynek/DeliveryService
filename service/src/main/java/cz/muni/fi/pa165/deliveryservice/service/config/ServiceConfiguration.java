package cz.muni.fi.pa165.deliveryservice.service.config;

import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import cz.muni.fi.pa165.deliveryservice.service.ProductServiceImpl;
import cz.muni.fi.pa165.deliveryservice.service.facade.ProductFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 25. 11. 2015.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {ProductServiceImpl.class, ProductFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozer-config.xml");
        return new DozerBeanMapper(mappingFiles);
    }
}
