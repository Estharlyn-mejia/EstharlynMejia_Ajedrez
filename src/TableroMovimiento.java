public class TableroMovimiento {
    
    String[][] tablero = new String[8][8];
    public TableroMovimiento(){
        inicializarTablero();
    }

    public void inicializarTablero(){
        tablero = new String[][]{
            {"t", "c", "a", "r", "d", "a", "c", "t"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"T", "C", "A", "R", "D", "A", "C", "T"}
        };
    }

    public void LimpiarTablero(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tablero[i][j] = "☘";
            }
        }
    }

    public void marcarMovimiento(int fila, int columna){
        LimpiarTablero();

        String pieza = tablero[fila][columna];
        if(pieza.equals("P")){
            // Verificar si el peón puede avanzar una casilla
            if (fila - 1 >= 0 && tablero[fila - 1][columna].equals("")) {
                tablero[fila - 1][columna] = "☘";
            }

            // Verificar si el peón está en su posición inicial y puede avanzar dos casillas
            if(fila == 6 && tablero[fila - 1][columna].equals("") && tablero[fila - 2][columna].equals("")){
                tablero[fila - 2][columna] = "☘";
            }

            // Verificar si el peón puede capturar una pieza enemiga en diagonal a la izquierda
            if(fila -1 >= 0 && columna - 1 >= 0 && !tablero[fila - 1][columna -1].equals("") && tablero[fila - 1][columna -1].equals(tablero[fila -1][columna -1].toLowerCase())){
                tablero[fila - 1][columna -1] = "☘";
            }

            // Verificar si el peón puede capturar una pieza enemiga en diagonal a la derecha
            if(fila -1 >= 0 && columna + 1 < 8 && !tablero[fila - 1][columna +1].equals("") && tablero[fila - 1][columna +1].equals(tablero[fila -1][columna +1].toLowerCase())){
                tablero[fila - 1][columna +1] = "☘";
            }
        }
        if(pieza.equals("p")){
            // Verificar si el peón puede avanzar una casilla
            if (fila + 1 < 8 && tablero[fila + 1][columna].equals("")) {
                tablero[fila + 1][columna] = "☘";
            }

            // Verificar si el peón está en su posición inicial y puede avanzar dos casillas
            if(fila == 1 && tablero[fila + 1][columna].equals("") && tablero[fila + 2][columna].equals("")){
                tablero[fila + 2][columna] = "☘";
            }

            // Verificar si el peón puede capturar una pieza enemiga en diagonal a la izquierda
            if(fila +1 < 8 && columna - 1 >= 0 && !tablero[fila + 1][columna -1].equals("") && tablero[fila + 1][columna -1].equals(tablero[fila +1][columna -1].toUpperCase())){
                tablero[fila + 1][columna -1] = "☘";
            }

            // Verificar si el peón puede capturar una pieza enemiga en diagonal a la derecha
            if(fila +1 < 8 && columna + 1 < 8 && !tablero[fila + 1][columna +1].equals("") && tablero[fila + 1][columna +1].equals(tablero[fila +1][columna +1].toUpperCase())){
                tablero[fila + 1][columna +1] = "☘";
            }
        }
    }
}
