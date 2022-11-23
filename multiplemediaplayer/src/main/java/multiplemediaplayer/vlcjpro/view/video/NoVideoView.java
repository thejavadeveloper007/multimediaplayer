/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.BorderLayout;

import uk.co.caprica.vlcjprodemo.toolkit.image.ImagePane;
import uk.co.caprica.vlcjprodemo.toolkit.image.ImagePane.Mode;

/**
 * A simple view to display when there is no active video.
 */
final class NoVideoView extends BasePane {

    NoVideoView() {
        setLayout(new BorderLayout());
        add(new ImagePane(Mode.CENTER, getClass().getResource("/vlcj-logo.png"), 1.0f), BorderLayout.CENTER);
    }
}
