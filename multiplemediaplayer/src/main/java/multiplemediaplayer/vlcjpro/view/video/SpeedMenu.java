package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SpeedMenu extends JMenu {

    private final OutOfProcessMediaPlayerComponent mediaPlayerComponent;

    public SpeedMenu(final OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super("Speed");
        setMnemonic('e');

        this.mediaPlayerComponent = mediaPlayerComponent;

        add(new AbstractAction("Faster") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().setRate(4.f);
            }
        });

        add(new AbstractAction("Faster (fine)") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().setRate(2.f);
            }
        });

        add(new AbstractAction("Normal speed") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().setRate(1.f);
            }
        });

        add(new AbstractAction("Slower (fine)") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().setRate(0.5f);
            }
        });

        add(new AbstractAction("Slower") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().setRate(0.25f);
            }
        });
    }

}
