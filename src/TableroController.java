import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TableroController {

    @FXML
    private Pane capaPiezas;

    @FXML
    private Button btnReiniciar;

    @FXML
    private Button btnRendirse;

    @FXML
    private Label turno;

    private Tablero tablero;

    @FXML
    public void initialize() {

        tablero = new Tablero(capaPiezas, turno, this::mostrarVentanaFinal);
        tablero.inicializarPiezas();

        btnReiniciar.setOnAction(e -> tablero.reiniciar());

        btnRendirse.setOnAction(e -> tablero.rendirse());
    }

    public void mostrarVentanaFinal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaFinal.fxml"));
            Parent root = loader.load();

            Stage nueva = new Stage();
            nueva.setScene(new Scene(root));
            nueva.setTitle("Fin de la partida");

            // cerrar ventana actual
            Stage actual = (Stage) turno.getScene().getWindow();
            actual.close();

            nueva.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}