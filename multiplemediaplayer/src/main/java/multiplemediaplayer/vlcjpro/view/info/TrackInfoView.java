package multiplemediaplayer.vlcjpro.view.info;

import org.jtwig.JtwigModel;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.TextTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;

import java.util.List;

public class TrackInfoView extends BaseHtmlView {

    public TrackInfoView(List<AudioTrackInfo> audioTracks, List<VideoTrackInfo> videoTracks, List<TextTrackInfo> textTracks) {
        super("Track Information", "track-information");
        setModel(createModel(audioTracks, videoTracks, textTracks));
    }

    private JtwigModel createModel(List<AudioTrackInfo> audioTracks, List<VideoTrackInfo> videoTracks, List<TextTrackInfo> textTracks) {
        return JtwigModel.newModel()
            .with("audio", audioTracks)
            .with("video", videoTracks)
            .with("text" , textTracks )
        ;
    }

}
