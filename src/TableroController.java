import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class TableroController {
    @FXML
    private Pane capaPiezas;

    @FXML
    public void initialize() {
        Tablero tablero = new Tablero(capaPiezas);
        tablero.inicializarPiezas();
    }
}
