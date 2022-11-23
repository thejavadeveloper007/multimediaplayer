package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

public class CropGeometryMenu extends ValuesMenu {

    private static final String[][] VALUES = {
        {"Default", null     },
        {"16:10"  , "16:10"  },
        {"16:9"   , "16:9"   },
        {"4:3"    , "4:3"    },
        {"1.85:1" , "185:100"},
        {"2.21:1" , "221:100"},
        {"2.35:1" , "235:100"},
        {"2.39:1" , "239:100"},
        {"5:3"    , "5:3"    },
        {"5:4"    , "5:4"    },
        {"1:1"    , "1:1"    }
    };

    public CropGeometryMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Crop Geometry", 'c', VALUES);
    }

    @Override
    protected void onSelected(String text, String value) {
        mediaPlayerComponent.mediaPlayer().video().setCropGeometry(value);
    }

}
