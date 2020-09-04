import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * the main part of the GUI graphic and main element of the application.
 */
public class Gui {
    private static JFrame frame;
    private static JPanel panel1;
    private static JPanel panel;
    private static Send send;
    private static JPanel panel3;
    private static JScrollPane scrollBar;
    private static JScrollPane scrollPane;
    private static JScrollPane scrollPane2;
    private JPanel panel2;
    private Border border;
    private static JTextArea status;
    private static JTextArea time;
    private JPanel statusPanel;

    /**
     * the constructor that make the frame and its visibility.
     *
     * @param name
     * @throws ClassNotFoundException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */

    public Gui(String name) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        statusPanel = new JPanel(new GridLayout(1, 2, 4, 4));
        status = new JTextArea("status");
        time = new JTextArea("time");
        statusPanel.add(status);
        statusPanel.add(time);
        status.setEditable(false);
        time.setEditable(false);
        panel = new JPanel(new GridLayout(1, 3));
        panel1 = new JPanel();
        GridLayout gridLayout = new GridLayout(50, 1, 5, 5);
        gridLayout.preferredLayoutSize(panel1);
        BoxLayout boxLayout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        panel1.setLayout(boxLayout);
        panel2 = new JPanel(new BorderLayout(5, 5));
        panel2.add(new Tabs(), BorderLayout.CENTER);
        panel3 = new JPanel(new BorderLayout(5, 5));
        frame = new JFrame(name);
        frame.setIconImage(new ImageIcon("logo.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        send = new Send();
        panel2.add(send, BorderLayout.NORTH);
        panel3.add(statusPanel, BorderLayout.NORTH);
        panel3.add(new Panel3(), BorderLayout.CENTER);
        scrollBar = new JScrollPane(panel1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2 = new JScrollPane(panel3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane = new JScrollPane(panel2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel1.setAutoscrolls(true);
        panel.add(scrollBar);
        panel.add(scrollPane);
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(new Menu());
        border = BorderFactory.createLineBorder(Color.black, 1, true);
        panel2.setBorder(border);
        panel3.setBorder(border);
        panel.add(scrollPane2);
        frame.setContentPane(panel);
        panel1.setBorder(border);
        frame.setVisible(true);
    }

    //reSize the frame.
    public static void setSize(int i, int j) {
        frame.setSize(i, j);
    }

    /**
     * getter
     *
     * @return frame
     */
    public static JFrame getFrame() {
        return frame;
    }

    public static void setFrame(int i) {
        frame.setDefaultCloseOperation(i);
    }

    /**
     * the gets a string and and make a Jlabel for the left panel to save it.
     *
     * @param string
     */
    public static void setInPanel(String string) {
        JPanel panel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(boxLayout);
        panel.setBackground(Color.GRAY);
        panel.add(new JLabel(send.getTypeRequest() + "           " + string + "                      "));
        MouseOption mouseOption = new MouseOption();
        panel.addMouseListener(mouseOption);
        panel1.add(panel);
        panel1.revalidate();
        panel1.repaint();

    }

    /**
     * hide or unHide the sideBar.
     *
     * @param i
     */
    public static void sideBar(int i) {
        if (i % 2 == 0) {
            panel.remove(scrollBar);
        } else {
            panel.add(scrollBar, 0);
            panel.add(scrollPane);
            panel.add(scrollPane2);
        }
        panel.revalidate();
        panel.repaint();
    }

    /**
     * mouse listener class for mouse option.
     **/
    private static class MouseOption implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        //changing the color
        @Override
        public void mouseEntered(MouseEvent e) {
            e.getComponent().setBackground(Color.orange);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.getComponent().setBackground(Color.gray);

        }
    }

    public static void setTime(String time1) {
        time.setText(time1);
    }

    public static void setStatus(String status1) {
        status.setText(status1);
    }
}


