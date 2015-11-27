package cz.muni.fi.pa165.deliveryservice.persist.entity;

import cz.muni.fi.pa165.deliveryservice.persist.enums.OrderState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pavel on 21. 10. 2015.
 * Entity representing Order in the system
 *
 * @author Pavel
 * @author Matej Leško
 * @version 0.1
 */
@Entity
@Table(name = "DELIVERY_ORDER")
public class Order extends DBEntity {

    @ManyToOne(optional = false)
    @NotNull
    private Customer customer;

    @OneToMany
    @NotNull
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @NotNull
    private LocalDate created;

    @Enumerated
    @NotNull
    private OrderState state;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public void addOrderItem(OrderItem p) {
        orderItems.add(p);
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (state != other.state)
            return false;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        return true;
    }
}
