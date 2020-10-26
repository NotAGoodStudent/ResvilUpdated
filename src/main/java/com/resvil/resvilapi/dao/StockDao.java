package com.resvil.resvilapi.dao;

import com.resvil.resvilapi.classes.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDao extends JpaRepository<Stock, Integer>
{

}
