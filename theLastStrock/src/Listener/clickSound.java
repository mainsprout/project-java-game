package Listener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class clickSound {
    public static Clip buttonclip;
    public static void clickSound(){
        try {
            buttonclip = AudioSystem.getClip();
            File audioFile = new File("music/effect/click.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            buttonclip.open(audioStream);
            buttonclip.start();
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
