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
import java.util.List;

abstract class TrackMenu extends OnDemandMenu {

    private static final String KEY_TRACK_DESCRIPTION = "track-description"; // FIXME why?

    TrackMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent, String name, char mnemonic) {
        super(mediaPlayerComponent, name, mnemonic, true);
    }

    @Override
    protected final void onPrepareMenu(JMenu menu) {
        ButtonGroup buttonGroup = new ButtonGroup();
        if (mediaPlayerComponent.state() == OutOfProcessMediaPlayerComponent.State.STARTED) {
            int selectedTrack = onGetSelectedTrack();
            for (TrackDescription trackDescription : onGetTrackDescriptions()) {
                JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(createAction(trackDescription));
                menuItem.putClientProperty(KEY_TRACK_DESCRIPTION, trackDescription);
                buttonGroup.add(menuItem);
                menu.add(menuItem);
                if (selectedTrack == trackDescription.id()) {
                    menuItem.setSelected(true);
                }
            }
        }
        if (menu.getItemCount() == 0) {
            JMenuItem menuItem = new JMenuItem("No tracks");
            menuItem.setEnabled(false);
            menu.add(menuItem);
        }
    }

    protected abstract Action createAction(TrackDescription trackDescription);

    protected abstract List<TrackDescription> onGetTrackDescriptions();

    protected abstract int onGetSelectedTrack();

}
