import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Audio {
    private static MediaPlayer player;

    // Método para reproducir música de fondo, deteniendo cualquier música que ya esté sonando
    public static void reproducir(String ruta){
        try {
            if(player != null){
                player.stop();
            }

            String mediaUrl = buscarMediaURL(ruta);
            if (mediaUrl == null) {
                return;
            }

            Media media = new Media(mediaUrl);
            player = new MediaPlayer(media);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        } catch (Exception e) {
            System.out.println("ERROR reproduciendo audio: " + ruta);
            e.printStackTrace();
            if(player != null){
                player.stop();
            }
        }
    }

    // Método para reproducir un efecto de sonido sin afectar la música de fondo
    public static void reproducirEfecto(String ruta){
        try {
            String mediaUrl = buscarMediaURL(ruta);
            if (mediaUrl == null) {
                return;
            }

            Media media = new Media(mediaUrl);
            MediaPlayer efecto = new MediaPlayer(media);
            efecto.setCycleCount(1);
            efecto.play();
            efecto.setOnEndOfMedia(() -> {
                efecto.stop();
                efecto.dispose();
            });
        } catch (Exception e) {
            System.out.println("ERROR reproducir efecto: " + ruta);
            e.printStackTrace();
        }
    }

    // Método para buscar la URL de un archivo de audio en el classpath o en bin/
    private static String buscarMediaURL(String ruta) {
        var resource = Audio.class.getResource(ruta);
        if (resource != null) {
            return resource.toExternalForm();
        }

        File audioFile = new File(ruta);
        if (audioFile.exists()) {
            return audioFile.toURI().toString();
        }

        File audioBin = new File("bin/" + ruta.replaceFirst("^/", ""));
        if (audioBin.exists()) {
            return audioBin.toURI().toString();
        }

        System.out.println("NO SE ENCONTRO: " + ruta);
        return null;
    }

    // Método para detener la música de fondo actual
    public static void detener(){
        if(player != null){
            player.stop();
        }
    }
}
