import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    
    @FXML
    public void initialize() {
        Audio.reproducir("/resources/audio/PantallaInicio.mp3");
    }

    
    public void jugar(ActionEvent event) {
        try {
            
            Audio.detener();
            Audio.reproducir("/resources/audio/SonidoFondo.mp3");

            Parent root = FXMLLoader.load(getClass().getResource("/Tablero.fxml"));

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Chess Game");
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

