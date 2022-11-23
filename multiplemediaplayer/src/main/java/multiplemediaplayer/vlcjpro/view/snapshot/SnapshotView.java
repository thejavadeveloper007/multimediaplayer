/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.snapshot;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import uk.co.caprica.vlcjprodemo.toolkit.image.ImagePane;
import uk.co.caprica.vlcjprodemo.toolkit.image.ImagePane.Mode;

/**
 * Simple frame implementation that shows a buffered image.
 */
public class SnapshotView extends JFrame {

    private final BufferedImage image;

    public SnapshotView(BufferedImage image) {
        this.image = image;
        setTitle("vlcj-pro snapshot");
        setContentPane(new ImagePane(Mode.DEFAULT, image, 1.0f));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationByPlatform(true);
        setVisible(true);
    }
}
