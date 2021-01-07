package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
    
    Connection conexao = null;
    
    public Connection getConnection(){
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/nota_fiscal", "root", "");
            System.out.println("Conectado");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "ERRO CONEXAO");
        }
        return conexao;
    }
    
}
