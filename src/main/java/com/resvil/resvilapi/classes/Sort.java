package com.resvil.resvilapi.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sort
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sortID;
    String sort;
    @OneToMany(cascade=CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    public Sort()
    {

    }

    public Sort(int sortID, String sort, List<Product> products) {
        this.sortID = sortID;
        this.sort = sort;
        this.products = products;
    }

    public int getSortID()
    {
        return sortID;
    }

    public void setSortID(int sortID) {
        this.sortID = sortID;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
