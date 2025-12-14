
package pokemon;

import java.util.Random;


public class Pokedex {
    
    
    private String[][] tiposEfectividad = {
        //Fuego, Agua, Planta, Eléctrico
        {"0.5", "2.0", "0.5", "1.0"},  // Ataque: Fuego
        {"0.5", "0.5", "2.0", "1.0"},  // Ataque: Agua
        {"2.0", "0.5", "0.5", "0.5"},  // Ataque: Planta
        {"1.0", "2.0", "0.5", "0.5"}   // Ataque: Electrico
    };
    
    private String[] nombreTipos = {"Fuego", "Agua", "Planta", "Electrico"};
    
    private Pokemons[] pokemones;
    private Random r;
    
    public Pokedex(){
        this.r = new Random();
        iniciPokemones();
        iniciAtaques();
    }
    
    private void iniciPokemones(){
        pokemones = new Pokemons[8];
        
        pokemones[0] = new Pokemons("Charizard", "Fuego", 78, 84, 78);
        pokemones[1] = new Pokemons("Blastoise", "Agua", 79, 83, 100);
        pokemones[2] = new Pokemons("Venusaur", "Planta", 80, 82, 83);
        pokemones[3] = new Pokemons("Pikachu", "Electrico", 35, 55, 40);
        pokemones[4] = new Pokemons("Arcanine", "Fuego", 90, 110, 80);
        pokemones[5] = new Pokemons("Lapras", "Agua", 130, 85, 80);
        pokemones[6] = new Pokemons("Exeggutor", "Planta", 95, 95, 85);
        pokemones[7] = new Pokemons("Raichu", "Electrico", 60, 90, 55);
    }
    
    private void iniciAtaques(){
        Ataque[] ataquesFuego = {
            new Ataque("Lanzallamas", "Fuego", 90, 15),
            new Ataque("Ascuas", "Fuego", 40, 25),
            new Ataque("Giro Fuego", "Fuego", 35, 15),
            new Ataque("Ataque Rapido", "Normal", 40, 30)
        };
        
        Ataque[] ataquesAgua = {
            new Ataque("Hidrobomba", "Agua", 110, 5),
            new Ataque("Pistola Agua", "Agua", 40, 25),
            new Ataque("Rayo Burbuja", "Agua", 65, 20),
            new Ataque("Cabezazo", "Normal", 70, 15)
        };
        
        Ataque[] ataquesPlanta = {
            new Ataque("Rayo Solar", "Planta", 120, 10),
            new Ataque("Latigo Cepa", "Planta", 45, 25),
            new Ataque("Drenadoras", "Planta", 20, 10),
            new Ataque("Derribo", "Normal", 90, 20)
        };
        
        Ataque[] ataquesElectrico = {
            new Ataque("Trueno", "Electrico", 110, 10),
            new Ataque("Impactrueno", "Electrico", 40, 30),
            new Ataque("Onda Trueno", "Electrico", 60, 20),
            new Ataque("Ataque Veloz", "Normal", 80, 20)
        };
        
        for (Pokemons p : pokemones) {
            String tipo = p.getTipo();
            Ataque[] ataques;
            
            switch (tipo) {
                case "Fuego": ataques = ataquesFuego; break;
                case "Agua": ataques = ataquesAgua; break;
                case "Planta": ataques = ataquesPlanta; break;
                default : ataques = ataquesElectrico; break;                   
            }
            
            for (int i = 0; i < 4; i++) {
                p.agregarAtaque(ataques[i], i);
            }
        }
    }
    
    public static void mostrarTablaEfectividad(){
        System.out.println("\n=== TABLA DE EFECTIVIDAD ===");
        System.out.println("Fuego > Planta (x2)");
        System.out.println("Fuego < Agua (x0.5)");
        System.out.println("Agua > Fuego (x2)");
        System.out.println("Agua < Planta (x0.5)");
        System.out.println("Planta > Agua (x2)");
        System.out.println("Planta < Fuego (x0.5)");
        System.out.println("Electrico > Agua (x2)");
        System.out.println("Electrico < Planta (x0.5)");
    }
    
    public Pokemons[] obPokemonesAle(int cant){
        Pokemons[] seleccionados = new Pokemons[cant];
        
        for (int i = 0; i < cant; i++) {
            int indice;
            do {
                indice = r.nextInt(pokemones.length);
            } while (yaSeleccionado(seleccionados, pokemones[indice]));
            
            seleccionados[i] = pokemones[indice];
        }
        return seleccionados;
    }
    
    private boolean yaSeleccionado(Pokemons[] seleccionados, Pokemons pokemon){
        for (Pokemons p : seleccionados) {
            if (p != null && p.equals(pokemon)) {
                return true;
            }
        }
        return false;
    }
    
    public void BuscarXTipo(String tipo){
        System.out.println("\n=== POKEMON DE TIPO " + tipo.toUpperCase() + " ===");
        int encon = 0;
        
        for (Pokemons p : pokemones) {
            if (p.getTipo().equalsIgnoreCase(tipo)) {
                System.out.println("- " + p);
                encon++;
            }
        }
        
        if (encon == 0) {
            System.out.println("No se encuantran Pokemon de este tipo");
        } else {
            System.out.println("Total: " + encon + " Pokemon");
        }
    }
    
    public Pokemons pokemonMasFuerte(){
        Pokemons masFuerte = pokemones[0];
        for (Pokemons p : pokemones) {
            if (p.getAtaque() > masFuerte.getAtaque()) {
                masFuerte = p;
            }
        }
        return masFuerte;
    }
}
