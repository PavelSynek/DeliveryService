package cz.muni.fi.pa165.deliveryservice.api.dto;


import cz.muni.fi.pa165.deliveryservice.api.dto.utils.InvalidPriceException;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created by Pavel on 25. 11. 2015.
 * @author Pavel
 * @author Matej Leško
 */
public class ProductCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private LocalDate addedDate;

    private Long price;


    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCreateDTO)) return false;

        ProductCreateDTO that = (ProductCreateDTO) o;

        if (!price.equals(that.price)) return false;
        if (!name.equals(that.name)) return false;
        return addedDate.equals(that.addedDate);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + addedDate.hashCode();
        result = 31 * result + price.hashCode();
        return result;
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
}
