package pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GUI extends JFrame {
    private JTextArea textArea;
    private JButton btnBatalla, btnPokedex, btnPracticar, btnConfig, btnAcerca, btnSalir, btnTerminarBatalla;
    private JComboBox<String> comboPokemon;
    private JPanel panelSuperior, panelCentral, panelInferior;
    private JScrollPane scrollPane;
    private JButton[] botonesAtaques = new JButton[4];
    
    private Pokedex pokedex;
    private Random random;
    private Batalla batallaActual;
    
    public GUI() {
        pokedex = new Pokedex();
        random = new Random();
        batallaActual = null;
        
        configurarVentana();
        crearComponentes();
        organizarComponentes();
        configurarEventos();
        
        setVisible(true);
    }
    
    private void configurarVentana() {
        setTitle("Simulador de Batalla Pokemon - GUI");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }
    
    private void crearComponentes() {
        panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(220, 60, 60));
        JLabel titulo = new JLabel("SIMULADOR DE BATALLA POKEMON");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);
        
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registro de Batallas"));
        
        panelInferior = new JPanel(new GridLayout(3, 3, 10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnBatalla = crearBoton("Nueva Batalla", Color.RED);
        btnPokedex = crearBoton("Ver Pokedex", Color.BLUE);
        btnPracticar = crearBoton("Practica Ataques", Color.GREEN);
        btnConfig = crearBoton("Configuracion", Color.ORANGE);
        btnAcerca = crearBoton("Acerca de", Color.MAGENTA);
        btnSalir = crearBoton("Salir", Color.GRAY);
        
        String[] pokemones = {"Charizard", "Blastoise", "Venusaur", "Pikachu", 
                             "Arcanine", "Lapras", "Exeggutor", "Raichu"};
        comboPokemon = new JComboBox<>(pokemones);
        comboPokemon.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelInferior.add(btnBatalla);
        panelInferior.add(btnPokedex);
        panelInferior.add(btnPracticar);
        panelInferior.add(btnConfig);
        panelInferior.add(btnAcerca);
        panelInferior.add(btnSalir);
        panelInferior.add(new JLabel("Seleccion rapida:"));
        panelInferior.add(comboPokemon);
        
        JPanel panelAtaques = new JPanel(new GridLayout(2, 2, 5, 5));
        panelAtaques.setBorder(BorderFactory.createTitledBorder("Selecciona Ataque"));
        
        for (int i = 0; i < 4; i++) {
            botonesAtaques[i] = new JButton("Ataque " + (i+1));
            botonesAtaques[i].setFont(new Font("Arial", Font.BOLD, 12));
            botonesAtaques[i].setBackground(Color.LIGHT_GRAY);
            botonesAtaques[i].setEnabled(false);
            panelAtaques.add(botonesAtaques[i]);
        }
        
        btnTerminarBatalla = new JButton("Terminar Batalla");
        btnTerminarBatalla.setFont(new Font("Arial", Font.BOLD, 12));
        btnTerminarBatalla.setBackground(new Color(220, 60, 60));
        btnTerminarBatalla.setForeground(Color.WHITE);
        btnTerminarBatalla.setEnabled(false);
        
        panelInferior.add(panelAtaques);
        panelInferior.add(btnTerminarBatalla);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }
    
    private void organizarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        mostrarBienvenida();
    }
    
    private void configurarEventos() {
        btnBatalla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarNuevaBatallaConAtaques();
            }
        });

        btnPokedex.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarPokedexGUI();
            }
        });

        btnPracticar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                practicarAtaquesGUI();
            }
        });

        btnConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuConfiguracionGUI();
            }
        });

        btnAcerca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDeGUI();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salirPrograma();
            }
        });

        comboPokemon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) comboPokemon.getSelectedItem();
                textArea.append("Pokemon seleccionado: " + seleccionado + "\n");

                for (int i = 0; i < 8; i++) {
                    if (seleccionado.equalsIgnoreCase(getNombrePokemon(i))) {
                        mostrarInfoPokemon(i);
                        break;
                    }
                }
            }
        });
        
        for (int i = 0; i < botonesAtaques.length; i++) {
            final int indiceAtaque = i;
            
            botonesAtaques[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ejecutarAtaqueEnBatalla(indiceAtaque);
                }
            });
        }
        
        btnTerminarBatalla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                terminarBatallaActual();
            }
        });
    }
    
    private void iniciarNuevaBatallaConAtaques() {
        textArea.setText("");
        
        textArea.append("=== INICIANDO NUEVA BATALLA CON ATAQUES ===\n\n");
        
        Pokemons[] pokemonesDisponibles = pokedex.obPokemonesAle(3);
        
        textArea.append("Pokemon disponibles para la batalla:\n");
        for (int i = 0; i < pokemonesDisponibles.length; i++) {
            textArea.append((i+1) + ". " + pokemonesDisponibles[i].getInfo() + "\n");
        }
        
        String[] opciones = new String[pokemonesDisponibles.length];
        for (int i = 0; i < pokemonesDisponibles.length; i++) {
            opciones[i] = pokemonesDisponibles[i].getNombre() + " - " + pokemonesDisponibles[i].getTipo();
        }
        
        String eleccionUsuario = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona tu Pokemon:",
            "Eleccion de Pokemon",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (eleccionUsuario == null) {
            textArea.append("Batalla cancelada.\n");
            return;
        }
        
        Pokemons pokemonJugador = null;
        Pokemons pokemonRival = null;
        
        for (int i = 0; i < pokemonesDisponibles.length; i++) {
            if (opciones[i].equals(eleccionUsuario)) {
                pokemonJugador = pokemonesDisponibles[i];
                
                int indiceRival = (i + 1) % pokemonesDisponibles.length;
                pokemonRival = pokemonesDisponibles[indiceRival];
                break;
            }
        }
        
        if (pokemonJugador == null || pokemonRival == null) {
            JOptionPane.showMessageDialog(this, "Error al seleccionar Pokemon", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        textArea.append("\n¡Has elegido a " + pokemonJugador.getNombre() + "!\n");
        textArea.append("Tu rival sera " + pokemonRival.getNombre() + "\n\n");
        
        batallaActual = new Batalla(pokemonJugador, pokemonRival);
        
        mostrarInformacionBatalla();
        
        actualizarBotonesAtaques();
        
        JOptionPane.showMessageDialog(
            this,
            "¡Que comience la batalla!\n" +
            pokemonJugador.getNombre() + " vs " + pokemonRival.getNombre(),
            "¡Batalla!",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void mostrarInformacionBatalla() {
        if (batallaActual == null) return;
        
        textArea.append("=== INFORMACION DE LA BATALLA ===\n");
        textArea.append(batallaActual.getJugador().getNombre() + ":\n");
        textArea.append("  HP: " + batallaActual.getJugador().getHp() + "/" + 
                       batallaActual.getJugador().getHpMax() + "\n");
        textArea.append("  Tipo: " + batallaActual.getJugador().getTipo() + "\n\n");
        
        textArea.append(batallaActual.getRival().getNombre() + ":\n");
        textArea.append("  HP: " + batallaActual.getRival().getHp() + "/" + 
                       batallaActual.getRival().getHpMax() + "\n");
        textArea.append("  Tipo: " + batallaActual.getRival().getTipo() + "\n\n");
        
        textArea.append("=== TUS ATAQUES DISPONIBLES ===\n");
        Ataque[] ataques = batallaActual.getJugador().getAtaques();
        for (int i = 0; i < ataques.length; i++) {
            if (ataques[i] != null) {
                textArea.append((i+1) + ". " + ataques[i].getNombre() + 
                              " (Tipo: " + ataques[i].getTipo() + 
                              ", Poder: " + ataques[i].getPoder() + 
                              ", PP: " + ataques[i].getPp() + "/" + 
                              ataques[i].getPpMax() + ")\n");
            }
        }
        textArea.append("\n");
    }
    
    private void ejecutarAtaqueEnBatalla(int indiceAtaque) {
        if (batallaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay batalla activa", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (batallaActual.isBatallaTerminada()) {
            textArea.append("La batalla ya ha terminado. ¡" + batallaActual.getGanador() + " gana!\n");
            return;
        }
        
        Ataque[] ataques = batallaActual.getJugador().getAtaques();
        if (indiceAtaque < 0 || indiceAtaque >= ataques.length || ataques[indiceAtaque] == null) {
            textArea.append("¡Ataque no disponible!\n");
            return;
        }
        
        Ataque ataqueSeleccionado = ataques[indiceAtaque];
        
        if (!ataqueSeleccionado.tienePP()) {
            textArea.append("¡" + ataqueSeleccionado.getNombre() + " no tiene PP restantes!\n");
            return;
        }
        
        textArea.append("\n=== TURNO DEL JUGADOR ===\n");
        textArea.append("Usas " + ataqueSeleccionado.getNombre() + "!\n");
        
        int danio = calcularDanoAtaque(ataqueSeleccionado, batallaActual.getJugador(), batallaActual.getRival());
        batallaActual.getRival().recibirDanio(danio, ataqueSeleccionado.getTipo());
        
        textArea.append("Daño infligido: " + danio + "\n");
        textArea.append(batallaActual.getRival().getNombre() + " ahora tiene " + 
                       batallaActual.getRival().getHp() + " HP\n");
        
        if (batallaActual.getRival().estaDebilitado()) {
            textArea.append("\n¡" + batallaActual.getRival().getNombre() + " fue debilitado!\n");
            textArea.append("¡" + batallaActual.getJugador().getNombre() + " gana la batalla!\n");
            batallaActual.setGanador(batallaActual.getJugador().getNombre());
            batallaActual.setBatallaTerminada(true);
            
            JOptionPane.showMessageDialog(
                this,
                "¡" + batallaActual.getJugador().getNombre() + " gana la batalla!",
                "¡Victoria!",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            desactivarBotonesAtaques();
            return;
        }
        
        textArea.append("\n=== TURNO DEL RIVAL ===\n");
        ejecutarTurnoRival();
        
        if (batallaActual.getJugador().estaDebilitado()) {
            textArea.append("\n¡" + batallaActual.getJugador().getNombre() + " fue debilitado!\n");
            textArea.append("¡" + batallaActual.getRival().getNombre() + " gana la batalla!\n");
            batallaActual.setGanador(batallaActual.getRival().getNombre());
            batallaActual.setBatallaTerminada(true);
            
            JOptionPane.showMessageDialog(
                this,
                "¡" + batallaActual.getRival().getNombre() + " gana la batalla!",
                "¡Derrota!",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            desactivarBotonesAtaques();
            return;
        }
        
        textArea.append("\n=== ESTADO ACTUAL ===\n");
        textArea.append(batallaActual.getJugador().getNombre() + ": " + 
                       batallaActual.getJugador().getHp() + " HP\n");
        textArea.append(batallaActual.getRival().getNombre() + ": " + 
                       batallaActual.getRival().getHp() + " HP\n");
        
        actualizarBotonesAtaques();
    }
    
    private void ejecutarTurnoRival() {
        if (batallaActual == null) return;
        
        Ataque[] ataquesRival = batallaActual.getRival().getAtaques();
        Ataque ataqueRival = null;
        
        for (int i = 0; i < ataquesRival.length; i++) {
            if (ataquesRival[i] != null && ataquesRival[i].tienePP()) {
                ataqueRival = ataquesRival[i];
                break;
            }
        }
        
        if (ataqueRival == null) {
            textArea.append(batallaActual.getRival().getNombre() + " no puede atacar (sin PP)\n");
            return;
        }
        
        textArea.append(batallaActual.getRival().getNombre() + " usa " + ataqueRival.getNombre() + "!\n");
        
        int danioRival = calcularDanoAtaque(ataqueRival, batallaActual.getRival(), batallaActual.getJugador());
        batallaActual.getJugador().recibirDanio(danioRival, ataqueRival.getTipo());
        
        textArea.append("Daño recibido: " + danioRival + "\n");
        textArea.append("Ahora tienes " + batallaActual.getJugador().getHp() + " HP\n");
        
        ataqueRival.usar();
    }
    
    private int calcularDanoAtaque(Ataque ataque, Pokemons atacante, Pokemons defensor) {
        Random rand = new Random();
        
        int dañoBase = ataque.getPoder();
        int nivel = 50;
        
        double efectividad = 1.0;
        String tipoAtaque = ataque.getTipo();
        String tipoDefensa = defensor.getTipo();
        
        if (tipoAtaque.equals("Fuego") && tipoDefensa.equals("Planta")) efectividad = 2.0;
        else if (tipoAtaque.equals("Agua") && tipoDefensa.equals("Fuego")) efectividad = 2.0;
        else if (tipoAtaque.equals("Planta") && tipoDefensa.equals("Agua")) efectividad = 2.0;
        else if (tipoAtaque.equals("Electrico") && tipoDefensa.equals("Agua")) efectividad = 2.0;
        else if (tipoAtaque.equals("Fuego") && tipoDefensa.equals("Agua")) efectividad = 0.5;
        else if (tipoAtaque.equals("Agua") && tipoDefensa.equals("Planta")) efectividad = 0.5;
        else if (tipoAtaque.equals("Planta") && tipoDefensa.equals("Fuego")) efectividad = 0.5;
        
        double variacion = 0.85 + (rand.nextDouble() * 0.3);
        int daño = (int)(dañoBase * efectividad * variacion);
        
        if (daño < 1) daño = 1;
        
        if (efectividad > 1.0) {
            textArea.append("¡Es super efectivo! (x" + efectividad + ")\n");
        } else if (efectividad < 1.0) {
            textArea.append("No es muy efectivo... (x" + efectividad + ")\n");
        }
        
        return daño;
    }
    
    private void actualizarBotonesAtaques() {
        if (batallaActual == null || batallaActual.isBatallaTerminada()) {
            desactivarBotonesAtaques();
            return;
        }
        
        Pokemons jugador = batallaActual.getJugador();
        Ataque[] ataques = jugador.getAtaques();
        
        for (int i = 0; i < botonesAtaques.length; i++) {
            if (i < ataques.length && ataques[i] != null) {
                if (ataques[i].tienePP()) {
                    botonesAtaques[i].setText(ataques[i].getNombre() + 
                                             " (PP: " + ataques[i].getPp() + ")");
                    botonesAtaques[i].setEnabled(true);
                    botonesAtaques[i].setBackground(new Color(100, 200, 100));
                    botonesAtaques[i].setForeground(Color.BLACK);
                } else {
                    botonesAtaques[i].setText(ataques[i].getNombre() + " (SIN PP)");
                    botonesAtaques[i].setEnabled(false);
                    botonesAtaques[i].setBackground(Color.LIGHT_GRAY);
                    botonesAtaques[i].setForeground(Color.DARK_GRAY);
                }
            } else {
                botonesAtaques[i].setText("Vacio");
                botonesAtaques[i].setEnabled(false);
                botonesAtaques[i].setBackground(Color.LIGHT_GRAY);
            }
        }
        
        btnTerminarBatalla.setEnabled(true);
    }
    
    private void desactivarBotonesAtaques() {
        for (int i = 0; i < botonesAtaques.length; i++) {
            botonesAtaques[i].setEnabled(false);
            botonesAtaques[i].setBackground(Color.LIGHT_GRAY);
            botonesAtaques[i].setText("Ataque " + (i+1));
        }
        
        btnTerminarBatalla.setEnabled(false);
    }
    
    private void terminarBatallaActual() {
        if (batallaActual == null) {
            JOptionPane.showMessageDialog(this, "No hay batalla activa", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estas seguro de que quieres terminar la batalla actual?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            textArea.append("\n=== BATALLA TERMINADA POR EL USUARIO ===\n");
            
            if (!batallaActual.isBatallaTerminada()) {
                int hpJugador = batallaActual.getJugador().getHp();
                int hpRival = batallaActual.getRival().getHp();
                
                if (hpJugador > hpRival) {
                    textArea.append("¡" + batallaActual.getJugador().getNombre() + " gana por tener mas HP!\n");
                } else if (hpRival > hpJugador) {
                    textArea.append("¡" + batallaActual.getRival().getNombre() + " gana por tener mas HP!\n");
                } else {
                    textArea.append("¡Empate!\n");
                }
            }
            
            batallaActual = null;
            desactivarBotonesAtaques();
        }
    }
    
    private void mostrarBienvenida() {
        textArea.append("======================================\n");
        textArea.append("   SIMULADOR DE BATALLA POKEMON\n");
        textArea.append("======================================\n");
        textArea.append("¡Bienvenido entrenador!\n\n");
        textArea.append("Instrucciones:\n");
        textArea.append("1. Haz clic en 'Nueva Batalla' para comenzar\n");
        textArea.append("2. Usa los botones de abajo para seleccionar ataques\n");
        textArea.append("3. ¡Gana la batalla!\n\n");
    }
    
    private void mostrarPokedexGUI() {
        textArea.append("\n=== POKEDEX ===\n");
        
        Pokemons[] todos = obtenerTodosPokemon();
        
        for (int i = 0; i < todos.length; i++) {
            if (todos[i] != null) {
                textArea.append((i+1) + ". " + todos[i].getInfo() + "\n");
            }
        }
        
        String[] opcionesPokedex = {"Buscar por tipo", "Pokemon mas fuerte", "Tabla de efectividad", "Cerrar"};
        int opcion = JOptionPane.showOptionDialog(
            this,
            "Seleccione una opcion:",
            "Submenu Pokedex",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opcionesPokedex,
            opcionesPokedex[3]
        );
        
        switch(opcion) {
            case 0:
                buscarPorTipoGUI();
                break;
            case 1:
                pokemonMasFuerteGUI();
                break;
            case 2:
                mostrarTablaEfectividadGUI();
                break;
        }
    }
    
    private void buscarPorTipoGUI() {
        String[] tipos = {"Fuego", "Agua", "Planta", "Electrico"};
        String tipo = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el tipo:",
            "Busqueda por tipo",
            JOptionPane.QUESTION_MESSAGE,
            null,
            tipos,
            tipos[0]
        );
        
        if (tipo != null) {
            textArea.append("\n=== POKEMON DE TIPO " + tipo.toUpperCase() + " ===\n");
            Pokemons[] todos = obtenerTodosPokemon();
            int encontrados = 0;
            
            for (Pokemons p : todos) {
                if (p != null && p.getTipo().equalsIgnoreCase(tipo)) {
                    textArea.append("- " + p.getNombre() + "\n");
                    encontrados++;
                }
            }
            
            if (encontrados == 0) {
                textArea.append("No se encontraron Pokemon de ese tipo.\n");
            } else {
                textArea.append("Total: " + encontrados + " Pokemon\n");
            }
        }
    }
    
    private void pokemonMasFuerteGUI() {
        Pokemons fuerte = pokedex.pokemonMasFuerte();
        if (fuerte != null) {
            textArea.append("\n=== POKEMON MAS FUERTE ===\n");
            textArea.append("Nombre: " + fuerte.getNombre() + "\n");
            textArea.append("Tipo: " + fuerte.getTipo() + "\n");
            textArea.append("Ataque: " + fuerte.getAtaque() + "\n");
            textArea.append("Defensa: " + fuerte.getDefensa() + "\n");
            textArea.append("HP: " + fuerte.getHp() + "\n");
        }
    }
    
    private void mostrarTablaEfectividadGUI() {
        textArea.append("\n=== TABLA DE EFECTIVIDAD ===\n");
        textArea.append("Fuego > Planta (x2.0)\n");
        textArea.append("Fuego < Agua (x0.5)\n");
        textArea.append("Agua > Fuego (x2.0)\n");
        textArea.append("Agua < Planta (x0.5)\n");
        textArea.append("Planta > Agua (x2.0)\n");
        textArea.append("Planta < Fuego (x0.5)\n");
        textArea.append("Electrico > Agua (x2.0)\n");
        textArea.append("Electrico < Planta (x0.5)\n");
    }
    
    private void practicarAtaquesGUI() {
        textArea.append("\n=== PRACTICA DE ATAQUES ===\n");
        
        Pokemons practica = new Pokemons("Pikachu", "Electrico", 100, 55, 40);
        practica.agregarAtaque(new Ataque("Impactrueno", "Electrico", 40, 30), 0);
        practica.agregarAtaque(new Ataque("Ataque Rapido", "Normal", 40, 30), 1);
        practica.agregarAtaque(new Ataque("Onda Trueno", "Electrico", 60, 20), 2);
        practica.agregarAtaque(new Ataque("Cola Ferrea", "Acero", 100, 15), 3);
        
        textArea.append("Pokemon: " + practica.getNombre() + "\n");
        textArea.append("Ataques disponibles:\n");
        Ataque[] ataques = practica.getAtaques();
        for (int i = 0; i < ataques.length; i++) {
            if (ataques[i] != null) {
                textArea.append((i+1) + ". " + ataques[i] + "\n");
            }
        }
        
        String[] opcionesAtaque = new String[4];
        for (int i = 0; i < 4; i++) {
            if (ataques[i] != null) {
                opcionesAtaque[i] = ataques[i].getNombre();
            } else {
                opcionesAtaque[i] = "Vacio";
            }
        }
        
        String ataqueSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona un ataque para practicar:",
            "Practica de Ataques",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcionesAtaque,
            opcionesAtaque[0]
        );
        
        if (ataqueSeleccionado != null && !ataqueSeleccionado.equals("Vacio")) {
            int indice = -1;
            for (int i = 0; i < opcionesAtaque.length; i++) {
                if (opcionesAtaque[i].equals(ataqueSeleccionado)) {
                    indice = i;
                    break;
                }
            }
            
            if (indice != -1 && ataques[indice] != null) {
                int danio = calcularDañoPractica(ataques[indice].getPoder());
                textArea.append("\nUsando " + ataqueSeleccionado + "...\n");
                textArea.append("Daño infligido: " + danio + "\n");
                textArea.append("PP restante: " + (ataques[indice].getPp() - 1) + "/" + 
                               ataques[indice].getPpMax() + "\n");
            }
        }
    }
    
    private int calcularDañoPractica(int poder) {
        return poder + random.nextInt(20);
    }
    
    private void menuConfiguracionGUI() {
        textArea.append("\n=== CONFIGURACION ===\n");
        
        String[] opciones = {"Cambiar dificultad", "Mostrar estadisticas", "Reiniciar datos", "Cerrar"};
        int opcion = JOptionPane.showOptionDialog(
            this,
            "Seleccione una opcion:",
            "Configuracion",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[3]
        );
        
        if (opcion == 0) {
            String[] dificultades = {"Facil", "Normal", "Dificil"};
            String dificultad = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione la dificultad:",
                "Configuracion",
                JOptionPane.QUESTION_MESSAGE,
                null,
                dificultades,
                dificultades[1]
            );
            
            if (dificultad != null) {
                textArea.append("Dificultad establecida: " + dificultad + "\n");
            }
        } else if (opcion == 1) {
            textArea.append("\n=== ESTADISTICAS ===\n");
            textArea.append("Pokemon en la Pokedex: 8\n");
            textArea.append("Tipos disponibles: 4\n");
            textArea.append("Ataques por Pokemon: 4\n");
            textArea.append("Nivel de los Pokemon: 50\n");
        } else if (opcion == 2) {
            int confirmar = JOptionPane.showConfirmDialog(
                this,
                "¿Esta seguro de reiniciar todos los datos?",
                "Confirmar Reinicio",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmar == JOptionPane.YES_OPTION) {
                pokedex = new Pokedex();
                textArea.append("Datos reiniciados correctamente.\n");
            }
        }
    }
    
    private void mostrarAcercaDeGUI() {
        String mensaje = "SIMULADOR DE BATALLA POKEMON\n" +
                        "Desarrollado para Proyecto Final\n" +
                        "CARACTERISTICAS IMPLEMENTADAS:\n" +
                        "6 clases diferentes\n" +
                        "Metodos estaticos y no estaticos\n" +
                        "Metodos recursivos\n" +
                        "Sobrecarga de metodos\n" +
                        "Arreglos unidimensionales y bidimensionales\n" +
                        "GUI con seleccion de ataques\n" +
                        "Validaciones robustas\n" +
                        "Menus y submenus\n" +
                        "Uso de JOptionPane\n\n" +
                        "¡Disfruta del juego!";
        
        JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Acerca del Programa",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void salirPrograma() {
        int confirmar = JOptionPane.showConfirmDialog(
            this,
            "¿Esta seguro de que desea salir?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (confirmar == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private Pokemons[] obtenerTodosPokemon() {
        Pokemons[] todos = new Pokemons[8];
        todos[0] = new Pokemons("Charizard", "Fuego", 78, 84, 78);
        todos[1] = new Pokemons("Blastoise", "Agua", 79, 83, 100);
        todos[2] = new Pokemons("Venusaur", "Planta", 80, 82, 83);
        todos[3] = new Pokemons("Pikachu", "Electrico", 35, 55, 40);
        todos[4] = new Pokemons("Arcanine", "Fuego", 90, 110, 80);
        todos[5] = new Pokemons("Lapras", "Agua", 130, 85, 80);
        todos[6] = new Pokemons("Exeggutor", "Planta", 95, 95, 85);
        todos[7] = new Pokemons("Raichu", "Electrico", 60, 90, 55);
        
        for (Pokemons p : todos) {
            if (p != null) {
                p.agregarAtaque(new Ataque("Ataque Rapido", "Normal", 40, 30), 0);
                p.agregarAtaque(new Ataque("Impactrueno", "Electrico", 40, 30), 1);
                p.agregarAtaque(new Ataque("Lanzallamas", "Fuego", 90, 15), 2);
                p.agregarAtaque(new Ataque("Hidrobomba", "Agua", 110, 5), 3);
            }
        }
        
        return todos;
    }
    
    private String getNombrePokemon(int index) {
        String[] nombres = {"Charizard", "Blastoise", "Venusaur", "Pikachu", 
                           "Arcanine", "Lapras", "Exeggutor", "Raichu"};
        return (index >= 0 && index < nombres.length) ? nombres[index] : "Desconocido";
    }
    
    private void mostrarInfoPokemon(int index) {
        String[] tipos = {"Fuego", "Agua", "Planta", "Electrico", "Fuego", "Agua", "Planta", "Electrico"};
        int[] hp = {78, 79, 80, 35, 90, 130, 95, 60};
        int[] ataque = {84, 83, 82, 55, 110, 85, 95, 90};
        int[] defensa = {78, 100, 83, 40, 80, 80, 85, 55};
        
        if (index >= 0 && index < 8) {
            textArea.append("\n--- Informacion Detallada ---\n");
            textArea.append("Nombre: " + getNombrePokemon(index) + "\n");
            textArea.append("Tipo: " + tipos[index] + "\n");
            textArea.append("HP: " + hp[index] + "\n");
            textArea.append("Ataque: " + ataque[index] + "\n");
            textArea.append("Defensa: " + defensa[index] + "\n");
            textArea.append("Nivel: 50\n\n");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
