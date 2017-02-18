/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Murilo
 */
class conexaoBanco {
     public static Connection getConexao() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            //SEMPRE QUE O DAO PRECISAR DE UMA CONEXAO ESSA CLASSE RETORNA A STRING PARA SE CONECTAR AO BANCO
            
            return DriverManager.getConnection("jdbc:mysql://localhost/ampix?useSSL=false","root","root");
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
      
    
}
