/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Cliente;
import Controller.Municipio;
import View.EditarCliente;
import View.Index;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Murilo
 */
public class DAO {
    //CRIA A VARIÁVEL GLOBAL DE CONNECTION PARA SER USADA EM TODA A CLASSE
    public static Connection conexao;
    public static String campoSelecionado;
    
    public DAO() throws ClassNotFoundException, SQLException{
        
        //CONEXAO RECEBE UMA NOVA CONEXAO DO TIPO CONNECTION
        
        DAO.conexao = conexaoBanco.getConexao();   
    }
    
    public void adicionaCliente(Cliente c) throws SQLException{
        
        //COMANDO MYSQL QUE SERÁ USADO PARA INSERIR OS DADOS AO BANCO
        String sql = "INSERT INTO cliente(codigo,nome,endereco,numero,bairro,cep) "+" VALUES(?,?,?,?,?,?)";
        
            try (PreparedStatement stm = conexao.prepareStatement(sql)) {
                
                //SETA NOS ?, AS STRINGS DA CLASSE CLIENTE
                stm.setString(1, c.getCodigo());
                stm.setString(2, c.getNome());
                stm.setString(3, c.getEndereco());
                stm.setString(4, c.getNumero());
                stm.setString(5, c.getBairro());
                stm.setString(6, c.getCep());
                
                //EXECUTA
                stm.execute();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, ""+e.getMessage());
        }

    }
    
    public void adicionaMunicipio(Municipio cm) throws SQLException {
        String sql = "INSERT INTO municipio(codigo,nome,uf)"+" VALUES(?,?,?)";
        
        
        try(PreparedStatement stm = conexao.prepareStatement(sql)){
            stm.setString(1, cm.getCodigo());
            stm.setString(2, cm.getNome());
            stm.setString(3, cm.getUf());

            stm.execute();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        

    }

    
    public boolean verificaCodigo(String codigo,int id) throws SQLException{ 
        ResultSet rs;
        int cont;
        String sql = null;
        
        if(id == 1){
               sql = "SELECT codigo FROM cliente";
            }
        else if(id == 2){
            sql = "SELECT codigo FROM municipio";
        }
        
        //TENTA A CONEXAO AO BANCO  E EXECUTA O COMANDO MYSQL
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            
            rs = stm.executeQuery();
            cont = 0;
            while(rs.next()){
                if(rs.getString("codigo") == null ? codigo == null : rs.getString("codigo").equals(codigo)){
                    cont++;
                } 
            }
        }
        rs.close();
     
        //RETORNA O RESULTADO DE CONT PARA SABER SE EXISTA DUPLICIDADE DE CODIGO
        return cont != 0;
       
    }
    
    
    //ELE ATUALIZA AS TABELAS DO PROGRAMA
    public DefaultTableModel atualizaTabela(int id, String valorCombo) throws SQLException{
        //CRIAR UM OBJETO DEFAULTTABLEMODEL QUE SER DE MODELO PARA ADICIONAR DADOS NA TABELA, TAO COMO
        //ADICIONAR COLUNAS
        DefaultTableModel dtm = new DefaultTableModel() {
                @Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
        
        String sql;
        PreparedStatement stm;
	ResultSet rs;
        
        
        
        switch (id) {
            case 1:
                sql = "SELECT * FROM cliente";
                stm = conexao.prepareCall(sql);
                rs = stm.executeQuery();
                
                //ADICIONA AS COLUNAS NA TABELA
                dtm.addColumn("Código");
                dtm.addColumn("Nome");
                dtm.addColumn("Endereço");
                dtm.addColumn("Número");
                dtm.addColumn("Bairro");
                dtm.addColumn("CEP");

                
                while (rs.next()) {
                    dtm.addRow(new String[] {rs.getString("codigo"), rs.getString("nome"), 
                        rs.getString("endereco"), rs.getString("numero"),
                        rs.getString("bairro"),rs.getString("cep")});
                }   
                stm.close();
                
                break;
            case 2:
                sql = "SELECT * FROM municipio";
                stm = conexao.prepareCall(sql);
                rs = stm.executeQuery();
                
                dtm.addColumn("Código");
                dtm.addColumn("Nome");
                dtm.addColumn("UF");
                
                while(rs.next()){
                    dtm.addRow(new String[] {rs.getString("codigo"),
                        rs.getString("nome"),rs.getString("uf")});
                }   
                
                stm.close();
                rs.close();
                
                break;
            case 3:
                
                if(!valorCombo.equals("Nenhum")){
                    sql = "SELECT * FROM cliente WHERE municipio = ?";
                    stm = conexao.prepareCall(sql);
                    
                    
                    stm.setString(1, valorCombo);
                    rs = stm.executeQuery();
                }
                else{
                    sql = "SELECT * FROM cliente";
                    stm = conexao.prepareCall(sql);
                    rs = stm.executeQuery();
                }
                   
                    
                    dtm.addColumn("Cliente");
                    dtm.addColumn("Endereço");
                    dtm.addColumn("Número");
                    dtm.addColumn("Bairro");
                    dtm.addColumn("CEP");
                    dtm.addColumn("Município");


                    while(rs.next()){
                        dtm.addRow(new String[] {rs.getString("codigo"),rs.getString("endereco"),rs.getString("numero"),
                        rs.getString("bairro"), rs.getString("cep"), rs.getString("municipio")});
                    }

                    stm.close();
                    rs.close();

                
                break;
            default:
                break;
        }
        
        return dtm;

    }
    
public void excluirDado(String selecionado, int id) throws SQLException, ClassNotFoundException {
        if(id == 1){
            String sql = "DELETE FROM cliente WHERE codigo = "+" ?";
            
            try(PreparedStatement stm = conexao.prepareStatement(sql)){
                stm.setString(1, selecionado);
                
                stm.execute();
                
            }
        }
        else if(id == 2){
            String sql = "DELETE FROM municipio WHERE codigo = "+" ?";
            
            try(PreparedStatement stm = conexao.prepareStatement(sql)){
                stm.setString(1, selecionado);
                
                stm.execute();
                
            }
            
            new Index().atualizaTabelaMunicipio();
        }

 
    }
    
    //FAZ UMA CONSULTA AO BANCO, PARA PREENCHER OS TEXTFIELD DO EDITAR
    public List<String> preencheEditar(int id) throws SQLException, ClassNotFoundException {
        
        String sql = null;
        
        if(id == 1){
            sql = "SELECT * FROM cliente WHERE codigo = "+"?";
        }
        else if(id == 2){
            sql = "SELECT * FROM municipio WHERE codigo = "+"?";
        }
        
        List<String> lista;
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, DAO.campoSelecionado);
            
            try (ResultSet rs = stm.executeQuery()) {
                lista = new ArrayList<>();
                    if(id == 1){
                        while(rs.next()){
                            lista.add(0, rs.getString("codigo"));
                            lista.add(1, rs.getString("nome"));
                            lista.add(2, rs.getString("endereco"));
                            lista.add(3, rs.getString("numero"));
                            lista.add(4, rs.getString("bairro"));
                            lista.add(5, rs.getString("cep"));

                        }
                    }
                    else if(id == 2){
                        while(rs.next()){
                            lista.add(0,rs.getString("codigo"));
                            lista.add(1,rs.getString("nome"));
                            lista.add(2,rs.getString("uf"));
                        }
                    }    
            }
            
        }
        
        return lista;
       
    }


    
    //MÉTODO REFERENTE AO EDITAR.
    public void Atualizar(List<String> lista,String codigoAux, int id) throws SQLException, ClassNotFoundException {
        String sql = null;
        
        
        if(id == 1){
            sql = "UPDATE cliente SET nome = ?, endereco = ?, numero = ?, bairro = ?, cep = ?, municipio = ? WHERE codigo = ?";
        }
        else if(id == 2){
            sql = "UPDATE municipio SET nome = ?, uf = ? WHERE codigo = ?";
        }
        
        
        try(PreparedStatement stm = conexao.prepareStatement(sql)){
            int aux = lista.size()+1;
            
            
            for(int x = 1;x <= lista.size();x++){
                stm.setString(x, lista.get(x-1));
            }
            stm.setString(aux, codigoAux);
            
            
            stm.execute();
        }
        
        if(id == 2){
            new Index().atualizaTabelaMunicipio();
        }

    }
    
    //FAZ UMA CONSULTA AO BANCO DE DADOS PARA PREENCHER O COMBO BOX 
    public List<String> pesquisarMunicipio() throws SQLException {
        String sql = "SELECT * FROM municipio" ;
        
        List<String> lista = new ArrayList<>();

        try(PreparedStatement stm = conexao.prepareStatement(sql)){
            ResultSet rs = stm.executeQuery();
            lista.add("Nenhum");
            while(rs.next()){
                lista.add(rs.getString("nome"));
            }
            
        }
        
        return lista;
    }

}
