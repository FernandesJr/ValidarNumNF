package DAO;

import Model.NotaFiscal;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.Alerta;


public class ImportNota {
    
    private Connection conexao;

    public ImportNota(Connection conexao) {
        this.conexao = conexao;
    }
    
    public boolean importarNota(NotaFiscal nota){
        
        String selectNota = this.selectNota(nota);
        if(selectNota == null){
            
            String sql = "insert into notas (numero, data, hora, data_hora) values (?, current_date(), current_time(), ?);";

            try {

                PreparedStatement statement;
                statement = conexao.prepareStatement(sql);
                statement.setString(1, nota.getNum());
                statement.setString(2, nota.getDataHora());
                statement.execute();
                System.out.println("enviado");

                return true;
            } catch (SQLException ex) {
                System.out.println("Não consegui inserir");
            }
        }else{
            Alerta alerta = new Alerta();
            alerta.msg("Nota já declarada no dia: ");
            alerta.msg2(selectNota);
            alerta.getjLabelMsg().setForeground(Color.white);
            alerta.getjLabelMsg2().setForeground(Color.white);
            alerta.getjPanelFundoAlt().setBackground(Color.red);
            alerta.setVisible(true);
        }
        return false;
    }
    
    public String selectNota(NotaFiscal nota){
        
        String horaData = null;
        
        String sql = "select * from notas where numero = ?;";
        
        try {
            
            PreparedStatement statement;
            statement = conexao.prepareStatement(sql);
            statement.setString(1, nota.getNum());
            statement.execute();
            
            ResultSet resultSet = statement.getResultSet();
            
            while(resultSet.next()){
                
                Date   data  = resultSet.getDate("data");
                Time   hora  = resultSet.getTime("hora");
                SimpleDateFormat formatada = new SimpleDateFormat("dd/MM/yyyy");
                String dataFormatada = formatada.format(data);
                
                horaData = dataFormatada + " " + hora;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ImportNota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horaData;
    }
}
