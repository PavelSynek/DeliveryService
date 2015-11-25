package cz.fi.muni.pa165.deliveryservice.dto;

import java.time.LocalDate;

/**
 * Created by Pavel on 25. 11. 2015.
 */
public class ProductDTO {

    private long id;
    private String name;
    private LocalDate addedDate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;

        ProductDTO that = (ProductDTO) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        return !(addedDate != null ? !addedDate.equals(that.addedDate) : that.addedDate != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (addedDate != null ? addedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addedDate=" + addedDate +
                '}';
    }
}
