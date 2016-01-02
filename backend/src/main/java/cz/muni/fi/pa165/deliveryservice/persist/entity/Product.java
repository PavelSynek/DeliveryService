package cz.muni.fi.pa165.deliveryservice.persist.entity;

import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dao.util.InvalidWeightException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Pavel on 21. 10. 2015.
 * @author Pavel
 * @author Matej Leško
 */

@Entity
public class Product extends DBEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    private LocalDate addedDate;

    private Long price = 0L;

    private Long weight = 0L;

    @NotNull //TODO add order_product to tests
    @ManyToOne
    private Order order;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) throws InvalidPriceException {
        if (price < 0)
            throw new InvalidPriceException();
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!price.equals(product.price)) return false;
        if (!name.equals(product.name)) return false;
        if (!weight.equals(product.weight)) return false;
//        if (!order.equals(product.order)) return false;
        return addedDate.equals(product.addedDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addedDate.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + weight.hashCode();
//        result = 31 * result + order.hashCode();
        return result;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) throws InvalidWeightException {
        if (weight < 0)
            throw new InvalidWeightException();
        this.weight = weight;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
