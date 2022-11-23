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

import uk.co.caprica.vlcj.player.base.ChapterDescription;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

final class ChapterMenu extends OnDemandMenu {

    ChapterMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Chapter", 'c', true);
    }

    @Override
    protected void onPrepareMenu(JMenu menu) {
        int i = 0;
        // FIXME use selected
        int selected = mediaPlayerComponent.mediaPlayer().chapters().chapter();
        for (ChapterDescription chapter : mediaPlayerComponent.mediaPlayer().chapters().descriptions()) {
            ChapterAction action = new ChapterAction(i++, chapter);
            menu.add(action);
        }
    }

    private class ChapterAction extends AbstractAction {

        private final int number;
        private final ChapterDescription chapter;

        private ChapterAction(int number, ChapterDescription chapter) {
            this.number = number;
            this.chapter = chapter;
            putValue(NAME, getName());
        }

        public void actionPerformed(ActionEvent actionEvent) {
            mediaPlayerComponent.mediaPlayer().chapters().setChapter(number);
        }

        private String getName() {
            return chapter.name() != null ? chapter.name() : String.format("Chapter %d", number + 1);
        }

        private String getDuration() {
            return "1:20:30"; // FIXME
        }

    }

}
