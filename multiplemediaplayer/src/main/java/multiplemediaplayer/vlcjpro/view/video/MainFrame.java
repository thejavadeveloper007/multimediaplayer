/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponentFactory;
import uk.co.caprica.vlcjpro.client.port.NoMorePortsException;
import uk.co.caprica.vlcjprodemo.toolkit.layout.ZoomLayoutManager;

/**
 * Main application frame.
 */
public class MainFrame {

    /**
     * The vlcj out-of-process media player component factory instance.
     * <p>
     * This instance should be held for the life-time of the application, and explicitly released
     * during application termination.
     * <p>
     * It may be useful to use a standard shutdown hook to ensure this factory is released when the
     * application exits.
     */
    private final OutOfProcessMediaPlayerComponentFactory componentFactory = new OutOfProcessMediaPlayerComponentFactory();

    private final ZoomLayoutManager zoomLayout;
    
    public MainFrame(int rows, int columns) {
        zoomLayout = new ZoomLayoutManager(rows, columns);

        final JFrame f = new JFrame();
        f.setTitle("vlcj-pro");
        try {
            f.setIconImage(ImageIO.read(new File("vlcj-logo-small.png")));
        }
        catch (Exception e) {
        }
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Explicitly release the out-of-process component factory - it may be a good idea
                // to push this into a standard shutdown hook
                componentFactory.release();
                System.exit(0);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(1, 12, 1, 12));
        contentPane.setLayout(zoomLayout);
        
        String urls[] = {"rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4",
        		"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8",
        		"https://cdn.flowplayer.com/a30bd6bc-f98b-47bc-abf5-97633d4faea0/hls/de3f6ca7-2db3-4689-8160-0f574a5996ad/playlist.m3u8"};

        for (int i = 0; i < rows * columns; i++) {
            // Use the factory to create a new out-of-process media player component instance - the
            // factory itself keeps a reference to the component instance and automatically cleanly
            // releases it when the factory is released
            try {
                // seems like it ignore --vout and also it ignore the avcodec-hw=none setting here
//                String libVlcArgs = "-DvlcjProLibVLC=--video-filter=rotate --rotate-angle=235";
//                String libVlcArgs = "-DvlcjProLibVLC=--vout=xcb_xv --video-filter=transform --transform-type=90";
            	String url = urls[i];
                OutOfProcessMediaPlayerComponent component = componentFactory.newOutOfProcessMediaPlayerComponent("-noverify");

                VideoComponentView componentView = new VideoComponentView(component, url);
                componentView.addMouseListener(new ZoomListener(contentPane, zoomLayout, i));

                contentPane.add(componentView, Integer.toString(i));
            }
            catch (NoMorePortsException e) {
                e.printStackTrace();
            }
        }

        InputMap im = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "blur");

        ActionMap am = contentPane.getActionMap();
        am.put("blur", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                zoomLayout.blur();
                f.getContentPane().revalidate();
            }
        });


        f.setContentPane(contentPane);

        f.setSize(1440, 1040);
        f.setLocation(50, 100);
        f.setVisible(true);
    }

    private class ZoomListener extends MouseAdapter {

        private final JPanel panel;

        private final ZoomLayoutManager layout;

        private final int id;

        private ZoomListener(JPanel panel, ZoomLayoutManager layout, int id) {
            this.panel = panel;
            this.layout = layout;
            this.id = id;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            layout.focus(id);
            panel.revalidate();
        }
    }
}
