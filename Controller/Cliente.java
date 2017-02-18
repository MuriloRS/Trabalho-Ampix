/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Murilo
 */
public class Cliente {
    //ESSE É O MODELO PARA ADICIONAR CLIENTE AO BANCO DE DADOS
    
    private final String codigo;
    private final String nome;
    private final String endereco;
    private final String numero;
    private final String bairro;
    private final String cep;
    private final String municipio;
    
    
    public Cliente(String codigo, String nome, String endereco, String numero, String bairro, String cep, String municipio) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.municipio = municipio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }
    
    public String municipio() {
        return municipio;
    }

    
}
