/*
 * vlcj-pro-demo
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import uk.co.caprica.vlcjpro.common.MediaPlayerProcessExitCode;

/**
 * Base implementation common to all panels.
 */
abstract class BasePane extends JPanel {

    BasePane() {
        setBackground(Color.BLACK);
    }

	public void componentStopped(OutOfProcessMediaPlayerComponent component, MediaPlayerProcessExitCode exitCode) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
