/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import uk.co.caprica.vlcj.player.base.Logo;
import uk.co.caprica.vlcj.player.base.LogoPosition;
import uk.co.caprica.vlcj.player.base.Marquee;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponentListener;
import uk.co.caprica.vlcjpro.common.MediaPlayerProcessExitCode;
import uk.co.caprica.vlcjprodemo.toolkit.button.StandardButton;
import uk.co.caprica.vlcjprodemo.toolkit.label.StandardLabel;

/**
 * User interface component to manage the out-of-process media player component controls.
 * <p>
 * This user interface component listens to the out-of-process media player component life-cycle
 * events and updates a connected/not-connected display accordingly.
 * <p>
 * Ordinarily the remote media player process will be created on first access to the component
 * media player instance. A video camera button is provided here to explicitly request that media
 * player instance and create the new new media player process.
 */
final class ComponentControlsPane extends BasePane implements OutOfProcessMediaPlayerComponentListener {

    private static final String NOT_CONNECTED_TEXT = "Disconnected";

    private static final String CONNECTED_TEXT = "Connected";

//    private static final Color NOT_CONNECTED_COLOUR = Color.RED;
    private static final Color NOT_CONNECTED_COLOUR = new Color(116, 48, 48);

//    private static final Color CONNECTED_COLOUR = new Color(96, 232, 96);
    private static final Color CONNECTED_COLOUR = new Color(48, 116, 48);

    private final StandardButton videoCameraButton;

    private final StandardLabel statusLabel;

    private final OutOfProcessMediaPlayerComponent mediaPlayerComponent;

    ComponentControlsPane(final OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        this.mediaPlayerComponent = mediaPlayerComponent;

        videoCameraButton = new StandardButton("video-camera");
        statusLabel = new StandardLabel();
        setConnected(false);

//        setBorder(new BottomEdgeBorder(new Color(255, 255, 255, 128), 4));

        setLayout(new MigLayout("", "[]8[]", "[]"));
        add(videoCameraButton);
        add(statusLabel);

        // Add a listener for out-of-prcess media player component life-cycle events - the events
        // will not come in on the Swing Event Dispatch Thread, so when updating user interface
        // state in response to those events, they must be pushed to the EDT via invokeLater (as is
        // normal in Swing applications)
        mediaPlayerComponent.addOutOfProcessMediaPlayerComponentListener(this);

        videoCameraButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Just ask for a media player, this will try and get a new one if there isn't one
                // and started/stopped notifications will be fired depending on success/failure
                mediaPlayerComponent.mediaPlayer();
            }
        });
    }

    private void setConnected(boolean connected) {
        statusLabel.setText(connected ? String.format("OK (%d)", mediaPlayerComponent.serverPid()) : NOT_CONNECTED_TEXT);
        statusLabel.setBackground(connected ? CONNECTED_COLOUR : NOT_CONNECTED_COLOUR);
    }

    public void componentStarted(OutOfProcessMediaPlayerComponent component) {
//        Logo logo = Logo.logo().file("vlcj-logo.png").position(LogoPosition.TOP_LEFT).opacity(0.3f).enable();
//        mediaPlayerComponent.mediaPlayer().logo().set(logo);

        Marquee marquee = Marquee.marquee()
            .colour(Color.white)
            .size(20)
            .opacity(0.25f)
            .position(MarqueePosition.TOP_RIGHT)
            .location(8, 8)
            .text(String.format("PID %d, port %d", component.serverPid(), component.serverPort()))
            .enable();

        mediaPlayerComponent.mediaPlayer().marquee().set(marquee);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setConnected(true);
            }
        });
    }

    @Override
    public void componentStopped(OutOfProcessMediaPlayerComponent component, MediaPlayerProcessExitCode exitCode) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setConnected(false);
                // FIXME may want to mark this component as broken and replace it
            }
        });
    }
}
