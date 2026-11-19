package com.gcu.videogamestorefront.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a customer order placed through the storefront.
 * Stores customer contact and shipping information, the order total,
 * order date, and the collection of purchased order items.
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;

    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    private String customerEmail;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String zip;

    private String phone;

    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Creates an empty Order object.
     * Required by JPA for entity creation.
     */
    public Order() {
    }

    /**
     * Returns the unique order ID.
     *
     * @return order ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique order ID.
     *
     * @param id order ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the date and time when the order was placed.
     *
     * @return order date and time
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date and time when the order was placed.
     *
     * @param orderDate order date and time
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the customer's name.
     *
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer's name.
     *
     * @param customerName customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the customer's email address.
     *
     * @return customer email address
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the customer's email address.
     *
     * @param customerEmail customer email address
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Returns the customer's shipping address.
     *
     * @return shipping address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's shipping address.
     *
     * @param address shipping address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the customer's shipping city.
     *
     * @return shipping city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the customer's shipping city.
     *
     * @param city shipping city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the customer's ZIP code.
     *
     * @return ZIP code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the customer's ZIP code.
     *
     * @param zip ZIP code
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * Returns the customer's phone number.
     *
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer's phone number.
     *
     * @param phone phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the total amount charged for the order.
     *
     * @return total order amount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount charged for the order.
     *
     * @param totalAmount total order amount
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the items included in the order.
     *
     * @return list of order items
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Sets the items included in the order.
     *
     * @param items list of order items
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Adds an item to the order and establishes the relationship
     * between the order and the order item.
     *
     * @param item order item to add
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}