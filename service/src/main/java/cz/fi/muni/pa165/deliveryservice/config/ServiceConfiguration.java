package cz.fi.muni.pa165.deliveryservice.config;

import cz.fi.muni.pa165.deliveryservice.ProductServiceImpl;
import cz.fi.muni.pa165.deliveryservice.facade.ProductFacadeImpl;
import cz.muni.fi.pa165.deliveryservice.persist.PersistenceApplicationContext;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Pavel on 25. 11. 2015.
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {ProductServiceImpl.class, ProductFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    /**
     * Custom config for Dozer if needed
     */
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            //mapping(Category.class, CategoryDTO.class);
        }
    }
}
