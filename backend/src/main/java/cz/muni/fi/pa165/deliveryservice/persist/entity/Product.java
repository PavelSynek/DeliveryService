package cz.muni.fi.pa165.deliveryservice.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Pavel on 21. 10. 2015.
 */

@Entity
public class Product extends DBEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    private LocalDate addedDate;

    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (price != product.price) return false;
        if (!name.equals(product.name)) return false;
        return addedDate.equals(product.addedDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addedDate.hashCode();
        result = 31 * result + price;
        return result;
    }
}
