package graphic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sound {
    public static void playMusic() {
        int count = 0;
        while (true) {
            try {
                String path = "music/Super" + count +".mp3";
                FileInputStream file = new FileInputStream(path);
                Player player = new Player(file);
                player.play();
                count++;
                if (count > 2) {
                    count = 0;
                }
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }
}
