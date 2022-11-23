package multiplemediaplayer.vlcjpro.view.info;

import org.jtwig.JtwigModel;
import uk.co.caprica.vlcj.media.Meta;
import uk.co.caprica.vlcj.media.MetaData;

import java.util.Map;

public class MetaView extends BaseHtmlView {

    public MetaView(MetaData metaData) {
        super("Media Information", "media-information");
        setModel(createModel(metaData));
    }

    private JtwigModel createModel(MetaData metaData) {
        JtwigModel model = JtwigModel.newModel();
        if (metaData != null) {
            for (Map.Entry<Meta, String> entry : metaData.values().entrySet()) {
                model.with(entry.getKey().name(), entry.getValue());
            }
        }
        return model;
    }

}
