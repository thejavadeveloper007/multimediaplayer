/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import net.miginfocom.swing.MigLayout;

import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.MetaData;
import uk.co.caprica.vlcj.media.TextTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjprodemo.toolkit.border.RoundedLineBorder;
import uk.co.caprica.vlcjprodemo.toolkit.border.TopEdgeBorder;
import uk.co.caprica.vlcjprodemo.toolkit.button.StandardToggleButton;
import multiplemediaplayer.vlcjpro.view.info.MetaView;
import multiplemediaplayer.vlcjpro.view.info.StatsView;
import multiplemediaplayer.vlcjpro.view.info.TrackInfoView;
import multiplemediaplayer.vlcjpro.view.snapshot.SnapshotView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

// FIXME need some reminder here that we're doing work on UI thread which ideally we wouldn't

/**
 * User interface component to hold an application video component.
 * <p>
 * This is the composite view component that includes the out-of-process media player component
 * controls, the media player controls and the view that contains the video surface.
 * <p>
 * This component is more of a controller in MVC parlance, in that it is linking up components and
 * responding to events.
 */
public final class VideoComponentView extends BasePane implements ActionListener, ChangeListener {

    private static final long SKIP_DELTA = 20 * 1000;

    private final OutOfProcessMediaPlayerComponent mediaPlayerComponent;

    private final ComponentControlsPane componentControlsPane;

    private final VideoPane videoPane;

    private final PlayerControlsPane playerControlsPane;

    private File chooserDirectory;

    private Rectangle metaBounds;

    private Rectangle trackInfoBounds;

    private Rectangle statsBounds;
    
