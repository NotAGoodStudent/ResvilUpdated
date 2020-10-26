package com.resvil.resvilapi.classes;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodID;
    private String prodName;
    private String prodDesc;
    private double prodPrice;
    private double prodRating;
    private String prodType;

    public Product(int prodID, String prodName, String prodDesc, Double prodPrice)
    {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodPrice = prodPrice;
    }

    public Product() {

    }

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public double getProdRating() {
        return prodRating;
    }

    public void setProdRating(double prodRating) {
        this.prodRating = prodRating;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
}
