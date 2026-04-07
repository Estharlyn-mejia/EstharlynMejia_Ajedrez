import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class TableroController {
    @FXML
    private Pane capaPiezas;

    @FXML
    private Label turno;

    @FXML
    public void initialize() {
        Tablero tablero = new Tablero(capaPiezas, turno);
        tablero.inicializarPiezas();
    }
}
