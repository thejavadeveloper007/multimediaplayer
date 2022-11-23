/*
 * vlcj-pro-demo

 *
 * Copyright 2015 Caprica Software Limited.
 */

package multiplemediaplayer.vlcjpro.main;

import javax.swing.SwingUtilities;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import multiplemediaplayer.vlcjpro.view.video.MainFrame;

/**
 * Main application class for the demo application.
 * <p>
 * e.g.<pre>java -Dlogback.configurationFile=logback-client.groovy -jar vlcj-pro-demo-1.0.0-SNAPSHOT.jar 2 4</pre>
 */
public class VlcjProDemo {

    static {
        try {
            UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
        }
        catch (Exception e) {
        }
    }

    /**
     * Application entry-point.
     * <p>
     * Interpret the command line arguments:
     * <ul>
     *  <li>no arguments supplied  -> 1 row X 2 columns</li>
     *  <li>one argument supplied  -> 1 row X n columns</li>
     *  <li>two arguments supplied -> n rows x m columns</li>
     * </ul>
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        final int rows;
        final int columns;
//        switch (args.length) {
//            case 0:
//                rows = 2;
//                columns = 2;
//                break;
//            case 1:
//                rows = 1;
//                columns = Integer.parseInt(args[0]);
//                break;
//            case 2:
//            default:
//                rows = Integer.parseInt(args[0]);
//                columns = Integer.parseInt(args[1]);
//                break;
//        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(4, 4);
            }
        });
    }
}