    String url;
    public VideoComponentView(final OutOfProcessMediaPlayerComponent mediaPlayerComponent, String url) {
        this.mediaPlayerComponent = mediaPlayerComponent;
        this.url = url;
        componentControlsPane = new ComponentControlsPane(mediaPlayerComponent);
        videoPane = new VideoPane(mediaPlayerComponent);
        playerControlsPane = new PlayerControlsPane(mediaPlayerComponent, url);

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setBorder(new RoundedLineBorder(Color.WHITE, 4, 4, 2));

        add(videoPane, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.black);
//        controlsPanel.setBorder(new TopEdgeBorder(new Color(255, 255, 255, 128), 4));
        controlsPanel.setLayout(new MigLayout("fill", "[]push[]", "[]"));
//        controlsPanel.add(componentControlsPane);
        controlsPanel.add(playerControlsPane);

        add(controlsPanel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();

        JMenu playbackMenu = new JMenu("Playback");
        playbackMenu.setMnemonic('l');
        playbackMenu.add(new TitleMenu(mediaPlayerComponent).menu());
        playbackMenu.add(new ChapterMenu(mediaPlayerComponent).menu());
        playbackMenu.add(new JSeparator());
        playbackMenu.add(new SpeedMenu(mediaPlayerComponent));
        playbackMenu.add(new JSeparator());
        playbackMenu.add(new AbstractAction("Jump Forward") {
            {
                putValue(MNEMONIC_KEY, (int) 'j');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(10 * 1000);
            }
        });
        playbackMenu.add(new AbstractAction("Jump Backward") {
            {
                putValue(MNEMONIC_KEY, (int) 'k');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().skipTime(-10 * 1000);
            }
        });
        playbackMenu.add(new JSeparator());
        playbackMenu.add(new AbstractAction("Previous title") {
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        playbackMenu.add(new AbstractAction("Next title") {
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        playbackMenu.add(new AbstractAction("Previous chapter") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().chapters().previous();
            }
        });
        playbackMenu.add(new AbstractAction("Next chapter") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().chapters().next();
            }
        });
        playbackMenu.add(new JSeparator());
        playbackMenu.add(new AbstractAction("Play") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().play();
            }
        });
        playbackMenu.add(new AbstractAction("Stop") {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().controls().stop();
            }
        });
        menuBar.add(playbackMenu);

        JMenu audioMenu = new JMenu("Audio");
        audioMenu.setMnemonic('a');
        audioMenu.add(new AudioTrackMenu(mediaPlayerComponent).menu());
        audioMenu.add(new AudioDeviceMenu(mediaPlayerComponent).menu());
        audioMenu.add(new StereoModeMenu(mediaPlayerComponent));
        menuBar.add(audioMenu);

        JMenu videoMenu = new JMenu("Video");
        videoMenu.setMnemonic('v');
        videoMenu.add(new VideoTrackMenu(mediaPlayerComponent).menu());
        videoMenu.add(new JSeparator());
        videoMenu.add(new ZoomMenu(mediaPlayerComponent));
        videoMenu.add(new AspectRatioMenu(mediaPlayerComponent));
        videoMenu.add(new CropGeometryMenu(mediaPlayerComponent));
        menuBar.add(videoMenu);

        JMenu subtitleMenu = new JMenu("Subtitle");
        subtitleMenu.setMnemonic('t');
        subtitleMenu.add(new AbstractAction("Add Subtitle File...") {
            {
                putValue(MNEMONIC_KEY, (int) 's');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                if (chooserDirectory != null) {
                    chooser.setCurrentDirectory(chooserDirectory);
                }
                chooser.setDialogTitle("Select subtitle file...");
                if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(VideoComponentView.this)) {
                    mediaPlayerComponent.mediaPlayer().subpictures().setSubTitleFile(chooser.getSelectedFile());
                }
                chooserDirectory = chooser.getCurrentDirectory();
            }
        });
        subtitleMenu.add(new SubtitleTrackMenu(mediaPlayerComponent).menu());
        menuBar.add(subtitleMenu);

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic('s');
        toolsMenu.add(new AbstractAction("Media Information") {
            {
                putValue(MNEMONIC_KEY, (int) 'i');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                MetaData metaData = mediaPlayerComponent.mediaPlayer().media().meta().asMetaData();
                final MetaView metaView = new MetaView(metaData);
                metaView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        metaBounds = metaView.getBounds();
                        metaBounds.setLocation(metaView.getLocationOnScreen());
                    }
                });
                if (metaBounds != null) {
                    metaView.setLocation(metaBounds.getLocation());
                    metaView.setSize(metaBounds.getSize());
                } else {
                    metaView.setSize(400, 700);
                }
                metaView.setVisible(true);
            }
        });
        toolsMenu.add(new AbstractAction("Codec Information") {
            {
                putValue(MNEMONIC_KEY, (int) 'c');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                List<AudioTrackInfo> audioTracks = mediaPlayerComponent.mediaPlayer().media().info().audioTracks();
                List<VideoTrackInfo> videoTracks = mediaPlayerComponent.mediaPlayer().media().info().videoTracks();
                List<TextTrackInfo> textTracks = mediaPlayerComponent.mediaPlayer().media().info().textTracks();
                final TrackInfoView trackInfoView = new TrackInfoView(audioTracks, videoTracks, textTracks);
                trackInfoView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        trackInfoBounds = trackInfoView.getBounds();
                        trackInfoBounds.setLocation(trackInfoView.getLocationOnScreen());
                    }
                });
                if (trackInfoBounds != null) {
                    trackInfoView.setLocation(trackInfoBounds.getLocation());
                    trackInfoView.setSize(trackInfoBounds.getSize());
                } else {
                    trackInfoView.setSize(1000, 400);
                }
                trackInfoView.setVisible(true);
            }
        });
        toolsMenu.add(new AbstractAction("Media Statistics") {
            {
                putValue(MNEMONIC_KEY, (int) 't');
            }
            public void actionPerformed(ActionEvent actionEvent) {
                final StatsView statsView = new StatsView(mediaPlayerComponent.mediaPlayer());
                statsView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        statsBounds = statsView.getBounds();
                        statsBounds.setLocation(statsView.getLocationOnScreen());
                    }
                });
                if (statsBounds != null) {
                    statsView.setLocation(statsBounds.getLocation());
                    statsView.setSize(statsBounds.getSize());
                } else {
                    statsView.setSize(300, 420);
                }
                statsView.setVisible(true);
            }
        });
        menuBar.add(toolsMenu);

//        add(menuBar, BorderLayout.NORTH);

        playerControlsPane.addActionListener(this);
        playerControlsPane.addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "play":
            case "pause":
                mediaPlayerComponent.mediaPlayer().controls().pause();
                break;
            case "stop":
                mediaPlayerComponent.mediaPlayer().controls().stop();
                break;
            case "rewind":
                mediaPlayerComponent.mediaPlayer().controls().skipTime(-SKIP_DELTA);
                break;
            case "skip":
                mediaPlayerComponent.mediaPlayer().controls().skipTime(SKIP_DELTA);
                break;
            case "snapshot":
                // Get a snapshot from the remote process - this will serialise a buffered image
                // from the remote process
                final BufferedImage image = mediaPlayerComponent.mediaPlayer().snapshots().get();
                if (image != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            onSnapshot(image);
                        }
                    });
                }
                break;
            case "mute-on":
            case "mute-off":
                boolean muted = mediaPlayerComponent.mediaPlayer().audio().mute();
                StandardToggleButton source = (StandardToggleButton) e.getSource();
                source.toggle(!muted);
                break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        // Don't fire too many remote invocations
        if (!slider.getValueIsAdjusting()) {
            int volume = slider.getValue();
            mediaPlayerComponent.mediaPlayer().audio().setVolume(volume);
        }
    }

    private void onSnapshot(BufferedImage image) {
        new SnapshotView(image);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
        videoPane.addVideoSurfaceMouseListener(l);
    }
}
