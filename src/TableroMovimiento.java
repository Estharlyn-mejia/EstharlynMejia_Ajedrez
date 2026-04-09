public class TableroMovimiento {

    private String[][] tablero;

    public TableroMovimiento(String[][] tablero) {
        this.tablero = tablero;
    }

    public TableroMovimiento() {
        this.tablero = new String[8][8];
        inicializarTablero();
    }

    public void inicializarTablero() {
        tablero = new String[][]{
            {"t", "c", "a", "r", "q", "a", "c", "t"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"T", "C", "A", "R", "Q", "A", "C", "T"}
        };
    }

    public void LimpiarTablero() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j].equals("☘")) {
                    tablero[i][j] = "";
                }
            }
        }
    }

    public void marcarMovimientoPeon(int fila, int columna) {
        String pieza = tablero[fila][columna];

        if (pieza.equals("P")) {

            //Movimiento hacia adelante de una casilla
            if (fila - 1 >= 0 && tablero[fila - 1][columna].equals("")) {
                tablero[fila - 1][columna] = "☘";
            }

            //Movimiento inicial de dos casillas desde la posición inicial de dos casillas
            if (fila == 6 && tablero[fila - 1][columna].equals("") && tablero[fila - 2][columna].equals("")) {
                tablero[fila - 2][columna] = "☘";
            }
            // Captura diagonal hacia la izquierda
            if (fila - 1 >= 0 && columna - 1 >= 0 &&
                !tablero[fila - 1][columna - 1].equals("") &&
                tablero[fila - 1][columna - 1].equals(tablero[fila - 1][columna - 1].toLowerCase())) {

                tablero[fila - 1][columna - 1] = "☘";
            }
            // Captura diagonal hacia la derecha
            if (fila - 1 >= 0 && columna + 1 < 8 &&
                !tablero[fila - 1][columna + 1].equals("") &&
                tablero[fila - 1][columna + 1].equals(tablero[fila - 1][columna + 1].toLowerCase())) {

                tablero[fila - 1][columna + 1] = "☘";
            }
        }

        // Movimiento hacia adelante de una casilla para peones
        if (pieza.equals("p")) {

            // Movimiento hacia adelante de una casilla
            if (fila + 1 < 8 && tablero[fila + 1][columna].equals("")) {
                tablero[fila + 1][columna] = "☘";
            }

            // Movimiento inicial de dos casillas desde la posición inicial de dos casillas
            if (fila == 1 && tablero[fila + 1][columna].equals("") && tablero[fila + 2][columna].equals("")) {
                tablero[fila + 2][columna] = "☘";
            }

            // Captura diagonal hacia la izquierda
            if (fila + 1 < 8 && columna - 1 >= 0 &&
                !tablero[fila + 1][columna - 1].equals("") &&
                tablero[fila + 1][columna - 1].equals(tablero[fila + 1][columna - 1].toUpperCase())) {

                tablero[fila + 1][columna - 1] = "☘";
            }

            // Captura diagonal hacia la derecha
            if (fila + 1 < 8 && columna + 1 < 8 &&
                !tablero[fila + 1][columna + 1].equals("") &&
                tablero[fila + 1][columna + 1].equals(tablero[fila + 1][columna + 1].toUpperCase())) {

                tablero[fila + 1][columna + 1] = "☘";
            }
        }
    }

    // Método para marcar los movimientos posibles de una torre
    public void marcarMovimientoTorre(int fila, int columna) {
        String pieza = tablero[fila][columna];
        if (!"T".equals(pieza) && !"t".equals(pieza)) {
            return;
        }

        boolean esBlanca = "T".equals(pieza);

        for (int i = fila - 1; i >= 0; i--) {
            String destino = tablero[i][columna];
            if (destino.equals("")) {
                tablero[i][columna] = "☘";
            } else {
                if (esBlanca && destino.equals(destino.toLowerCase())) {
                    tablero[i][columna] = "☘";
                } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                    tablero[i][columna] = "☘";
                }
                break;
            }
        }

        for (int i = fila + 1; i < 8; i++) {
            String destino = tablero[i][columna];
            if (destino.equals("")) {
                tablero[i][columna] = "☘";
            } else {
                if (esBlanca && destino.equals(destino.toLowerCase())) {
                    tablero[i][columna] = "☘";
                } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                    tablero[i][columna] = "☘";
                }
                break;
            }
        }

        for (int j = columna - 1; j >= 0; j--) {
            String destino = tablero[fila][j];
            if (destino.equals("")) {
                tablero[fila][j] = "☘";
            } else {
                if (esBlanca && destino.equals(destino.toLowerCase())) {
                    tablero[fila][j] = "☘";
                } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                    tablero[fila][j] = "☘";
                }
                break;
            }
        }

        for (int j = columna + 1; j < 8; j++) {
            String destino = tablero[fila][j];
            if (destino.equals("")) {
                tablero[fila][j] = "☘";
            } else {
                if (esBlanca && destino.equals(destino.toLowerCase())) {
                    tablero[fila][j] = "☘";
                } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                    tablero[fila][j] = "☘";
                }
                break;
            }
        }
    }

    public void marcarMovimientoCaballo(int fila, int columna) {
        // Implementar lógica para marcar movimientos del caballo
        String pieza = tablero[fila][columna];
        if (!"C".equals(pieza) && !"c".equals(pieza)) {
            return;
        }
        boolean esBlanca = "C".equals(pieza);
        int[][] movimientos = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };
        for (int[] mov : movimientos) {
            int nuevaFila = fila + mov[0];
            int nuevaColumna = columna + mov[1];
            if (nuevaFila >= 0 && nuevaFila < 8 && nuevaColumna >= 0 && nuevaColumna < 8) {
                String destino = tablero[nuevaFila][nuevaColumna];
                if (destino.equals("")) {
                    tablero[nuevaFila][nuevaColumna] = "☘";
                } else {
                    if (esBlanca && destino.equals(destino.toLowerCase())) {
                        tablero[nuevaFila][nuevaColumna] = "☘";
                    } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                        tablero[nuevaFila][nuevaColumna] = "☘";
                    }
                }
            }
        }
    }

    public void marcarMovimientoAlfil(int fila, int columna) {
        // Implementar lógica para marcar movimientos del alfil
        String pieza = tablero[fila][columna];
        if (!"A".equals(pieza) && !"a".equals(pieza)) {
            return;
        }
        boolean esBlanca = "A".equals(pieza);
        int[][] direcciones = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : direcciones) {
            int f = fila + dir[0];
            int c = columna + dir[1];
            while (f >= 0 && f < 8 && c >= 0 && c < 8) {
                String destino = tablero[f][c];
                if (destino.equals("")) {
                    tablero[f][c] = "☘";
                } else {
                    if (esBlanca && destino.equals(destino.toLowerCase())) {
                        tablero[f][c] = "☘";
                    } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                        tablero[f][c] = "☘";
                    }
                    break;
                }
                f += dir[0];
                c += dir[1];
            }
        }

    }

    public void marcarMovimientoRey(int fila, int columna) {
        // Implementar lógica para marcar movimientos del rey
        String pieza = tablero[fila][columna];
        if (!"R".equals(pieza) && !"r".equals(pieza)) {
            return;
        }
        boolean esBlanca = "R".equals(pieza);
        int[][] movimientos = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };
        for (int[] mov : movimientos) {
            int nuevaFila = fila + mov[0];
            int nuevaColumna = columna + mov[1];
            if (nuevaFila >= 0 && nuevaFila < 8 && nuevaColumna >= 0 && nuevaColumna < 8) {
                String destino = tablero[nuevaFila][nuevaColumna];
                if (destino.equals("")) {
                    tablero[nuevaFila][nuevaColumna] = "☘";
                } else {
                    if (esBlanca && destino.equals(destino.toLowerCase())) {
                        tablero[nuevaFila][nuevaColumna] = "☘";
                    } else if (!esBlanca && destino.equals(destino.toUpperCase())) {
                        tablero[nuevaFila][nuevaColumna] = "☘";
                    }
                }
            }
        }
    }

    public void marcarMovimientoDama(int fila, int columna) {
        // Implementar lógica para marcar movimientos de la dama
        String pieza = tablero[fila][columna];
        if (!"D".equals(pieza) && !"d".equals(pieza)) {
            return;
        }
        marcarMovimientoTorre(fila, columna);
        marcarMovimientoAlfil(fila, columna);
    }

    public Boolean MoverPieza(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        String pieza = tablero[filaOrigen][columnaOrigen];
        if (pieza.equals("") || pieza.equals("☘")) {
            return false;
        }

        LimpiarTablero();

        if (pieza.equals("P") || pieza.equals("p")) {
            marcarMovimientoPeon(filaOrigen, columnaOrigen);
        } else if (pieza.equals("T") || pieza.equals("t")) {
            marcarMovimientoTorre(filaOrigen, columnaOrigen);
        } else if (pieza.equals("C") || pieza.equals("c")) {
            marcarMovimientoCaballo(filaOrigen, columnaOrigen);
        } else if (pieza.equals("A") || pieza.equals("a")) {
            marcarMovimientoAlfil(filaOrigen, columnaOrigen);
        } else if (pieza.equals("R") || pieza.equals("r")) {
            marcarMovimientoRey(filaOrigen, columnaOrigen);
        } else if (pieza.equals("Q") || pieza.equals("q")) {
            marcarMovimientoDama(filaOrigen, columnaOrigen);
        }
        if (tablero[filaDestino][columnaDestino].equals("☘")) {
            tablero[filaDestino][columnaDestino] = pieza;
            tablero[filaOrigen][columnaOrigen] = "";
            LimpiarTablero();
            return true;
        }
        LimpiarTablero();
        return false;
    }
}