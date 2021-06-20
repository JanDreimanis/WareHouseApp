package warehouse.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customers;

    public Order() {
    }



    public Order(Long customerId, Long productId) {
        this.customerId = customerId;
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customers;
    }

    public void setCustomer(Customer customer) {
        this.customers = customer;
    }

//    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + id +
//                ", customerId=" + customerId +
//                ", productId=" + productId +
//                ", product=" + product +
//                ", customers=" + customers +
//                '}';
//    }
}
