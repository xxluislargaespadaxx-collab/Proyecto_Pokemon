package pokemon;

public class Ataque {

    private String nombre;
    private String tipo;
    private int poder;
    private int pp;
    private int ppMax;

    public Ataque(String nombre, String tipo, int poder, int pp) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
        this.pp = pp;
        this.ppMax = pp;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPoder() {
        return poder;
    }

    public int getPp() {
        return pp;
    }

    public int getPpMax() {
        return ppMax;
    }
    
    public int usar(){
        if (pp > 0) {
            pp --;
            return poder;
        }
        return 0;
    }
    
    public boolean tienePP(){
        return pp > 0;
    }
    
    public void restaurarPP(){
        pp = ppMax;
    }
    
    @Override
    public String toString(){
        return nombre + " (" + tipo + ") Poder: " + poder + " PP: " + pp + "/" + ppMax;
    }
}
