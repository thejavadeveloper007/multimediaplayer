/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import uk.co.caprica.vlcj.player.base.LibVlcConst;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjpro.client.player.events.OutOfProcessMediaPlayerEventAdapter;
import uk.co.caprica.vlcjpro.client.player.oop.OutOfProcessMediaPlayer;
import uk.co.caprica.vlcjprodemo.toolkit.button.StandardButton;
import uk.co.caprica.vlcjprodemo.toolkit.button.StandardToggleButton;
import uk.co.caprica.vlcjprodemo.toolkit.slider.StandardSlider;

/**
 * A user interface component that manages the media player controls.
 */
final class PlayerControlsPane extends BasePane {

    private final StandardToggleButton playPauseButton;

    private final StandardButton stopButton;

    private final StandardButton rewindButton;

    private final StandardButton skipButton;

    private final StandardSlider volumeSlider;

    private final StandardToggleButton muteButton;

    private final StandardButton snapshotButton;

    private final OutOfProcessMediaPlayerComponent mediaPlayerComponent;
    
    private final String url;
    private final String[] options = { "--network-caching","400" };

    PlayerControlsPane(OutOfProcessMediaPlayerComponent mediaPlayerComponent, String url) {
        this.mediaPlayerComponent = mediaPlayerComponent;
        this.url = url;
        
        playPauseButton = new StandardToggleButton("play", "pause");
        stopButton = new StandardButton("stop");

        rewindButton = new StandardButton("rewind");
        skipButton = new StandardButton("skip");

        volumeSlider = new StandardSlider();
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);

        muteButton = new StandardToggleButton("mute-on", "mute-off");

        snapshotButton = new StandardButton("snapshot");

//        setBorder(new TopEdgeBorder(new Color(255, 255, 255, 128), 4));

//        setLayout(new MigLayout("", "[][][]rel[]8[100]rel[]2*rel[]", "[]"));
        setLayout(new GridLayout(1, 9, 0, 0));

        add(playPauseButton);
        add(stopButton);
        add(rewindButton);
        add(skipButton);
        add(volumeSlider);
        add(muteButton);
        add(snapshotButton);

        showSeekControls(false);

        // Add a listener to be notified of media player events - these events come indirectly from
        // the remote out-of-process media player. Note that user interface state must be updated
        // on the Swing Event Dispatch Thread.
        mediaPlayerComponent.addMediaPlayerEventListener(new OutOfProcessMediaPlayerEventAdapter() {

            @Override
            public void playing(OutOfProcessMediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        onPlaying();
                    }
                });
            }

            @Override
            public void paused(OutOfProcessMediaPlayer mediaPlayer) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        onPaused();
                    }
                });
            }

            @Override
            public void seekableChanged(OutOfProcessMediaPlayer mediaPlayer, final int newSeekable) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        showSeekControls(newSeekable != 0);
                    }
                });
            }
        });
    }

    void addActionListener(ActionListener listener) {
        playPauseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.mediaPlayer().media().play(url, options);
				
			}
		});
        stopButton.addActionListener(listener);
        rewindButton.addActionListener(listener);
        skipButton.addActionListener(listener);
        muteButton.addActionListener(listener);
        snapshotButton.addActionListener(listener);
    }

    void addChangeListener(ChangeListener listener) {
        volumeSlider.addChangeListener(listener);
    }

    private void onPlaying() {
        playPauseButton.toggle(false);
    }

    private void onPaused() {
        playPauseButton.toggle(true);
    }

    private void showSeekControls(boolean show) {
//        rewindButton.setEnabled(show);
//        skipButton.setEnabled(show);
    }
}
