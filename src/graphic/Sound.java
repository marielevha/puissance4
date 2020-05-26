package graphic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sound {
    public static void playMusic(boolean state) {
        int count = 0;
        while (!state) {
            try {
                String path = "music/Super" + count +".mp3";
                FileInputStream file = new FileInputStream(path);
                Player player = new Player(file);
                player.play();
                System.out.println("end " + count);
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
