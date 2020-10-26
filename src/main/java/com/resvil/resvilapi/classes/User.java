package com.resvil.resvilapi.classes;

import com.sun.istack.Nullable;

import javax.persistence.*;

@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idUser;
    @Column(nullable=false)
    String name;
    @Column(nullable=false)
    String surname;
    @Column(nullable=false)
    String email;
    @Column(nullable=false)
    String password;
    @Column(nullable=false)
    String role;
    @Column(nullable=false)
    float credit;


    public User()
    {

    }
    public User(String name, String surname, String email, String password, String role)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit += credit;
    }

    public void substractCredit(float credit) {
        this.credit -= credit;
    }
}
