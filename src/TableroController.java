import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TableroController {

    @FXML
    private Pane capaPiezas;

    @FXML
    private Label turno;

    @FXML
    public void initialize() {
        Tablero tablero = new Tablero(capaPiezas, turno, this::mostrarVentanaVideo);
        tablero.inicializarPiezas();
    }

    private void mostrarVentanaVideo() {
        try {
            // ⏱ Espera 2 segundos
            PauseTransition pause = new PauseTransition(Duration.seconds(2));

            pause.setOnFinished(e -> {
                try {
                    // 🔊 Audio CORRECTO (sin /resources)
                    Audio.reproducirEfecto("/audio/ganarNegra.mp3");

                    // 🪟 Cambiar escena
                    Stage stage = (Stage) turno.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaVideo.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.setTitle("Chess Game");
                    stage.centerOnScreen();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            pause.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}