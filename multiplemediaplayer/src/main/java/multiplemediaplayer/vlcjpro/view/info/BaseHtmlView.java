package multiplemediaplayer.vlcjpro.view.info;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public abstract class BaseHtmlView extends JFrame {

    private final JtwigTemplate template;

    private final JEditorPane editorPane;

    public BaseHtmlView(String title, String templateName) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        template = JtwigTemplate.classpathTemplate(String.format("templates/%s.twig", templateName));

        editorPane = new JEditorPane() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                super.paint(g);
            }
        };
        editorPane.setBorder(null);
        editorPane.setEditable(false);

        HTMLEditorKit editorKit = new HTMLEditorKit();
        editorPane.setEditorKit(editorKit);

        Document document = editorKit.createDefaultDocument();
        editorPane.setDocument(document);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    protected final void setModel(JtwigModel model) {
        editorPane.setText(template.render(model));
    }

}
