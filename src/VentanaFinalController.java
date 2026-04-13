import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VentanaFinalController {

    @FXML
    private Button btnVolverJugar;

    @FXML
    private Button btnMenu;

    @FXML
    public void initialize() {

        btnVolverJugar.setOnAction(e -> {
            System.out.println("Reiniciar juego");
            abrirVentana("/Tablero.fxml", e);
        });

        btnMenu.setOnAction(e -> {
            System.out.println("Ir al menú");
            abrirVentana("/VentanaInicio.fxml", e);
        });
    }

    private void abrirVentana(String ruta, javafx.event.ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();

            // cerrar ventana actual
            cerrarVentana(e);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cerrarVentana(javafx.event.ActionEvent e) {
        Stage stage = (Stage) btnMenu.getScene().getWindow();
        stage.close();
    }
}