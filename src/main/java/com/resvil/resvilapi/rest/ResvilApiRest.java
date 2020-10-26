package com.resvil.resvilapi.rest;
import com.resvil.resvilapi.classes.*;
import com.resvil.resvilapi.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResvilApiRest
{

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao prodDao;
    @Autowired
    private SortDao sortDao;
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private StockDao stockDao;
    @Autowired
    private CartDao cartDao;





    @RequestMapping(value = "getUser/{mail}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("mail") String mail)
    {
        List<User> llista = userDao.findAll();
        for(User user : llista){
            if(user.getEmail().equalsIgnoreCase(mail)){
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.notFound().build();

    }

    //USER
    @RequestMapping(value = "addUser", method =  RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        List<User> users = userDao.findAll();
        for(User u : users)
        {
            if(u.getEmail().equals(user.getEmail()))
            {
                return ResponseEntity.noContent().build();

            }
        }

        userDao.save(user);
        return ResponseEntity.ok(user);
    }



    @RequestMapping(value = "checkout/{idUser}", method = RequestMethod.PUT)
    public ResponseEntity<Sale> checkout(@PathVariable("idUser") int idUser)
    {
        float priceCounter = 0;
        Optional<User> user = userDao.findById(idUser);
        List<Sale> sales = saleDao.findAll();
        Sale currentSale = null;
        if(user.isPresent())
        {
            User foundUser = user.get();
            for (Sale s : sales) {
                if (s.getBuyer().getIdUser() == idUser && !s.isPaid())
                {
                    currentSale = s;
                    for (PurchaseQuantity pq : s.getCart().getListPQ())
                    {
                        priceCounter += pq.getPurchasedQuantity() * pq.getProd().getProdPrice();
                    }
                    s.setTotal(priceCounter);
                }
            }


            if (foundUser.getCredit() >= currentSale.getTotal())
            {

                currentSale.setLdt(LocalDateTime.now());
                foundUser.substractCredit(priceCounter);
                currentSale.setPaid(true);
                saleDao.save(currentSale);
                userDao.save(foundUser);
                return ResponseEntity.ok(currentSale);
            }
            else
                {
                    return ResponseEntity.noContent().build();
                }
        }
            return ResponseEntity.notFound().build();
    }



    @RequestMapping(value = "getSale/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sale> getSale(@PathVariable("id") int id)
    {
       Optional<Sale> sale = saleDao.findById(id);
       if(sale.isPresent())
       {
           Sale foundSale = sale.get();
           return ResponseEntity.ok(foundSale);
       }

       return ResponseEntity.noContent().build();



    }

    @RequestMapping(value="getProd/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable int id)
    {
        Optional<Product> findprod = prodDao.findById(id);
        if(findprod.isPresent())
        {
            Product product = findprod.get();
            return ResponseEntity.ok(product);
        }
        else return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "addProd/{type}", method = RequestMethod.POST)
    public ResponseEntity<Product> addProd(@PathVariable("type") String type, @RequestBody Product prod)
    {
        List<Sort> types = sortDao.findAll();

        for (Sort s : types)
        {
            if(s.getSort().equalsIgnoreCase(type))
            {
                prod.setProdType(s.getSort());
                prodDao.save(prod);
                updateTypeList(s, prod);
                return ResponseEntity.ok(prod);
            }
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "getType/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sort> getType(@PathVariable int id)
    {
        Optional<Sort> type = sortDao.findById(id);
        if(type.isPresent())
        {
            Sort s = type.get();
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.noContent().build();

    }

    @RequestMapping(value = "addType", method = RequestMethod.POST)
    public ResponseEntity<Sort> addType(@RequestBody Sort type)
    {
        List<Sort> types = sortDao.findAll();

        for(Sort s : types)
        {
            if(s.getSort().equalsIgnoreCase(type.getSort())) return ResponseEntity.noContent().build();
        }
        sortDao.save(type);
        return ResponseEntity.ok(type);
    }

    @RequestMapping(value = "updateTypeList", method = RequestMethod.PUT)
    public ResponseEntity<Sort> updateTypeList(Sort s, Product p)
    {
        s.getProducts().add(p);
        sortDao.save(s);
        return ResponseEntity.ok(s);
    }

    @RequestMapping(value = "addQuantity/{id}", method = RequestMethod.POST)
    public ResponseEntity<Stock> addQuantity(@PathVariable int id, @RequestBody Stock quantity)
    {
        Optional<Product> product = prodDao.findById(id);

        if(product.isPresent())
        {
            List<Stock> getQuantiies = stockDao.findAll();
            if(!getQuantiies.isEmpty())
            {
                for(Stock ppq : getQuantiies)
                {
                    if(ppq.getProd().getProdID() == id)
                    {
                        ppq.setQuantity(ppq.getQuantity() + quantity.getQuantity());
                        stockDao.save(ppq);
                        return ResponseEntity.ok(quantity);
                    }
                }
            }

            Product prod = product.get();
            quantity.setProd(prod);
            stockDao.save(quantity);
            return ResponseEntity.ok(quantity);
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "getQuantity/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stock> getQuantity(@PathVariable int id)
    {
        List<Stock> stocks = stockDao.findAll();
        for(Stock s : stocks)
        {
            if(s.getProd().getProdID() == id) return ResponseEntity.ok(s);
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "deleteUser/{idUser}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable int idUser)
    {
        Optional<User> delUser = userDao.findById(idUser);
        if(delUser.isPresent())
        {
            User user = delUser.get();
            userDao.delete(user);
            return ResponseEntity.ok(user);
        }

        else return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "deleteProduct/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable int id)
    {
        Optional<Product> delProd = prodDao.findById(id);
        if(delProd.isPresent())
        {
            Product prod = delProd.get();
            prodDao.delete(prod);
            return ResponseEntity.ok(prod);
        }

        else return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "deleteType/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Sort> deleteType(@PathVariable int id)
    {
        Optional<Sort> delSort = sortDao.findById(id);
        if(delSort.isPresent())
        {
            Sort sort = delSort.get();
            sortDao.delete(sort);
            return ResponseEntity.ok(sort);
        }

        else return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "updateProd/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProd(@PathVariable int id, @RequestBody Product prod)
    {
        Optional<Product> product = prodDao.findById(id);
        if(product.isPresent())
        {
            Product saveData = product.get();
            Product updatedProd = prod;
            updatedProd.setProdID(id);
            updatedProd.setProdType(saveData.getProdType());
            prodDao.save(updatedProd);
            return ResponseEntity.ok(updatedProd);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "updateType/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Sort> updateType(@PathVariable int id, @RequestBody Sort sort)
    {
        Optional<Sort> prodType = sortDao.findById(id);
        if(prodType.isPresent())
        {
            Sort saveData = prodType.get();
            Sort s = sort;
            s.setProducts(saveData.getProducts());
            s.setSortID(id);
            sortDao.save(s);
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "updateUser/{idUser}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable int idUser, @RequestBody User user)
    {
        Optional<User> u = userDao.findById(idUser);
        if(u.isPresent())
        {
            User saveData = u.get();
            if(user.getEmail() != null || !user.getEmail().isEmpty()) saveData.setEmail(user.getEmail());
            if(user.getCredit() > 0) saveData.setCredit(user.getCredit());
            if((user.getName() != null || !user.getName().isEmpty()) && !user.getName().contains("//d") ) saveData.setName(user.getName());
            if(user.getPassword() != null || !user.getPassword().isEmpty()) saveData.setPassword(user.getPassword());
            if((user.getSurname() != null || !user.getSurname().isEmpty()) && !user.getSurname().contains("//d")) saveData.setSurname(user.getSurname());
            userDao.save(saveData);
            return ResponseEntity.ok(saveData);
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "addtoCart/{idProd}/{idUser}", method = RequestMethod.POST)
    public ResponseEntity<Sale> addtoCart(@PathVariable int idProd, @PathVariable int idUser, @RequestBody PurchaseQuantity purchase)
    {
       List<Sale> sales = saleDao.findAll();
       Optional<Product> p = prodDao.findById(idProd);
       Optional<User> u = userDao.findById(idUser);
       List<Stock> stocks = stockDao.findAll();
       boolean enoughStock = false;

       if(p.isPresent() && u.isPresent())
       {
           for (Stock s : stocks) {
               if (s.getProd().getProdID() == p.get().getProdID()) {
                   if (s.getQuantity() >= purchase.getPurchasedQuantity()) {
                       s.setQuantity(s.getQuantity() - purchase.getPurchasedQuantity());
                       enoughStock = true;
                   }
               }
           }

           if (!sales.isEmpty() && enoughStock)
           {
               for (Sale s : sales)
               {
                   if (s.getBuyer().getIdUser() == idUser && !s.isPaid())
                   {

                       for (PurchaseQuantity prod : s.getCart().getListPQ())
                       {
                           if (p.get().getProdID() == prod.getProd().getProdID())
                           {
                               prod.setPurchasedQuantity(prod.getPurchasedQuantity() + purchase.getPurchasedQuantity());
                               saleDao.save(s);
                               return ResponseEntity.ok(s);

                           }
                       }

                       purchase.setProd(p.get());
                       s.getCart().getListPQ().add(purchase);
                       s.setPaid(false);
                       saleDao.save(s);
                       return ResponseEntity.ok(s);



                   }
               }
           }
           if (enoughStock)
           {
               Sale sale = new Sale();
               Cart c = new Cart();
               purchase.setProd(p.get());
               c.getListPQ().add(purchase);
               sale.setCart(c);
               sale.setBuyer(u.get());
               sale.setPaid(false);
               sale.setProdArrived(false);
               saleDao.save(sale);
           }
           else return ResponseEntity.noContent().build();
       }

        return ResponseEntity.notFound().build();

    }










}

