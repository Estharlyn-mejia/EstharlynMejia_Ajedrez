import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Audio {
    private static MediaPlayer player;

    public static void reproducir(String ruta){
        try {
            if(player != null){
                player.stop();
            }
            Media media = new Media(Audio.class.getResource(ruta).toExternalForm());
            player = new MediaPlayer(media);

            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        } catch (Exception e) {
            System.out.println("NO SE ENCONTRO: " + ruta);
            player.stop();
            // TODO: handle exception
        }
    }

    public static void detener(){
        if(player != null){
            player.stop();
        }
    }
}
