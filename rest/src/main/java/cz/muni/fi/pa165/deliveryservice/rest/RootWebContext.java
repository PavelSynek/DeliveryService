package cz.muni.fi.pa165.deliveryservice.rest;

import cz.muni.fi.pa165.deliveryservice.service.config.ServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Simplified version of RootWebContext from exemplary project.
 *
 * @author Matej Leško
 */
@EnableWebMvc
@Configuration
@Import({ServiceConfiguration.class})
@ComponentScan(basePackages = {"cz.muni.fi.pa165.deliveryservice.rest.controllers"})
public class RootWebContext extends WebMvcConfigurerAdapter {

}
