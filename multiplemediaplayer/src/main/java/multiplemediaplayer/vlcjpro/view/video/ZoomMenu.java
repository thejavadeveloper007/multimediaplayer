package multiplemediaplayer.vlcjpro.view.video;

import uk.co.caprica.vlcjpro.client.player.component.OutOfProcessMediaPlayerComponent;

public class ZoomMenu extends ValuesMenu {

    private static final String[][] VALUES = {
        {"1:4 Quarter" , "0.25"},
        {"1:2 Half"    , "0.5" },
        {"1:1 Original", "1"   },
        {"2:1 Double"  , "2"   }
    };

    public ZoomMenu(OutOfProcessMediaPlayerComponent mediaPlayerComponent) {
        super(mediaPlayerComponent, "Zoom", 'z', VALUES);
    }

    @Override
    protected void onSelected(String text, String value) {
        mediaPlayerComponent.mediaPlayer().video().setScale(Float.parseFloat(value));
    }

}
