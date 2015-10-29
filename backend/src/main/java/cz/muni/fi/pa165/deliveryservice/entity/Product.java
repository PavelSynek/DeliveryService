package cz.muni.fi.pa165.deliveryservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Created by Pavel on 21. 10. 2015.
 */

@Entity
public class Product extends DBEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDate addedDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return name.equals(product.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
