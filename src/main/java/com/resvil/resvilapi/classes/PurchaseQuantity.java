package com.resvil.resvilapi.classes;

import javax.persistence.*;

@Entity
public class PurchaseQuantity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseQuantityID;
    @OneToOne
    private Product prod;
    private int purchasedQuantity;
    public PurchaseQuantity(int purchaseQuantityID) {
        this.purchaseQuantityID = purchaseQuantityID;
    }

    public PurchaseQuantity() {

    }

    public int getPurchaseQuantityID() {
        return purchaseQuantityID;
    }

    public void setPurchaseQuantityID(int purchaseQuantityID) {
        this.purchaseQuantityID = purchaseQuantityID;
    }

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public void setPurchasedQuantity(int purchasedQuantity) {
        this.purchasedQuantity = purchasedQuantity;
    }
}
