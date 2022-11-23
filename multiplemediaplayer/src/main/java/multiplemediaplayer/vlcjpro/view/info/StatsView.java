package multiplemediaplayer.vlcjpro.view.info;

import org.jtwig.JtwigModel;
import uk.co.caprica.vlcj.media.MediaStatistics;
import uk.co.caprica.vlcjpro.client.player.oop.OutOfProcessMediaPlayer;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatsView extends BaseHtmlView {

    private static final int UPDATE_PERIOD = 2;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public StatsView(final OutOfProcessMediaPlayer mediaPlayer) {
        super("Media Statistics", "media-statistics");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                executor.shutdown();
            }
        });
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    final MediaStatistics stats = mediaPlayer.media().info().statistics();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            updateStats(stats);
                        }
                    });
                }
                catch (Exception e) {
                }
            }
        }, 0, UPDATE_PERIOD, TimeUnit.SECONDS);
    }

    private void updateStats(MediaStatistics stats) {
        setModel(createModel(stats));
    }

    private JtwigModel createModel(MediaStatistics stats) {
        return JtwigModel.newModel().with("stats", stats);
    }

}
