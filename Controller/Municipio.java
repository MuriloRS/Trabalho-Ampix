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
public class Municipio {
    //ESSE É O MODELO PARA ADICIONAR MUNICIPIO AO BANCO DE DADOS
    
    private final String codigo;
    private final String nome;
    private final String uf;
    
    public Municipio(String codigo,String nome,String uf){
        this.codigo = codigo;
        this.nome = nome;
        this.uf = uf;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }
    
}
