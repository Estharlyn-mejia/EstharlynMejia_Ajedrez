import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VentanaFinalController {

    @FXML
    private Pane rootPane;

    @FXML
    private Button btnVolverJugar;

    @FXML
    private Button btnMenu;

    @FXML
    public void initialize() {

        // 🔁 Volver a jugar
        btnVolverJugar.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tablero.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) btnVolverJugar.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Chess Game");
                stage.centerOnScreen();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 🏠 Volver al menú
        btnMenu.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/VentanaInicio.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) btnMenu.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Chess Game");
                stage.centerOnScreen();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}