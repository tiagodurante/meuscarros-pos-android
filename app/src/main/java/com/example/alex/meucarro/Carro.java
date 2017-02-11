package com.example.alex.meucarro;

/**
 * Created by alex on 11/02/17.
 */

public class Carro {

    private Integer id;
    private String nome;
    private String marca;


    public Carro(Integer id, String nome, String marca) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
    }

    public Carro(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", marca='" + marca + '\'' +
                '}';
    }
}