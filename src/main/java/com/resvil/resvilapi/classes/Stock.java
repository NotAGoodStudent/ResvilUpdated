package com.resvil.resvilapi.classes;

import javax.persistence.*;

@Entity
public class Stock
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int stockID;
    @OneToOne
    Product prod;
    int quantity;


    public Stock() {
    }


    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
