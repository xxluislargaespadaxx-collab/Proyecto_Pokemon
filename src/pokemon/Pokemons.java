package pokemon;

public class Pokemons {

    private String nombre;
    private String tipo;
    private int hp;
    private int hpMax;
    private int nivel;
    private int ataque;
    private int defensa;
    private Ataque[] ataques;

    public Pokemons(String nombre, String tipo, int hp, int ataque, int defensa) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.hp = hp;
        this.hpMax = hp;
        this.ataque = ataque;
        this.defensa = defensa;
        this.nivel = 50;
        this.ataques = new Ataque[4];
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getHp() {
        return hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public Ataque[] getAtaques() {
        return ataques;
    }

    public String getInfo() {
        return String.format("%s (Tipo: %s) HP: %d/%d Ataque: %d Defensa: %d",
                nombre, tipo, hp, hpMax, ataque, defensa);
    }

    public void recibirDanio(int danio) {
        this.hp -= danio;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void recibirDanio(int danio, String tipoAtaque) {
        double efectividad = calcularEfectividad(tipo, tipoAtaque);
        int danioFinal = (int) (danio * efectividad);
        this.hp -= danioFinal;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.printf("%s recibe %d de daño (x%.1f efectividad)%n",
                nombre, danioFinal, efectividad);
    }

    public boolean estaDebilitado() {
        return hp <= 0;
    }

    public boolean puedeAtacar() {
        return hp > 0;
    }

    public void agregarAtaque(Ataque ataque, int indice) {
        if (indice >= 0 && indice < 4) {
            this.ataques[indice] = ataque;
        }
    }

    private double calcularEfectividad(String tipoDefensa, String tipoAtaque) {

        if (tipoAtaque.equals("Fuego")) {
            if (tipoDefensa.equals("Planta")) {
                return 2.0;
            }
            if (tipoDefensa.equals("Agua")) {
                return 0.5;
            }
            if (tipoDefensa.equals("Fuego")) {
                return 0.5;
            }
        }
        if (tipoAtaque.equals("Agua")) {
            if (tipoDefensa.equals("Fuego")) {
                return 2.0;
            }
            if (tipoDefensa.equals("Planta")) {
                return 0.5;
            }
            if (tipoDefensa.equals("Agua")) {
                return 0.5;
            }
        }
        if (tipoAtaque.equals("Planta")) {
            if (tipoDefensa.equals("Agua")) {
                return 2.0;
            }
            if (tipoDefensa.equals("Planta")) {
                return 0.5;
            }
            if (tipoDefensa.equals("Fuego")) {
                return 0.5;
            }
        }
        if (tipoAtaque.equals("Electrico")) {
            if (tipoDefensa.equals("Agua")) {
                return 2.0;
            }
            if (tipoDefensa.equals("Planta")) {
                return 0.5;
            }
            if (tipoDefensa.equals("Electrico")) {
                return 0.5;
            }
        }
        return 1.0;
    }

    @Override
    public String toString() {
        return nombre + " [" + tipo + "] Lv." + nivel;
    }
}
