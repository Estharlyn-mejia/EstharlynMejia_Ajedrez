import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

    public Tablero(Pane Piezas, Label turnoLabel) {
        this.Piezas = Piezas;
        this.turnoLabel = turnoLabel;
        inicializarLogica();
        actualizarTurno();
    }

    // Constructor para pruebas sin interfaz gráfica
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

    // Método para inicializar el tablero visualmente
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
        pieza.setLayoutX(140 + columna * TAM);
        pieza.setLayoutY(137 + fila * TAM);
        pieza.setUserData(new int[]{fila, columna});
        pieza.setOnMouseClicked(e -> {
            int[] pos = (int[]) pieza.getUserData();
            clickPieza(pieza, pos[0], pos[1]);
        });

        return pieza;
    }

    // Método para colocar las piezas en el tablero al iniciar el juego
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

    // Método para manejar el clic en una pieza y mostrar los posibles movimientos
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

        if (pieza == piezaSeleccionada) {
            piezaSeleccionada.setOpacity(1.0);
            piezaSeleccionada = null;
            limpiarPuntos();
            return;
        }

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

    // Método para seleccionar una pieza y mostrar sus posibles movimientos
    private void seleccionarPieza(ImageView pieza, int fila, int columna) {
        piezaSeleccionada = pieza;
        filaSeleccionada = fila;
        colSeleccionada = columna;
        piezaSeleccionada.setOpacity(1.0);
        mostrarPosiblesMovimientos(fila, columna);
    }

    // Método para mostrar los posibles movimientos de una pieza seleccionada
    private void mostrarPosiblesMovimientos(int filaOrigen, int columnaOrigen) {
        limpiarPuntos();
        String pieza = tablero[filaOrigen][columnaOrigen];
        if (pieza == null) {
            return;
        }

        if ("P".equals(pieza)) {
            int f1 = filaOrigen - 1;
            if (f1 >= 0 && tablero[f1][columnaOrigen] == null) {
                crearPunto(columnaOrigen, f1, filaOrigen, columnaOrigen);
            }
            if (filaOrigen == 6 && tablero[5][columnaOrigen] == null && tablero[4][columnaOrigen] == null) {
                crearPunto(columnaOrigen, 4, filaOrigen, columnaOrigen);
            }
            if (f1 >= 0 && columnaOrigen - 1 >= 0 && tablero[f1][columnaOrigen - 1] != null &&
                    tablero[f1][columnaOrigen - 1].equals(tablero[f1][columnaOrigen - 1].toLowerCase())) {
                crearPunto(columnaOrigen - 1, f1, filaOrigen, columnaOrigen);
            }
            if (f1 >= 0 && columnaOrigen + 1 < 8 && tablero[f1][columnaOrigen + 1] != null &&
                    tablero[f1][columnaOrigen + 1].equals(tablero[f1][columnaOrigen + 1].toLowerCase())) {
                crearPunto(columnaOrigen + 1, f1, filaOrigen, columnaOrigen);
            }
        } else if ("p".equals(pieza)) {
            int f1 = filaOrigen + 1;
            if (f1 < 8 && tablero[f1][columnaOrigen] == null) {
                crearPunto(columnaOrigen, f1, filaOrigen, columnaOrigen);
            }
            if (filaOrigen == 1 && tablero[2][columnaOrigen] == null && tablero[3][columnaOrigen] == null) {
                crearPunto(columnaOrigen, 3, filaOrigen, columnaOrigen);
            }
            if (f1 < 8 && columnaOrigen - 1 >= 0 && tablero[f1][columnaOrigen - 1] != null &&
                    tablero[f1][columnaOrigen - 1].equals(tablero[f1][columnaOrigen - 1].toUpperCase())) {
                crearPunto(columnaOrigen - 1, f1, filaOrigen, columnaOrigen);
            }
            if (f1 < 8 && columnaOrigen + 1 < 8 && tablero[f1][columnaOrigen + 1] != null &&
                    tablero[f1][columnaOrigen + 1].equals(tablero[f1][columnaOrigen + 1].toUpperCase())) {
                crearPunto(columnaOrigen + 1, f1, filaOrigen, columnaOrigen);
            }
        } else if ("T".equals(pieza) || "t".equals(pieza)) {
            boolean esBlanca = "T".equals(pieza);
            int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : direcciones) {
                int f = filaOrigen + dir[0];
                int c = columnaOrigen + dir[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if (esBlanca && destino.equals(destino.toLowerCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += dir[0];
                    c += dir[1];
                }
            }
        } else if ("C".equals(pieza) || "c".equals(pieza)) {
            boolean esBlanca = "C".equals(pieza);
            // Movimientos posibles del caballo
            int[][] movimientos = {
                {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
                {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
            };
            for (int[] mov : movimientos) {
                int f = filaOrigen + mov[0];
                int c = columnaOrigen + mov[1];
                if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null ||
                        (esBlanca && destino.equals(destino.toLowerCase())) ||
                        (!esBlanca && destino.equals(destino.toUpperCase()))) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    }
                }
            }
        } else if ("A".equals(pieza) || "a".equals(pieza)) {
            boolean esBlanca = "A".equals(pieza);
            // Movimientos posibles del alfil
            int[][] direcciones = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
            for (int[] dir : direcciones) {
                int f = filaOrigen + dir[0];
                int c = columnaOrigen + dir[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if (esBlanca && destino.equals(destino.toLowerCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += dir[0];
                    c += dir[1];
                }
            }
        } else if ("R".equals(pieza) || "r".equals(pieza)) {
            boolean esBlanca = "R".equals(pieza);
            int[][] movimientos = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
            };
            for (int[] mov : movimientos) {
                int f = filaOrigen + mov[0];
                int c = columnaOrigen + mov[1];
                if (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null ||
                        (esBlanca && destino.equals(destino.toLowerCase())) ||
                        (!esBlanca && destino.equals(destino.toUpperCase()))) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    }
                }
            }
        }
        else if ("Q".equals(pieza) || "q".equals(pieza)) {
            boolean esBlanca = "Q".equals(pieza);
            // Movimientos posibles de la reina (combinación de torre y alfil)
            int[][] direcciones = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
            };
            for (int[] dir : direcciones) {
                int f = filaOrigen + dir[0];
                int c = columnaOrigen + dir[1];
                while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                    String destino = tablero[f][c];
                    if (destino == null) {
                        crearPunto(c, f, filaOrigen, columnaOrigen);
                    } else {
                        if (esBlanca && destino.equals(destino.toLowerCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                            crearPunto(c, f, filaOrigen, columnaOrigen);
                        }
                        break;
                    }
                    f += dir[0];
                    c += dir[1];
                }
            }
        }
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
        punto.setLayoutX(140 + columna * TAM + (TAM - 20) / 2.0);
        punto.setLayoutY(137 + fila * TAM + (TAM - 20) / 2.0);
        punto.setOpacity(0.8);
        punto.setOnMouseClicked(e -> {
            String pieza = tablero[fO][cO];
            
            if (
                (("P".equals(pieza) || "p".equals(pieza)) && puedeMoverPeon(fO, cO, fila, columna)) ||
                (("T".equals(pieza) || "t".equals(pieza)) && puedeMoverTorre(fO, cO, fila, columna)) ||
                (("C".equals(pieza) || "c".equals(pieza)) && puedeMoverCaballo(fO, cO, fila, columna)) ||
                (("A".equals(pieza) || "a".equals(pieza)) && puedeMoverAlfil(fO, cO, fila, columna)) ||
                (("R".equals(pieza) || "r".equals(pieza)) && puedeMoverRey(fO, cO, fila, columna)) ||
                (("Q".equals(pieza) || "q".equals(pieza)) && puedeMoverReina(fO, cO, fila, columna))
            ) {
                mover(fO, cO, fila, columna);
                Audio.reproducirEfecto("/resources/audio/SonidoPiesa.mp3");
                cambiarTurno();
            }
            limpiarPuntos();
            if (piezaSeleccionada != null) {
                piezaSeleccionada.setOpacity(1.0);
                piezaSeleccionada = null;
            }
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
        double x = 140 + columna * TAM;
        double y = 137 + fila * TAM;
        List<ImageView> eliminar = new ArrayList<>();
        for (var nodo : Piezas.getChildren()) {
            if (nodo instanceof ImageView iv && iv != piezaSeleccionada) {
                if (iv.getLayoutX() == x && iv.getLayoutY() == y) {
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
        if (fO != fD && cO != cD) {
            return false;
        }

        int df = Integer.compare(fD, fO);
        int dc = Integer.compare(cD, cO);
        int f = fO + df;
        int c = cO + dc;
        while (f != fD || c != cD) {
            if (tablero[f][c] != null) {
                return false;
            }
            f += df;
            c += dc;
        }

        String destino = tablero[fD][cD];
        if (destino == null) {
            return true;
        }

        boolean esBlanca = "T".equals(tablero[fO][cO]);
        return esBlanca ? destino.equals(destino.toLowerCase()) : destino.equals(destino.toUpperCase());
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
        while (f != fD && c != cD) {
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
        int df = Math.abs(fD - fO);
        int dc = Math.abs(cD - cO);
        if (df > 1 || dc > 1) {
            return false;
        }

        String destino = tablero[fD][cD];
        if (destino == null) {
            return true;
        }

        boolean esBlanca = "R".equals(tablero[fO][cO]);
        return esBlanca ? destino.equals(destino.toLowerCase()) : destino.equals(destino.toUpperCase());
    }

    private boolean puedeMoverReina(int fO, int cO, int fD, int cD) {
        String pieza = tablero[fO][cO];
    
        if (pieza == null) {
            return false;
        }
    
        boolean esBlanca = pieza.equals(pieza.toUpperCase());
    
        int df = fD - fO;
        int dc = cD - cO;
    
        int absDf = Math.abs(df);
        int absDc = Math.abs(dc);
    
        // Verificar si el movimiento es válido (recto o diagonal)
        if (!(fO == fD || cO == cD || absDf == absDc)) {
            return false;
        }
    
        int pasoF = Integer.compare(fD, fO);
        int pasoC = Integer.compare(cD, cO);
    
        int f = fO + pasoF;
        int c = cO + pasoC;
    
        // Verificar que no haya piezas en el camino
        while (f != fD || c != cD) {
            if (tablero[f][c] != null) {
                return false;
            }
            f += pasoF;
            c += pasoC;
        }
    
        // Verificar captura
        String destino = tablero[fD][cD];
        if (destino == null) {
            return true;
        }
    
        return esBlanca
            ? destino.equals(destino.toLowerCase())
            : destino.equals(destino.toUpperCase());
    }

    // Método para realizar el movimiento de una pieza en el tablero y actualizar la interfaz gráfica
    private void mover(int fO, int cO, int fD, int cD) {
        tablero[fD][cD] = tablero[fO][cO];
        tablero[fO][cO] = null;

        removerPiezaVisual(fD, cD);

        piezaSeleccionada.setLayoutX(140 + cD * TAM);
        piezaSeleccionada.setLayoutY(137 + fD * TAM);
        piezaSeleccionada.setUserData(new int[]{fD, cD});
    }
}