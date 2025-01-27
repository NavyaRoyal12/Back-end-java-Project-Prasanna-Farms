package in.prasannaFarm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "cartTable")
public class CartTable {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // Map each field to a unique column name
    @Column(name = "userId", nullable = false)
    private long userId;
    @Column(name = "productId", nullable = false)
    private long productId;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    // Getters and Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "CartTable [id=" + id + ", userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }
}