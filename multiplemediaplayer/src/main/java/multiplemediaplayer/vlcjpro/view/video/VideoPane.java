/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseListener;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponentAdapter;
import uk.co.caprica.vlcjpro.client.player.events.OutOfProcessMediaPlayerEventAdapter;
import uk.co.caprica.vlcjpro.client.player.oop.OutOfProcessMediaPlayer;
import uk.co.caprica.vlcjpro.client.player.videosurface.ComponentOutOfProcessVideoSurface;
import uk.co.caprica.vlcjpro.common.MediaPlayerProcessExitCode;
import uk.co.caprica.vlcjprodemo.toolkit.transfer.MediaTransferHandler;

/**
 * Video view.
 * <p>
 * This component contains the video surface, and another view shown when there is no video.
 */
final class VideoPane extends BasePane {

    private static final String DEFAULT_VIEW = "default";

    private static final String VIDEO_VIEW = "video";

    private final OutOfProcessMediaPlayerComponent mediaPlayerComponent;

    private final Canvas videoSurface;

    private final CardLayout cardLayout;

    VideoPane(final OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        this.mediaPlayerComponent = mediaPlayerComponent;
        this.videoSurface = createVideoSurface();

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(new NoVideoView(), DEFAULT_VIEW);
        add(videoSurface, VIDEO_VIEW);

        // Associate the video surface with the out-of-process media player component - note that
        // this will automatically survive any crash/restart of the remote process so it only needs
        // to be set once here
        mediaPlayerComponent.setVideoSurface(new ComponentOutOfProcessVideoSurface(videoSurface)); // FICME revisit this comment, This just sets the component, it doesn't grab the id at this point because the UI is not validated yet

        // Add a listener to be notified of component life-cycle events - this is used to hide the
        // video view and show the default view instead when a component stopped event is received
        mediaPlayerComponent.addOutOfProcessMediaPlayerComponentListener(new OutOfProcessMediaPlayerComponentAdapter() {

            @Override
            public void componentStopped(OutOfProcessMediaPlayerComponent component, MediaPlayerProcessExitCode exitCode) {
                showDefaultView();
            }
        });

        // Add a listener to be notified of media player events, indirectly, from the out-of-process
        // media player - this listener is only used to determine whether or not the video view
        // should be displayed
        mediaPlayerComponent.addMediaPlayerEventListener(new OutOfProcessMediaPlayerEventAdapter() {

            @Override
            public void playing(OutOfProcessMediaPlayer mediaPlayer) {
                showVideoView();
            }

            @Override
            public void stopped(OutOfProcessMediaPlayer mediaPlayer) {
                showDefaultView();
            }

            @Override
            public void finished(OutOfProcessMediaPlayer mediaPlayer) {
                showDefaultView();
            }

            @Override
            public void error(OutOfProcessMediaPlayer mediaPlayer) {
                showDefaultView();
            }
        });

        setTransferHandler(new MediaTransferHandler() {
            @Override
            protected void onMediaDropped(String[] uris) {
                OutOfProcessMediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();
                if (mediaPlayer != null) {
                    // Specifying this HW decoding setting here was the only way I could get rotated video working - it
                    // still needs video filter settings on the factory, those cannot be specified here
//                    mediaPlayer.media().play(uris[0], ":avcodec-hw=none");
                    mediaPlayer.media().play(uris[0]);
                } else {
                    System.out.println("Failed to create media player, probably out of ports");
                }
            }
        });
    }

    private Canvas createVideoSurface() {
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        return canvas;
    }

    private void showDefaultView() {
        cardLayout.show(this, DEFAULT_VIEW);
    }

    private void showVideoView() {
        cardLayout.show(this, VIDEO_VIEW);
    }

    public void addVideoSurfaceMouseListener(MouseListener l) {
        videoSurface.addMouseListener(l);
    }
}
