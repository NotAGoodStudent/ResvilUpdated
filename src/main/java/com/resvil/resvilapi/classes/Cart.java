package com.resvil.resvilapi.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCart;
    @OneToMany(cascade=CascadeType.ALL)
    List<PurchaseQuantity> listPQ = new ArrayList<>();
    @OneToOne

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public List<PurchaseQuantity> getListPQ() {
        return listPQ;
    }

    public void setListPQ(List<PurchaseQuantity> listPQ) {
        this.listPQ = listPQ;
    }

}
