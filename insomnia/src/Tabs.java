import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * the class for tabs in the middle panel that extends the tab class.
 */
public class Tabs extends JTabbedPane {
    private JPanel header;
    private JPanel formData;
    private static ArrayList<JPanel> container;
    private JPanel firstPanel;
    private JTextArea key;
    private JTextArea value;
    private JButton delete;
    private JCheckBox include;
    private static ArrayList<JPanel> containerForm;
    private JPanel firstPanelForm;
    private JTextArea keyForm;
    private JTextArea valueForm;
    private JButton deleteForm;
    private JCheckBox includeForm;

    /**
     * constructor.
     */
    public Tabs() {
        header = new JPanel(new GridLayout(20, 1, 4, 4));
        formData = new JPanel(new GridLayout(20, 1, 4, 4));
        container = new ArrayList<>();
        containerForm = new ArrayList<>();
        add("form Data", formData);
        add("header", header);
        firstPanel = new JPanel(new GridLayout(1, 4, 3, 3));
        key = new JTextArea("key");
        keyForm = new JTextArea("key");
        value = new JTextArea("value");
        valueForm = new JTextArea("value");
        delete = new JButton("del");
        deleteForm = new JButton("del");
        include = new JCheckBox();
        includeForm = new JCheckBox();
        firstPanel.add(key);
        firstPanel.add(value);
        firstPanel.add(delete);
        firstPanel.add(include);
        firstPanelForm = new JPanel(new GridLayout(1, 4, 3, 3));
        firstPanelForm.add(keyForm);
        firstPanelForm.add(valueForm);
        firstPanelForm.add(deleteForm);
        firstPanelForm.add(includeForm);
        header.add(firstPanel);
        formData.add(firstPanelForm);
        container.add(firstPanel);
        containerForm.add(firstPanelForm);
        key.addMouseListener(new MouseOption());
        value.addMouseListener(new MouseOption());
        keyForm.addMouseListener(new MouseOption());
        valueForm.addMouseListener(new MouseOption());
        delete.addMouseListener(new Delete());
        deleteForm.addMouseListener(new DeleteForm());
        include.addMouseListener(new Disable());
        includeForm.addMouseListener(new Disable());
    }

    /**
     * the class for extending the key & value bars.
     */
    private class MouseOption implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (container.indexOf(e.getComponent().getParent()) == container.size() - 1) {
                JPanel panel = new JPanel(new GridLayout(1, 4, 3, 3));
                JTextArea key = new JTextArea("key");
                JTextArea value = new JTextArea("value");
                JButton delete = new JButton("del");
                JCheckBox include = new JCheckBox();
                panel.add(key);
                panel.add(value);
                panel.add(delete);
                panel.add(include);
                header.add(panel);
                header.repaint();
                header.revalidate();
                key.addMouseListener(new MouseOption());
                value.addMouseListener(new MouseOption());
                delete.addMouseListener(new Delete());
                include.addMouseListener(new Disable());
                container.add(panel);
            } else if (containerForm.indexOf(e.getComponent().getParent()) == containerForm.size() - 1) {
                JPanel panel = new JPanel(new GridLayout(1, 4, 3, 3));
                JTextArea key = new JTextArea("key");
                JTextArea value = new JTextArea("value");
                JButton delete = new JButton("del");
                JCheckBox include = new JCheckBox();
                panel.add(key);
                panel.add(value);
                panel.add(delete);
                panel.add(include);
                formData.add(panel);
                formData.repaint();
                formData.revalidate();
                key.addMouseListener(new MouseOption());
                value.addMouseListener(new MouseOption());
                delete.addMouseListener(new DeleteForm());
                include.addMouseListener(new Disable());
                containerForm.add(panel);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * a class for deleting bars.
     */
    private class Delete implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int i = 0;
            for (JPanel jPanel : container) {
                if (e.getComponent().getParent().equals(jPanel)) {
                    break;
                }
                i++;
            }
            if (container.size() != 1) {
                container.remove(i);
                header.remove(i);
                header.revalidate();
                header.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class DeleteForm implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int i = 0;
            for (JPanel jPanel : containerForm) {
                if (e.getComponent().getParent().equals(jPanel)) {
                    break;
                }
                i++;
            }
            if (containerForm.size() != 1) {
                containerForm.remove(i);
                formData.remove(i);
                formData.revalidate();
                formData.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * a class for showing that the text area is disabled.
     */
    private class Disable implements MouseListener {
        private int flag = 0;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (flag % 2 == 0) {
                e.getComponent().getParent().getComponent(0).setBackground(Color.lightGray);
                e.getComponent().getParent().getComponent(1).setBackground(Color.lightGray);
                JTextArea textArea = (JTextArea) e.getComponent().getParent().getComponent(1);
                JTextArea textArea2 = (JTextArea) e.getComponent().getParent().getComponent(0);
                if (container.contains(e.getComponent().getParent())) {
                    container.remove(e.getComponent().getParent());
                } else {
                    containerForm.remove(e.getComponent().getParent());
                }
                textArea.setEditable(false);
                textArea2.setEditable(false);
            } else {
                e.getComponent().getParent().getComponent(0).setBackground(Color.WHITE);
                e.getComponent().getParent().getComponent(1).setBackground(Color.WHITE);
                JTextArea textArea = (JTextArea) e.getComponent().getParent().getComponent(1);
                JTextArea textArea2 = (JTextArea) e.getComponent().getParent().getComponent(0);
                if (container.contains(e.getComponent().getParent())) {
                    container.add((JPanel) e.getComponent().getParent());
                } else {
                    containerForm.add((JPanel) e.getComponent().getParent());
                }
                textArea.setEditable(true);
                textArea2.setEditable(true);

            }
            flag++;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static ArrayList<JPanel> getContainer() {
        return container;
    }

    public static ArrayList<JPanel> getContainerForm() {
        return containerForm;
    }
}
