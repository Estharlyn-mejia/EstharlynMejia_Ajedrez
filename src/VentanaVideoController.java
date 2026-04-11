import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VentanaVideoController {

    @FXML
    private MediaView mediaView;

    @FXML
    public void initialize() {
        try {
            String videoPath = getClass().getResource("/resources/video/GanaNegro.mp4").toExternalForm();
            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            
            // Cuando el video termina, cerrar ventana y mostrar VentanaFinal
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                Stage stage = (Stage) mediaView.getScene().getWindow();
                stage.close();
                mostrarVentanaFinal();
            });
            
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarVentanaFinal() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/VentanaFinal.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chess Game");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
