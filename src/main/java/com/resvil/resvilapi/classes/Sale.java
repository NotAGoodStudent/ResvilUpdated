package com.resvil.resvilapi.classes;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Sale
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int saleID;
    @OneToOne(cascade = CascadeType.ALL)
    Cart cart;
    @OneToOne(cascade = CascadeType.ALL)
    User buyer;
    float total;
    LocalDateTime ldt;
    boolean paid;
    boolean prodArrived;


    public Sale() {

    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isProdArrived() {
        return prodArrived;
    }

    public void setProdArrived(boolean prodArrived) {
        this.prodArrived = prodArrived;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
