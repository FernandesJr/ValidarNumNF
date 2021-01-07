package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import view.Alerta;

public class ControllerAlerta implements ActionListener{
    
    private Alerta view;

    public ControllerAlerta(Alerta view) {
        this.view = view;
        this.duracao();
    }
    
    public void duracao(){
        
        Timer time = new Timer(7000, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        view.dispose();
    }
}
