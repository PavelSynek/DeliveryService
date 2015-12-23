package cz.muni.fi.pa165.deliveryservice.api.dto;

import cz.muni.fi.pa165.deliveryservice.api.dto.utils.InvalidPriceException;
import cz.muni.fi.pa165.deliveryservice.api.dto.utils.InvalidWeightException;

import java.time.LocalDate;

/**
 * Created by Pavel on 25. 11. 2015.
 * @author Pavel
 * @author Matej Leško
 */
public class ProductDTO {

    private long id;
    private String name;
    private LocalDate addedDate;
    private Long price;

    private Long weight;

    private OrderDTO order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
        if (!(o instanceof ProductDTO)) return false;

        ProductDTO that = (ProductDTO) o;

        if (!price.equals(that.price)) return false;
        if (!name.equals(that.name)) return false;
        if (!weight.equals(that.weight)) return false;
        if (!order.equals(that.order)) return false;
        return addedDate.equals(that.addedDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addedDate.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + weight.hashCode();
        result = 31 * result + order.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addedDate=" + addedDate +
                ", price=" + price +
                ", weight=" + weight +
                ", order=" + order +
                '}';
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) throws InvalidWeightException {
        if (weight < 0)
            throw new InvalidWeightException();
        this.weight = weight;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
