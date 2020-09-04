import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * a class for tabs on the right panel.
 */
public class Panel3 extends JTabbedPane {
    private static JPanel header;
    private JPanel rawCode;
    private static JPanel visualPreview;
    private static JTextArea rawText;
    private JComboBox preview;
    private JPanel panel;
    private static JTextArea headers;

    /**
     * constructor
     */
    public Panel3() {
        headers = new JTextArea();
        headers.setEditable(false);
        rawCode = new JPanel(new BorderLayout());
        visualPreview = new JPanel(new BorderLayout());
        header = new JPanel(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        rawText = new JTextArea();
        rawText.setPreferredSize(new Dimension(400, 10000));
        rawText.setSize(rawCode.getWidth() - 10, rawCode.getHeight() - 10);
        rawCode.add(rawText, BorderLayout.CENTER);
        rawText.setEditable(false);
        preview = new JComboBox();
        preview.addItem("choose one!");
        preview.addItem("visual preview");
        preview.addItem("raw");
        panel = new JPanel(new BorderLayout(4, 4));
        add("preview", panel);
        panel.add(preview, BorderLayout.NORTH);
        preview.addActionListener(new Preview());
        add("header", header);

        JButton jButton = new JButton("copy to clipBoard");
        jButton.addActionListener(new Copy());
        header.add(headers, BorderLayout.CENTER);
        header.add(jButton, BorderLayout.SOUTH);
        jButton.setPreferredSize(new Dimension(100, 30));
        headers.setPreferredSize(new Dimension(400, 300));

    }

    /**
     * an Action listener for preview tab.
     */
    private class Preview implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            Object object = comboBox.getSelectedItem();
            if (object.equals("visual preview")) {
                panel.remove(rawCode);
                panel.add(visualPreview, BorderLayout.CENTER);
            } else if (object.equals("raw")) {
                panel.remove(visualPreview);
                panel.add(rawCode, BorderLayout.CENTER);
            } else if (object.equals("choose one!")) {
                panel.remove(visualPreview);
                panel.remove(rawCode);
            }

            panel.repaint();
            panel.revalidate();
        }
    }

    /**
     * copy to clipboard listener.
     */
    private static class Copy implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String string = "";
            for (Component component : header.getComponents()) {
                if (component instanceof JTextArea) {
                    JTextArea textArea = (JTextArea) component;
                    string = string + textArea.getText();
                }
            }
            string = string.substring(0, string.length() - 1);
            StringSelection selection = new StringSelection(string);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }

    public static void setRawText(String rawText1) {
        rawText.setText(rawText1);
    }


    public static void addInHeaders(String string) {
        headers.setText(string);
    }

    public static void preview(JEditorPane jEditorPane) {
        visualPreview.removeAll();
        visualPreview.add(jEditorPane);
    }
}
