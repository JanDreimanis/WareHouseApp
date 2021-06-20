package warehouse.entities;

import javafx.scene.control.TextField;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private Integer quantity;
    @Column
    private String unit;
    @Column
    private Double price;
    @Column(name = "suppliers_id")
    private Long supplierId;

    @ManyToOne
    @JoinColumn(name = "suppliers_id", insertable = false, updatable = false)
    private Supplier suppliers;

    @OneToMany(mappedBy = "product")
    private final Set<Order> orders = new HashSet<>();

    public Product() {
    }

    public Product(String name, Integer quantity, String unit, Double price,Long supplierId) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.supplierId = supplierId;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Supplier getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Supplier suppliers) {
        this.suppliers = suppliers;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", supplierId=" + supplierId +
                ", suppliers=" + suppliers +
                ", orders=" + orders +
                '}';
    }
}
