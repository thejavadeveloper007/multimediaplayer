/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcj.player.base.TrackDescription;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

final class AudioTrackMenu extends TrackMenu {

    AudioTrackMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Audio Track", 'a');
    }

    @Override
    protected Action createAction(final TrackDescription trackDescription) {
        return new AbstractAction(trackDescription.description()) {
            public void actionPerformed(ActionEvent actionEvent) {
                mediaPlayerComponent.mediaPlayer().audio().setTrack(trackDescription.id());
            }
        };
    }

    @Override
    protected List<TrackDescription> onGetTrackDescriptions() {
        return mediaPlayerComponent.mediaPlayer().audio().trackDescriptions();
    }

    @Override
    protected int onGetSelectedTrack() {
        return mediaPlayerComponent.mediaPlayer().audio().track();
    }
}
