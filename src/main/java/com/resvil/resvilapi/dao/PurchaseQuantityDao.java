package com.resvil.resvilapi.dao;

import com.resvil.resvilapi.classes.PurchaseQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseQuantityDao extends JpaRepository<PurchaseQuantity, Integer>
{
}
