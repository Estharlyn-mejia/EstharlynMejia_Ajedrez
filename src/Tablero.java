import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Pane Piezas;
    private final Label turnoLabel;
    private final int TAM = 58;
    private final String[][] tablero = new String[8][8];
    private ImageView piezaSeleccionada = null;
    private int filaSeleccionada, colSeleccionada;
    private final List<ImageView> puntos = new ArrayList<>();
    private boolean turnoBlanco = true;
    private Rectangle jaqueIndicator = null;
    private Runnable onJaqueMate;
    private final List<Rectangle> ataques = new ArrayList<>();

    private boolean reyBlancoMovido = false;
    private boolean reyNegroMovido = false;
    private boolean torreBlancaIzqMovida = false;
    private boolean torreBlancaDerMovida = false;
    private boolean torreNegraIzqMovida = false;
    private boolean torreNegraDerMovida = false;

    public Tablero(Pane Piezas, Label turnoLabel, Runnable onJaqueMate) {
        this.Piezas = Piezas;
        this.turnoLabel = turnoLabel;
        this.onJaqueMate = onJaqueMate;
        inicializarLogica();
        actualizarTurno();
    }

    // Método para inicializar la lógica del tablero, colocando las piezas en sus posiciones iniciales según las reglas del ajedrez
    private void inicializarLogica() {
        for (int i = 0; i < 8; i++) {
            tablero[1][i] = "p";
            tablero[6][i] = "P";
        }
        tablero[0][0] = "t"; tablero[0][7] = "t";
        tablero[7][0] = "T"; tablero[7][7] = "T";
        tablero[0][1] = "c"; tablero[0][6] = "c";
        tablero[7][1] = "C"; tablero[7][6] = "C";
        tablero[0][2] = "a"; tablero[0][5] = "a";
        tablero[7][2] = "A"; tablero[7][5] = "A";
        tablero[0][3] = "q";
        tablero[7][3] = "Q";
        tablero[0][4] = "r";
        tablero[7][4] = "R";
    }

    // Método para inicializar las piezas visuales en el tablero, creando una imagen para cada pieza según su tipo y posición, y asignándoles eventos de clic para manejar la selección y movimientos
    public void inicializarPiezas() {
        Piezas.getChildren().addAll(
            crearPieza("TorreNegro", 0, 0), crearPieza("CaballoNegro", 1, 0), crearPieza("AlfilNegro", 2, 0), crearPieza("ReinaNegro", 3, 0), crearPieza("ReyNegro", 4, 0), crearPieza("AlfilNegro", 5, 0), crearPieza("CaballoNegro", 6, 0), crearPieza("TorreNegro", 7, 0)
        );
        for (int i = 0; i < 8; i++) {
            Piezas.getChildren().add(crearPieza("PeonNegro", i, 1));
        }

        Piezas.getChildren().addAll(
            crearPieza("TorreBlanco", 0, 7), crearPieza("CaballoBlanco", 1, 7), crearPieza("AlfilBlanco", 2, 7), crearPieza("ReinaBlanco", 3, 7), crearPieza("ReyBlanco", 4, 7), crearPieza("AlfilBlanco", 5, 7), crearPieza("CaballoBlanco", 6, 7), crearPieza("TorreBlanco", 7, 7)
        );
        for (int i = 0; i < 8; i++) {
            Piezas.getChildren().add(crearPieza("PeonBlanco", i, 6));
        }
    }

    // Método para crear una pieza visual a partir de su nombre y posición en el tablero, y asignarle un evento de clic para manejar la selección y movimientos
    private ImageView crearPieza(String nombre, int columna, int fila) {
        String ruta = "/resources/image/" + nombre + ".png";
        var url = getClass().getResource(ruta);
        if (url == null) {
            System.out.println("NO SE ENCONTRO: " + ruta);
            return new ImageView();
        }
        Image img = new Image(url.toExternalForm());
        ImageView pieza = new ImageView(img);

        pieza.setFitWidth(TAM);
        pieza.setFitHeight(TAM);
        pieza.setLayoutX(138 + columna * TAM);
        pieza.setLayoutY(138 + fila * TAM);
        pieza.setUserData(new int[]{fila, columna});
        pieza.setOnMouseClicked(e -> {
            int[] pos = (int[]) pieza.getUserData();
            clickPieza(pieza, pos[0], pos[1]);
        });

        return pieza;
    }

    // Método para manejar el clic en una pieza, seleccionándola o mostrando sus movimientos posibles según el estado actual del juego
    private void clickPieza(ImageView pieza, int fila, int columna) {
        String tipo = tablero[fila][columna];

        if (piezaSeleccionada == null) {
            if (!"P".equals(tipo) && !"p".equals(tipo) &&
                !"T".equals(tipo) && !"t".equals(tipo) &&
                !"C".equals(tipo) && !"c".equals(tipo) &&
                !"A".equals(tipo) && !"a".equals(tipo) &&
                !"R".equals(tipo) && !"r".equals(tipo) &&
                !"Q".equals(tipo) && !"q".equals(tipo)) {
                return;
            }
            if ((turnoBlanco && !"P".equals(tipo) && !"T".equals(tipo) && !"C".equals(tipo) &&
                !"A".equals(tipo) && !"R".equals(tipo) && !"Q".equals(tipo)) ||
                (!turnoBlanco && !"p".equals(tipo) && !"t".equals(tipo) && !"c".equals(tipo) &&
                !"a".equals(tipo) && !"r".equals(tipo) && !"q".equals(tipo))) {
                return;
            }
            seleccionarPieza(pieza, fila, columna);
            return;
        }
        // Si se hace clic en la misma pieza seleccionada, deseleccionarla
        if (pieza == piezaSeleccionada) {
            piezaSeleccionada.setOpacity(1.0);
            piezaSeleccionada = null;
            limpiarPuntos();
            return;
        }
        // Si se hace clic en otra pieza del mismo color, cambiar la selección a esa pieza
        String tipoClic = tablero[fila][columna];
        if ((turnoBlanco && ("P".equals(tipoClic) || "T".equals(tipoClic) || "C".equals(tipoClic) ||
                            "A".equals(tipoClic) || "R".equals(tipoClic) || "Q".equals(tipoClic))) ||
            (!turnoBlanco && ("p".equals(tipoClic) || "t".equals(tipoClic) || "c".equals(tipoClic) ||
                            "a".equals(tipoClic) || "r".equals(tipoClic) || "q".equals(tipoClic)))) {
            piezaSeleccionada.setOpacity(1.0);
            limpiarPuntos();
            seleccionarPieza(pieza, fila, columna);
            return;
        }

        piezaSeleccionada.setOpacity(1.0);
        piezaSeleccionada = null;
        limpiarPuntos();
    }

    // Método para seleccionar una pieza, almacenar su posición y mostrar los movimientos posibles
    private void seleccionarPieza(ImageView pieza, int fila, int columna) {
        piezaSeleccionada = pieza;
        filaSeleccionada = fila;
        colSeleccionada = columna;
        piezaSeleccionada.setOpacity(1.0);
        mostrarPosiblesMovimientos(fila, columna); // Mostrar movimientos posibles al seleccionar una pieza, asegurándose de que no se muestren movimientos que dejarían al rey en jaque
    }

    // Método para mostrar los movimientos posibles de una pieza seleccionada, asegurándose de que no se muestren movimientos que dejarían al rey en jaque
    private void mostrarPosiblesMovimientos(int filaOrigen, int columnaOrigen) {
        limpiarPuntos();
        String pieza = tablero[filaOrigen][columnaOrigen];
        if (pieza == null) return;

        if ("P".equals(pieza)) { // Peón blanco
            int f = filaOrigen - 1;
            if (f >= 0 && tablero[f][columnaOrigen] == null && esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen))
                crearPunto(columnaOrigen, f, filaOrigen, columnaOrigen);
            if (filaOrigen == 6 && tablero[5][columnaOrigen] == null && tablero[4][columnaOrigen] == null &&
                esMovimientoLegal(filaOrigen, columnaOrigen, 4, columnaOrigen))
                crearPunto(columnaOrigen, 4, filaOrigen, columnaOrigen);
            if (f >= 0 && columnaOrigen - 1 >= 0 && tablero[f][columnaOrigen - 1] != null &&
                tablero[f][columnaOrigen - 1].equals(tablero[f][columnaOrigen - 1].toLowerCase()) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen - 1))
                crearPunto(columnaOrigen - 1, f, filaOrigen, columnaOrigen);
            if (f >= 0 && columnaOrigen + 1 < 8 && tablero[f][columnaOrigen + 1] != null &&
                tablero[f][columnaOrigen + 1].equals(tablero[f][columnaOrigen + 1].toLowerCase()) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen + 1))
                crearPunto(columnaOrigen + 1, f, filaOrigen, columnaOrigen);
        } 
        else if ("p".equals(pieza)) { // Peón negro
            int f = filaOrigen + 1;
            if (f < 8 && tablero[f][columnaOrigen] == null && esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen))
                crearPunto(columnaOrigen, f, filaOrigen, columnaOrigen);
            if (filaOrigen == 1 && tablero[2][columnaOrigen] == null && tablero[3][columnaOrigen] == null &&
                esMovimientoLegal(filaOrigen, columnaOrigen, 3, columnaOrigen))
                crearPunto(columnaOrigen, 3, filaOrigen, columnaOrigen);
            if (f < 8 && columnaOrigen - 1 >= 0 && tablero[f][columnaOrigen - 1] != null &&
                tablero[f][columnaOrigen - 1].equals(tablero[f][columnaOrigen - 1].toUpperCase()) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen - 1))
                crearPunto(columnaOrigen - 1, f, filaOrigen, columnaOrigen);
            if (f < 8 && columnaOrigen + 1 < 8 && tablero[f][columnaOrigen + 1] != null &&
                tablero[f][columnaOrigen + 1].equals(tablero[f][columnaOrigen + 1].toUpperCase()) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, f, columnaOrigen + 1))
                crearPunto(columnaOrigen + 1, f, filaOrigen, columnaOrigen);
        } 
        else if ("T".equals(pieza) || "t".equals(pieza)) {
            boolean esBlanca = "T".equals(pieza);
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
            for (int[] d : dirs) {
                int f = filaOrigen + d[0];
                int c = columnaOrigen + d[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if ((esBlanca && destino.equals(destino.toLowerCase())) ||
                            (!esBlanca && destino.equals(destino.toUpperCase()))) {
                            if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                                crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += d[0];
                    c += d[1];
                }
            }
        } 
        else if ("C".equals(pieza) || "c".equals(pieza)) {
            int[][] movs = {{-2,-1},{-2,1},{2,-1},{2,1},{-1,-2},{-1,2},{1,-2},{1,2}};
            for (int[] m : movs) {
                int f = filaOrigen + m[0];
                int c = columnaOrigen + m[1];
                if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null || 
                        ("C".equals(pieza) && destino.equals(destino.toLowerCase())) ||
                        ("c".equals(pieza) && destino.equals(destino.toUpperCase()))) {
                        if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                    }
                }
            }
        } 
        else if ("A".equals(pieza) || "a".equals(pieza)) {
            int[][] dirs = {{-1,-1},{-1,1},{1,-1},{1,1}};
            for (int[] d : dirs) {
                int f = filaOrigen + d[0];
                int c = columnaOrigen + d[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if (("A".equals(pieza) && destino.equals(destino.toLowerCase())) ||
                            ("a".equals(pieza) && destino.equals(destino.toUpperCase()))) {
                            if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                                crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += d[0];
                    c += d[1];
                }
            }
        } 
        else if ("Q".equals(pieza) || "q".equals(pieza)) {
            int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
            for (int[] d : dirs) {
                int f = filaOrigen + d[0];
                int c = columnaOrigen + d[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if (("Q".equals(pieza) && destino.equals(destino.toLowerCase())) ||
                            ("q".equals(pieza) && destino.equals(destino.toUpperCase()))) {
                            if (esMovimientoLegal(filaOrigen, columnaOrigen, f, c))
                                crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += d[0];
                    c += d[1];
                }
            }
        } 
        else if ("R".equals(pieza) || "r".equals(pieza)) {
            int[][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
            for (int[] d : dirs) {
                int f = filaOrigen + d[0];
                int c = columnaOrigen + d[1];
                if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    boolean casillaValida = destino == null ||
                            ("R".equals(pieza) && destino.equals(destino.toLowerCase())) ||
                            ("r".equals(pieza) && destino.equals(destino.toUpperCase()));
                    if (casillaValida && esMovimientoLegal(filaOrigen, columnaOrigen, f, c)) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    }
                }
            }
            // Enroque
            if (puedeEnrocar(filaOrigen, columnaOrigen, filaOrigen, columnaOrigen + 2) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, filaOrigen, columnaOrigen + 2))
                crearPunto(columnaOrigen + 2, filaOrigen, filaOrigen, columnaOrigen);
            if (puedeEnrocar(filaOrigen, columnaOrigen, filaOrigen, columnaOrigen - 2) &&
                esMovimientoLegal(filaOrigen, columnaOrigen, filaOrigen, columnaOrigen - 2))
                crearPunto(columnaOrigen - 2, filaOrigen, filaOrigen, columnaOrigen);
        }
    }

    private boolean esMovimientoLegal(int fO, int cO, int fD, int cD) {
        String pieza = tablero[fO][cO];
        if (pieza == null) return false;

        boolean valido;
        if ("R".equals(pieza) || "r".equals(pieza)) {
            valido = puedeMoverRey(fO, cO, fD, cD);
        } else if ("P".equals(pieza) || "p".equals(pieza)) {
            valido = puedeMoverPeon(fO, cO, fD, cD);
        } else if ("T".equals(pieza) || "t".equals(pieza)) {
            valido = puedeMoverTorre(fO, cO, fD, cD);
        } else if ("C".equals(pieza) || "c".equals(pieza)) {
            valido = puedeMoverCaballo(fO, cO, fD, cD);
        } else if ("A".equals(pieza) || "a".equals(pieza)) {
            valido = puedeMoverAlfil(fO, cO, fD, cD);
        } else if ("Q".equals(pieza) || "q".equals(pieza)) {
            valido = puedeMoverReina(fO, cO, fD, cD);
        } else {
            valido = false;
        }

        if("R".equals(pieza) || "r".equals(pieza)){
            return valido && !casillaBajoAtaque(fD, cD, "R".equals(pieza));
        }
        return valido && !movimientoDejaEnJaque(fO, cO, fD, cD);
    }

    // Método para manejar el jaque mate, mostrando un mensaje y luego abriendo una nueva ventana con el resultado final
    private void manejarJaqueMate() {
    turnoLabel.setText("¡Jaque Mate! " + (turnoBlanco ? "Blanco" : "Negro") + " pierde.");

    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    pause.setOnFinished(event -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaFinal.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Fin del Juego");
            stage.setScene(new Scene(root));
            stage.show();

            // cerrar ventana actual (opcional)
            Stage actual = (Stage) Piezas.getScene().getWindow();
            actual.close();
            // Ejecutar el callback para manejar acciones adicionales después del jaque mate, como mostrar un mensaje o actualizar estadísticas
            if (onJaqueMate != null) {
                onJaqueMate.run();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    });
    pause.play();
}

    // Método para crear un punto visual que representa un movimiento posible y manejar el clic en ese punto para realizar el movimiento
    private void crearPunto(int columna, int fila, int fO, int cO) {
        var stream = getClass().getResourceAsStream("/resources/image/punto.png");
        if (stream == null) {
            System.out.println("NO SE ENCONTRO: /resources/image/punto.png");
            return;
        }
        ImageView punto = new ImageView(new Image(stream));
        punto.setFitWidth(20);
        punto.setFitHeight(20);
        punto.setLayoutX(138 + columna * TAM + (TAM - 20) / 2.0);
        punto.setLayoutY(138 + fila * TAM + (TAM - 20) / 2.0);
        punto.setOpacity(0.8);
    
        punto.setOnMouseClicked(e -> {
            // Ya sabemos que es legal porque solo mostramos movimientos legales
            mover(fO, cO, fila, columna);
            Audio.reproducirEfecto("/resources/audio/SonidoPiesa.mp3");
            cambiarTurno();
    
            if (estaEnJaque(turnoBlanco)) {
                marcarJaque();
                resaltarAtacante();
                Audio.reproducirEfecto("/resources/audio/EstaJaque.mp3");
            } else {
                limpiarJaque();
            }
    
            if (estaEnJaqueMate(turnoBlanco)) {
                limpiarPuntos();
                piezaSeleccionada = null;
                manejarJaqueMate();
                return;
            }
    
            limpiarPuntos();
            piezaSeleccionada = null;
        });
    
        puntos.add(punto);
        Piezas.getChildren().add(punto);
    }

    // Método para limpiar los puntos visuales de los movimientos posibles
    private void limpiarPuntos() {
        for (ImageView punto : puntos) {
            Piezas.getChildren().remove(punto);
        }
        puntos.clear();
    }

    // Método para marcar el cuadrante del rey en rojo cuando está en jaque
    private void marcarJaque() {

        limpiarJaque();
    
        int[] posRey = buscarRey(turnoBlanco);
        if (posRey == null) return;
    
        int fila = posRey[0];
        int col = posRey[1];
    
        Rectangle rect = new Rectangle(TAM, TAM);
        rect.setFill(javafx.scene.paint.Color.RED);
        rect.setOpacity(0.6);
        rect.setLayoutX(140 + col * TAM);
        rect.setLayoutY(137 + fila * TAM);
    
        Piezas.getChildren().add(rect);
        jaqueIndicator = rect;
    
        //  ANIMACIÓN PARPADEO
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rect);
        ft.setFromValue(0.2);
        ft.setToValue(0.8);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }


    private void resaltarAtacante() {
        limpiarAtaques(); // Limpiar ataques anteriores

        int[] posRey = buscarRey(turnoBlanco);
        if (posRey == null) return;
    
        int fR = posRey[0];
        int cR = posRey[1];
    
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
    
                String pieza = tablero[f][c];
    
                if (pieza != null &&
                    ((turnoBlanco && pieza.equals(pieza.toLowerCase())) ||
                    (!turnoBlanco && pieza.equals(pieza.toUpperCase())))) {
    
                    if (puedeAtacar(f, c, fR, cR)) {
    
                        Rectangle ataque = new Rectangle(TAM, TAM);
                        ataque.setFill(javafx.scene.paint.Color.ORANGE);
                        ataque.setOpacity(0.4);
                        ataque.setLayoutX(140 + c * TAM);
                        ataque.setLayoutY(137 + f * TAM);
    
                        Piezas.getChildren().add(ataque);
                        ataques.add(ataque);
                    }
                }
            }
        }
    }
    // Método para limpiar el indicador de jaque
    private void limpiarJaque() {
        if (jaqueIndicator != null) {
            Piezas.getChildren().remove(jaqueIndicator);
            jaqueIndicator = null;
        }
        limpiarAtaques();// Limpiar ataques al limpiar jaque, ya que si el rey ya no está en jaque, los atacantes resaltados ya no son relevantes
    }

    private void limpiarAtaques() {
        for (Rectangle ataque : ataques) {
            Piezas.getChildren().remove(ataque);
        }
        ataques.clear();
    }

    // Método para cambiar el turno entre jugadores
    private void cambiarTurno() {
        turnoBlanco = !turnoBlanco;
        actualizarTurno();
    }

    // Método para actualizar la etiqueta que muestra el turno actual
    private void actualizarTurno() {
        if (turnoLabel != null) {
            turnoLabel.setText("Turno: " + (turnoBlanco ? "Blanco" : "Negro"));
        }
    }

    // Método para remover una pieza visual del tablero después de un movimiento
    private void removerPiezaVisual(int fila, int columna) {
        double x = 138 + columna * TAM;
        double y = 138 + fila * TAM;
        List<ImageView> eliminar = new ArrayList<>();
        for (var nodo : Piezas.getChildren()) {
            if (nodo instanceof ImageView iv) {
                if (Math.abs(iv.getLayoutX() - x) < 1 && Math.abs(iv.getLayoutY() - y) < 1 && iv != piezaSeleccionada) {
                    eliminar.add(iv);
                }
            }
        }
        Piezas.getChildren().removeAll(eliminar);
    }

    // Método para verificar si un movimiento de peón es válido según las reglas del ajedrez
    private boolean puedeMoverPeon(int fO, int cO, int fD, int cD) {
        String pieza = tablero[fO][cO];
        // Verificar si la pieza es un peón y si el movimiento es válido según las reglas del ajedrez
        if (pieza == null) {
            return false;
        }
        // Lógica para el peón blanco
        if ("P".equals(pieza)) {
            // Verificar si el peón puede avanzar una casilla
            if (cO == cD && tablero[fD][cD] == null) {
                if (fD == fO - 1) {
                    return true;
                }
                // Verificar si el peón está en su posición inicial y puede avanzar dos casillas
                if (fO == 6 && fD == fO - 2 && tablero[5][cO] == null) {
                    return true;
                }
            }
            // Verificar si el peón puede capturar una pieza enemiga en diagonal a la izquierda
            if (Math.abs(cD - cO) == 1 && fD == fO - 1 && tablero[fD][cD] != null && tablero[fD][cD].equals(tablero[fD][cD].toLowerCase())) {
                return true;
            }
        }
        // Lógica para el peón negro
        if ("p".equals(pieza)) {
            if (cO == cD && tablero[fD][cD] == null) {
                if (fD == fO + 1) {
                    return true;
                }
                if (fO == 1 && fD == fO + 2 && tablero[2][cO] == null) {
                    return true;
                }
            }
            if (Math.abs(cD - cO) == 1 && fD == fO + 1 && tablero[fD][cD] != null && tablero[fD][cD].equals(tablero[fD][cD].toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean puedeMoverTorre(int fO, int cO, int fD, int cD) {

        if (fO != fD && cO != cD) return false;
    
        int pasoF = Integer.compare(fD, fO);
        int pasoC = Integer.compare(cD, cO);
    
        int f = fO + pasoF;
        int c = cO + pasoC;
    
        while (f != fD || c != cD) {
            if (tablero[f][c] != null) return false;
            f += pasoF;
            c += pasoC;
        }
    
        String destino = tablero[fD][cD];
        if (destino == null) return true;
    
        boolean esBlanca = Character.isUpperCase(tablero[fO][cO].charAt(0));
        boolean destinoBlanco = Character.isUpperCase(destino.charAt(0));

        return esBlanca != destinoBlanco;
    }

    private boolean puedeMoverCaballo(int fO, int cO, int fD, int cD) {
        int df = Math.abs(fD - fO);
        int dc = Math.abs(cD - cO);
        if (!((df == 2 && dc == 1) || (df == 1 && dc == 2))) {
            return false;
        }

        String destino = tablero[fD][cD];
        if (destino == null) {
            return true;
        }

        boolean esBlanca = "C".equals(tablero[fO][cO]);
        return esBlanca ? destino.equals(destino.toLowerCase()) : destino.equals(destino.toUpperCase());
    }

    private boolean puedeMoverAlfil(int fO, int cO, int fD, int cD) {
        int df = Math.abs(fD - fO);
        int dc = Math.abs(cD - cO);
        if (df != dc) {
            return false;
        }

        int dfDir = Integer.compare(fD, fO);
        int dcDir = Integer.compare(cD, cO);

        int f = fO + dfDir;
        int c = cO + dcDir;
        while (f != fD || c != cD) {
            if (tablero[f][c] != null) {
                return false;
            }
            f += dfDir;
            c += dcDir;
        }

        String destino = tablero[fD][cD];
        if (destino == null) {
            return true;
        }

        boolean esBlanca = "A".equals(tablero[fO][cO]);
        return esBlanca ? destino.equals(destino.toLowerCase()) : destino.equals(destino.toUpperCase());
    }
    // Método para verificar si un movimiento de rey es válido según las reglas del ajedrez
    private boolean puedeMoverRey(int fO, int cO, int fD, int cD) {

        String pieza = tablero[fO][cO];
        
    
        int df = Math.abs(fD - fO);
        int dc = Math.abs(cD - cO);
    
        // Movimiento normal (1 casilla)
        if (df <= 1 && dc <= 1) {
    
            String destino = tablero[fD][cD];
    
            // No puede comer su propia pieza
            if (destino != null) {
                boolean destinoBlanco = Character.isUpperCase(destino.charAt(0));
                boolean esBlanco = Character.isUpperCase(pieza.charAt(0));
                if (esBlanco == destinoBlanco) return false;
            }
            return true;
        }

        // ENROQUE
        if (df == 0 && dc == 2) {
            return puedeEnrocar(fO, cO, fD, cD);
        }
    
        return false;
    }
    
    // Método para verificar si el rey puede realizar un enroque según las reglas del ajedrez
    private boolean puedeEnrocar(int fO, int cO, int fD, int cD) {

        String rey = tablero[fO][cO];
        boolean esBlanca = Character.isUpperCase(rey.charAt(0));
    
        //  Si el rey ya se movió
        if (esBlanca && reyBlancoMovido) return false;
        if (!esBlanca && reyNegroMovido) return false;
    
        //  Si está en jaque
        if (estaEnJaque(esBlanca)) return false;

        //  Enroque corto
        if (cD == 6) {
            if(tablero[fO][7] == null || !tablero[fO][7].equals(esBlanca ? "T" : "t")) return false;
            if (tablero[fO][5] != null || tablero[fO][6] != null) return false;
    
            if (esBlanca && torreBlancaDerMovida) return false;
            if (!esBlanca && torreNegraDerMovida) return false;
    
            // No puede pasar por jaque
            if (casillaBajoAtaque(fO, 5, esBlanca) || casillaBajoAtaque(fO, 6, esBlanca)) return false;

            return true;
        }
    
        //  Enroque largo
        if (cD == 2) {
            if(tablero[fO][0] == null || !tablero[fO][0].equals(esBlanca ? "T" : "t")) return false;
            if (tablero[fO][1] != null || tablero[fO][2] != null || tablero[fO][3] != null) return false;
    
            if (esBlanca && torreBlancaIzqMovida) return false;
            if (!esBlanca && torreNegraIzqMovida) return false;
    
            if (casillaBajoAtaque(fO, 3, esBlanca) || casillaBajoAtaque(fO, 2, esBlanca)) return false;
    
            return true;
        }

        return false;
    }

    private boolean casillaBajoAtaque(int fila, int columna, boolean esBlancaRey) {
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                String pieza = tablero[f][c];
                if (pieza == null) continue;
    
                // Solo piezas del color contrario al rey
                boolean esPiezaEnemiga = esBlancaRey ? 
                    Character.isLowerCase(pieza.charAt(0)) : 
                    Character.isUpperCase(pieza.charAt(0));
    
                if (esPiezaEnemiga && puedeAtacar(f, c, fila, columna)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean puedeMoverReina(int fO, int cO, int fD, int cD) {
        String pieza = tablero[fO][cO];
    
        if (pieza == null) return false;
    
        boolean esBlanca = Character.isUpperCase(pieza.charAt(0));
    
        int df = fD - fO;
        int dc = cD - cO;
    
        int absDf = Math.abs(df);
        int absDc = Math.abs(dc);
    
        // Movimiento válido: recto o diagonal
        if (!(fO == fD || cO == cD || absDf == absDc)) {
            return false;
        }
    
        int pasoF = Integer.compare(fD, fO);
        int pasoC = Integer.compare(cD, cO);
    
        int f = fO + pasoF;
        int c = cO + pasoC;
    
        // Verificar camino libre
        while (f != fD || c != cD) {
            if (tablero[f][c] != null) {
                return false;
            }
            f += pasoF;
            c += pasoC;
        }
    
        // Verificar destino
        String destino = tablero[fD][cD];
    
        if (destino == null) return true;
    
        boolean destinoEsBlanco = Character.isUpperCase(destino.charAt(0));
    
        // Solo puede capturar si es del color contrario
        return esBlanca != destinoEsBlanco;
    }

    // Método para buscar la posición del rey en el tablero, lo cual es útil para verificar jaque o jaque mate
    private int[] buscarRey(boolean blanco) {
        String rey = blanco ? "R" : "r";
        for (int f = 0; f < 8; f++) {
            for(int c = 0; c < 8; c++){
                if(rey.equals(tablero[f][c])){
                    return new int[]{f, c};
                }
            }
        }
        return null;
    }

    private boolean estaEnJaque(boolean blanco) {
        int[] posRey = buscarRey(blanco);
        if (posRey == null) return false;
    
        int fR = posRey[0];
        int cR = posRey[1];
    
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                String pieza = tablero[f][c];
                if (pieza != null &&
                    ((blanco && pieza.equals(pieza.toLowerCase())) ||
                    (!blanco && pieza.equals(pieza.toUpperCase())))) {
                    if (puedeAtacar(f, c, fR, cR)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean puedeAtacar(int fO, int cO, int fD, int cD) {
    String pieza = tablero[fO][cO];
    if (pieza == null) return false;

    //  IMPORTANTE: simular sin importar si deja en jaque
    switch (pieza) {

        case "P": // blanco
            return (fD == fO - 1 && Math.abs(cD - cO) == 1);

        case "p": // negro
            return (fD == fO + 1 && Math.abs(cD - cO) == 1);

        case "C":
        case "c":
            return (Math.abs(fD - fO) == 2 && Math.abs(cD - cO) == 1) ||
                    (Math.abs(fD - fO) == 1 && Math.abs(cD - cO) == 2);

        case "T":
        case "t":
            if (fO != fD && cO != cD) return false;
            return caminoLibre(fO, cO, fD, cD);

        case "A":
        case "a":
            if (Math.abs(fD - fO) != Math.abs(cD - cO)) return false;
            return caminoLibre(fO, cO, fD, cD);

        case "Q":
        case "q":
            if (fO == fD || cO == cD || Math.abs(fD - fO) == Math.abs(cD - cO)) {
                return caminoLibre(fO, cO, fD, cD);
            }
            return false;

        case "R":
        case "r":
            return Math.abs(fD - fO) <= 1 && Math.abs(cD - cO) <= 1;
    }

    return false;
}

    private boolean caminoLibre(int fO, int cO, int fD, int cD) {

    int pasoF = Integer.compare(fD, fO); // dirección fila (-1, 0, 1)
    int pasoC = Integer.compare(cD, cO); // dirección columna (-1, 0, 1)

    int f = fO + pasoF;
    int c = cO + pasoC;

    // recorrer hasta llegar al destino (sin incluir destino)
    while (f != fD || c != cD) {

        if (tablero[f][c] != null) {
            return false; // hay una pieza bloqueando
        }

        f += pasoF;
        c += pasoC;
    }

    return true; // camino libre
}
    
    

    // Método para verificar si un movimiento deja al rey en jaque, lo que no es permitido en el ajedrez
    private boolean movimientoDejaEnJaque(int fO, int cO, int fD, int cD) {

        String pieza = tablero[fO][cO];
        String destino = tablero[fD][cD];
    
        // Simular movimiento
        tablero[fD][cD] = pieza;
        tablero[fO][cO] = null;
    
        boolean esBlanca = Character.isUpperCase(pieza.charAt(0));
    
        int[] posRey;
    
        if (pieza.equalsIgnoreCase("R")) {
            posRey = new int[]{fD, cD};
        } else {
            posRey = buscarRey(esBlanca);
        }
    
        boolean enJaque = false;
    
        if (posRey != null) {
            int fR = posRey[0];
            int cR = posRey[1];
    
            for (int f = 0; f < 8; f++) {
                for (int c = 0; c < 8; c++) {
    
                    String enemigo = tablero[f][c];
    
                    if (enemigo != null &&
                        ((esBlanca && enemigo.equals(enemigo.toLowerCase())) ||
                        (!esBlanca && enemigo.equals(enemigo.toUpperCase())))) {
    
                        if (puedeAtacar(f, c, fR, cR)) {
                            enJaque = true;
                            break;
                        }
                    }
                }
                if (enJaque) break;
            }
        }
    
        // Restaurar tablero
        tablero[fO][cO] = pieza;
        tablero[fD][cD] = destino;
    
        return enJaque;
    }
    



    
    // Método para verificar si el jugador actual está en jaque mate, lo que significa que su rey está en jaque y no tiene movimientos legales para salir de esa situación
    private boolean estaEnJaqueMate(boolean blanco) {
        if (!estaEnJaque(blanco)) {
            return false;
        }

        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                String pieza = tablero[f][c];
                if (pieza != null && ((blanco && Character.isUpperCase(pieza.charAt(0))) || (!blanco && Character.isLowerCase(pieza.charAt(0))))) {
                    for (int fD = 0; fD < 8; fD++) {
                        for (int cD = 0; cD < 8; cD++) {
                            if(
                                (("P".equals(pieza) || "p".equals(pieza)) && puedeMoverPeon(f, c, fD, cD)) ||
                                (("T".equals(pieza) || "t".equals(pieza)) && puedeMoverTorre(f, c, fD, cD)) ||
                                (("C".equals(pieza) || "c".equals(pieza)) && puedeMoverCaballo(f, c, fD, cD)) ||
                                (("A".equals(pieza) || "a".equals(pieza)) && puedeMoverAlfil(f, c, fD, cD)) ||
                                (("R".equals(pieza) || "r".equals(pieza)) && puedeMoverRey(f, c, fD, cD)) ||
                                (("Q".equals(pieza) || "q".equals(pieza)) && puedeMoverReina(f, c, fD, cD))
                            ){
                                if (!movimientoDejaEnJaque(f, c, fD, cD)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void reiniciar() {
        // Limpiar tablero lógico
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                tablero[f][c] = null;
            }
        }
        // Limpiar tablero visual
        Piezas.getChildren().removeIf(n -> n instanceof ImageView || n instanceof Rectangle);       
        piezaSeleccionada = null;
        turnoBlanco = true;
        inicializarLogica();
        actualizarTurno();
        inicializarPiezas();
    }

    public void rendirse() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaFinal.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Fin del Juego");
        stage.setScene(new Scene(root));
        stage.show();

        Stage actual = (Stage) Piezas.getScene().getWindow();
        actual.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void reemplazarPieza(int f, int c, String nombrePieza){
        double x = 138 + c * TAM;
        double y = 138 + f * TAM;
    
        Piezas.getChildren().removeIf(n -> n instanceof Rectangle);
    
        List<Object> copia = new ArrayList<>(Piezas.getChildren());
        ImageView piezaEliminar = null;
    
        for(var nodo : copia){
            if(nodo instanceof ImageView iv){
                if(Math.abs(iv.getLayoutX() - x) < 3 && Math.abs(iv.getLayoutY() - y) < 3){
                    piezaEliminar = iv;
                    break;
                }
            }
        }

        if (piezaEliminar != null) {
            Piezas.getChildren().remove(piezaEliminar);
        }
    
        var stream = getClass().getResourceAsStream("/resources/image/" + nombrePieza + ".png");
        if (stream == null) {
            System.out.println("NO SE ENCONTRO: /resources/image/" + nombrePieza + ".png");
            return;
        }
    
        ImageView nuevaPieza = new ImageView(new Image(stream));
        nuevaPieza.setFitWidth(TAM);
        nuevaPieza.setFitHeight(TAM);
        nuevaPieza.setLayoutX(x);
        nuevaPieza.setLayoutY(y);
        nuevaPieza.setUserData(new int[]{f, c});
        nuevaPieza.setOnMouseClicked(e -> clickPieza(nuevaPieza, f, c));
        Piezas.getChildren().add(nuevaPieza);
    }

    private void verificarCoronacion(int fD, int cD) {
        String pieza = tablero[fD][cD];
        if(pieza == null) {
            return;
        }
        if("P".equals(pieza) && fD == 0){
            tablero[fD][cD] = "Q";
            reemplazarPieza(fD, cD, "ReinaBlanco");
        }

        if("p".equals(pieza) && fD == 7){
            tablero[fD][cD] = "q";
            reemplazarPieza(fD, cD, "ReinaNegro");
        }
    }

    private void moverTorre(int fO, int cO, int fD, int cD) {

        removerPiezaVisual(fD, cD);
    
        tablero[fD][cD] = tablero[fO][cO];
        tablero[fO][cO] = null;
    

        for (var nodo : Piezas.getChildren()) {
            if (nodo instanceof ImageView iv) {
                int[] pos = (int[]) iv.getUserData();
                if (pos[0] == fO && pos[1] == cO) {
                    iv.setLayoutX(138 + cD * TAM);
                    iv.setLayoutY(138 + fD * TAM);
                    iv.setUserData(new int[]{fD, cD});
                    break;
                }
            }
        }
    }


    // Método para realizar el movimiento de una pieza en el tablero y actualizar la interfaz gráfica
    private void mover(int fO, int cO, int fD, int cD) {

        String pieza = tablero[fO][cO];

        // ENROQUE
        if ((pieza.equals("R") || pieza.equals("r")) && Math.abs(cD - cO) == 2) {

            // corto
            if (cD == 6) {
                moverTorre(fO, 7, fO, 5);
            }
            // largo
            else if (cD == 2) {
                moverTorre(fO, 0, fO, 3);
            }
        }

        removerPiezaVisual(fD, cD);
    
        tablero[fD][cD] = pieza;
        tablero[fO][cO] = null;
    
        piezaSeleccionada.setLayoutX(138 + cD * TAM);
        piezaSeleccionada.setLayoutY(138 + fD * TAM);
        piezaSeleccionada.setUserData(new int[]{fD, cD});
    
        // marcar movimientos
        if (pieza.equals("R")) reyBlancoMovido = true;
        if (pieza.equals("r")) reyNegroMovido = true;
    
        if (pieza.equals("T") && cO == 0) torreBlancaIzqMovida = true;
        if (pieza.equals("T") && cO == 7) torreBlancaDerMovida = true;
    
        if (pieza.equals("t") && cO == 0) torreNegraIzqMovida = true;
        if (pieza.equals("t") && cO == 7) torreNegraDerMovida = true;
    
        verificarCoronacion(fD, cD);
        piezaSeleccionada = null;
    }
}    