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

import uk.co.caprica.vlcj.player.base.TitleDescription;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;
import multiplemediaplayer.vlcjpro.time.Time;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class TitleMenu extends OnDemandMenu {

    TitleMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Title", 'i', true);
    }

    @Override
    protected void onPrepareMenu(JMenu menu) {
        int i = 0;
        // FIXME use selected
        int selected = mediaPlayerComponent.mediaPlayer().titles().title();
        for (TitleDescription title : mediaPlayerComponent.mediaPlayer().titles().titleDescriptions()) {
            TitleAction action = new TitleAction(i++, title);
            menu.add(action);
        }
    }

    private class TitleAction extends AbstractAction {

        private final int number;
        private final TitleDescription title;

        private TitleAction(int number, TitleDescription title) {
            this.number = number;
            this.title = title;
            if (!title.isMenu()) {
                putValue(NAME, String.format("%s [%s]", getName(), getDuration()));
            } else {
                putValue(NAME, getName());
            }
        }

        public void actionPerformed(ActionEvent actionEvent) {
            mediaPlayerComponent.mediaPlayer().titles().setTitle(number);
        }

        private String getName() {
            return title.name() != null ? title.name() : String.format("Title %d", number);
        }

        private String getDuration() {
            return Time.formatTime(title.duration());
        }

    }

}
