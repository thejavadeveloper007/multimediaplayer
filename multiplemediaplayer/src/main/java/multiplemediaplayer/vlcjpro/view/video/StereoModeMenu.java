package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcj.player.base.AudioChannel;
import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

// FIXME this menu should show the selected

public class StereoModeMenu extends ValuesMenu {

    private static final String[][] VALUES = {
        {"Unset"         , "UNSET"     },
        {"Stereo"        , "STEREO"    },
        {"Left"          , "LEFT"      },
        {"Right"         , "RIGHT"     },
        {"Reverse Stereo", "RSTEREO"   },
        {"Dolby Stereo"  , "DOLBYS"    },
        {"Headphones"    , "HEADPHONES"},
        {"Mono"          , "MONO"      }
    };

    public StereoModeMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Stereo Mode", 's', VALUES);
    }

    @Override
    protected void onSelected(String text, String value) {
        mediaPlayerComponent.mediaPlayer().audio().setChannel(AudioChannel.valueOf(value).intValue());
    }

}
