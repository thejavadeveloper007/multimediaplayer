package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcj.player.base.AudioDevice;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class AudioDeviceMenu extends OnDemandMenu {

    protected AudioDeviceMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Audio Device", 'd', true);
    }

    @Override
    protected void onPrepareMenu(JMenu menu) {
        if (mediaPlayerComponent.state() == OutOfProcessMediaPlayerComponent.State.STARTED) {
            for (final AudioDevice audioDevice : mediaPlayerComponent.mediaPlayer().audio().outputDevices()) {
                menu.add(new AbstractAction(audioDevice.getLongName()) {
                    public void actionPerformed(ActionEvent actionEvent) {
                        // FIXME unsure about null here? and what is output/setoutput for? vlc app doesn't have it in its menus i think
                        mediaPlayerComponent.mediaPlayer().audio().setOutputDevice(null, audioDevice.getDeviceId());
                    }
                });
            }
        }
    }

}
