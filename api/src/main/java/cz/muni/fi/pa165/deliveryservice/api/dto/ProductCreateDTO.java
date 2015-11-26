package cz.muni.fi.pa165.deliveryservice.api.dto;


import javax.validation.constraints.NotNull;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public class ProductCreateDTO {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCreateDTO)) return false;

        ProductCreateDTO that = (ProductCreateDTO) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
