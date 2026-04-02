import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Tablero{
    private Pane Piezas;
    private final int TAM = 58;

    public Tablero(Pane Piezas){
        this.Piezas = Piezas;
    }

    private ImageView crearPieza(String nombre, int columna, int fila){
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
    
        pieza.setLayoutX(140 +columna * TAM);
        pieza.setLayoutY(137 +fila * TAM);
    
        return pieza;
    }

    public void inicializarPiezas(){
        Piezas.getChildren().addAll(crearPieza("TorreNegro", 0, 0), crearPieza("CaballoNegro", 1, 0), crearPieza("AlfilNegro", 2, 0), crearPieza("ReinaNegro", 3, 0), crearPieza("ReyNegro", 4, 0), crearPieza("AlfilNegro", 5, 0), crearPieza("CaballoNegro", 6, 0), crearPieza("TorreNegro", 7, 0)); 
        for (int i = 0; i < 8; i++) {
            Piezas.getChildren().add(crearPieza("PeonNegro", i, 1));
        }

        Piezas.getChildren().addAll(crearPieza("TorreBlanco", 0, 7), crearPieza("CaballoBlanco", 1, 7), crearPieza("AlfilBlanco", 2, 7), crearPieza("ReinaBlanco", 3, 7), crearPieza("ReyBlanco", 4, 7), crearPieza("AlfilBlanco", 5, 7), crearPieza("CaballoBlanco", 6, 7), crearPieza("TorreBlanco", 7, 7));
        for (int i = 0; i < 8; i++) {
            Piezas.getChildren().add(crearPieza("PeonBlanco", i, 6));
        }
    }             
}