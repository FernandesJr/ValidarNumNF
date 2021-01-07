package Controller;

import DAO.Conexao;
import DAO.ImportNota;
import Model.NotaFiscal;
import java.awt.Color;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import view.Alerta;
import view.ViewPri;

/**
 *
 * @author Junior Fernandes
 */
public class ControllerViewPri implements ActionListener{
    
    private ViewPri view;
    
    public ControllerViewPri(ViewPri view) {
        this.view = view;
    }
    
    public void setIcone(){
        // No file jar. não funciona o ico, o SO é quem altera.
        //this.view.setIconImage(Toolkit.getDefaultToolkit().getImage("src/img/iconeNF.png"));
    }
    
    public void confirma(){
        
        boolean validarNum = this.validarNum();
        Alerta alerta = new Alerta();
        
        if(validarNum){
            
            NotaFiscal nota = new NotaFiscal(this.view.getNumNota().getText(), this.view.getDataHora().getText());
            
            Conexao conexao = new Conexao();
            
            ImportNota impNota = new ImportNota(conexao.getConnection());
            boolean notaOk = impNota.importarNota(nota);
            
            if (notaOk){
                
                this.view.getNumNota().setText("");
                alerta.msg("Nota fiscal ACEITA.");
                alerta.setVisible(true);
            }
        }else{
            alerta.msg("Código de barras inválido.");
            alerta.setVisible(true);
        }
    }
    
    public void confirmaAuto(){
        
        boolean validarNum = this.validarNum();
        
        if(validarNum){
            
            Conexao conexao = new Conexao();
            Connection connection = conexao.getConnection();
            
            NotaFiscal nota = new NotaFiscal(this.view.getNumNota().getText().replace(" ", ""), this.view.getDataHora().getText());
            
            ImportNota impNota = new ImportNota(connection);
            boolean notaOk = impNota.importarNota(nota);
            
            if (notaOk){
                
                this.view.getNumNota().setText("");
                Alerta alerta = new Alerta();
                alerta.msg("Nota fiscal ACEITA.");
                alerta.getjPanelFundoAlt().setBackground(Color.green);
                alerta.setVisible(true);
            }else{
                this.view.getNumNota().setText("");
            }
        }
    }
    
    public boolean validarNum(){
        
        String num  = this.view.getNumNota().getText().replace(" ", "");
        
        if(num.length() == 44){
            
            for (int i = 0;  i < 44; i++){
                
                if (Character.isLetter(num.charAt(i))){ //Valida se contém alguma letra.
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public void hora(){
        
        Timer timer = new Timer(1000, this); //Objeto que mostra hora atualizada
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");

        String dataHora = ldt.format(dtf);
        view.getDataHora().setText(dataHora);

        // Validação automatica
        confirmaAuto();
    }
}
