import javax.sound.sampled.*;
import java.net.URL;

public class MusicPlayer {
    final String MUSIC_FILE = "\\audio\\MainTheme.wav";
    Clip clip;
    boolean isPlaying;

    MusicPlayer() {
        isPlaying=false;
        URL audioFile = getClass().getResource(MUSIC_FILE);
        AudioInputStream audio = null;
        try {
            audio = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            if (clip == null) return;
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip == null) return;
        clip.stop();
        isPlaying=false;
    }
}
