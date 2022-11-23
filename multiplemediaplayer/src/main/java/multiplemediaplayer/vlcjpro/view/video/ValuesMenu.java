package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

// FIXME instead of disabling menus like this, maybe just disable all of them unless we're started

public abstract class ValuesMenu extends JMenu {

    protected final OutOfProcessMediaPlayerComponent mediaPlayerComponent;

    protected ValuesMenu(final OutOfProcessMediaPlayerComponent mediaPlayerComponent, String name, char mnemonic, String[][] values) {
        super(name);
        this.mediaPlayerComponent = mediaPlayerComponent;
        for (final String[] value : values) {
            add(new AbstractAction(value[0]) {
                public void actionPerformed(ActionEvent actionEvent) {
                    if (mediaPlayerComponent.state() == OutOfProcessMediaPlayerComponent.State.STARTED) {
                        onSelected(value[0], value[1]);
                    }
                }
            });
        }
    }

    protected abstract void onSelected(String text, String value);

}
