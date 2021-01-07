package Model;


public class NotaFiscal {
    
    private String num;
    private String dataHora;

    public NotaFiscal(String num, String dataHora) {
        this.num = num;
        this.dataHora = dataHora;
    }

    public String getNum() {
        return num;
    }

    public String getDataHora() {
        return dataHora;
    }
    
}
