import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * menu class for creating the menu on the top of the frame
 */
public class Menu extends JMenuBar {
    private JMenuBar mb;
    private JMenu application;
    private JMenu view;
    private JMenu help;
    private JMenuItem option;
    private JMenuItem exit;
    private JMenuItem toggleFUllScreen;
    private JMenuItem help1;
    private JMenuItem about;
    private static JCheckBox checkbox;
    private JCheckBox checkbox1;
    private JMenuItem toggleSideBar;

    /**
     * constructor
     */
    public Menu() {
        application = new JMenu("Application");
        application.setMnemonic(KeyEvent.VK_A);
        help = new JMenu("help");
        help.setMnemonic(KeyEvent.VK_H);
        view = new JMenu("view");
        view.setMnemonic(KeyEvent.VK_V);
        toggleFUllScreen = new JMenuItem("toggle fullscreen");
        help1 = new JMenuItem("help");
        option = new JMenuItem("option");
        exit = new JMenuItem("exit");
        about = new JMenuItem("about");
        application.add(option);
        application.add(exit);
        view.add(toggleFUllScreen);
        help.add(about);
        help.add(help1);
        add(application);
        add(view);
        add(help);
        toggleSideBar = new JMenuItem("toggle SideBar");
        KeyStroke keyStroke3 = KeyStroke.getKeyStroke("ctrl S");
        toggleSideBar.setAccelerator(keyStroke3);
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl F");
        toggleFUllScreen.setAccelerator(keyStroke);
        fullScreen fullScreen = new fullScreen();
        toggleFUllScreen.addActionListener(fullScreen);
        MenuHandler menuHandler = new MenuHandler();
        MenuHandler2 menuHandler2 = new MenuHandler2();
        help1.addActionListener(menuHandler2);
        about.addActionListener(menuHandler);
        Option optionListener = new Option();
        option.addActionListener(optionListener);
        exit exitOption = new exit();
        KeyStroke keyStroke2 = KeyStroke.getKeyStroke("ctrl F3");
        exit.setAccelerator(keyStroke2);
        exit.addActionListener(exitOption);
        checkbox = new JCheckBox("follow direct");
        checkbox1 = new JCheckBox("system tray");
        view.add(toggleSideBar);
        toggleSideBar.addActionListener(new SideBar());
    }

    /**
     * listener class for menu.
     */
    private class MenuHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(Gui.getFrame(), ">insomnia simulator\n>Sepehr Moghiseh\n>9831103\n>sepehrmoghiseh@aut.ac.ir", "information", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    //menu class.
    private class MenuHandler2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame1 = new JFrame("help");
            frame1.setSize(400, 400);
            frame1.setVisible(true);
            frame1.setLocationRelativeTo(Gui.getFrame());
        }
    }

    /**
     * full screen option for frame.
     **/

    private class fullScreen implements ActionListener {
        private int flag = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (flag % 2 == 0) {
                flag++;
                Gui.setSize(10000, 1000);
            } else {
                flag++;
                Gui.setSize(1000, 500);
            }
        }
    }

    private class Option implements ActionListener {
        private JFrame optionMenu;
        private JPanel panel;
        private JPanel panelCheckBox;

        private boolean state1;
        private boolean state2;
        private int flag = 0;
        private JButton apply;

        /**
         * option box item for the choosing between system tray or default exit.
         */
        public void makeOptions() {
            if (flag == 0) {
                panelCheckBox = new JPanel(new GridLayout(1, 2, 20, 20));
                apply = new JButton("apply");
                optionMenu = new JFrame("option");
                optionMenu.setSize(300, 300);
                panel = new JPanel(new BorderLayout(20, 20));
                panel.setBorder(new EmptyBorder(5, 5, 5, 5));
                panel.add(panelCheckBox, BorderLayout.CENTER);
                panel.add(apply, BorderLayout.SOUTH);
                optionMenu.setContentPane(panel);

                panelCheckBox.add(checkbox);
                panelCheckBox.add(checkbox1);
                applyButton applyButton = new applyButton();
                apply.addActionListener(applyButton);
                flag++;

            }
        }

        public void actionPerformed(ActionEvent e) {
            int i = 0;
            if (flag == 0) {
                makeOptions();
            }
            optionMenu.setVisible(true);


        }

        /**
         * apply button action for apply button in the option box.
         */
        private class applyButton implements ActionListener {
            int flag = 0;
            SystemTray systemTray = SystemTray.getSystemTray();
            PopupMenu trayMenu = new PopupMenu();
            TrayIcon icon = new TrayIcon(new ImageIcon("logo.png").getImage().getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING), "Insomnia", trayMenu);

            /**
             * action listener for option box
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkbox1.isSelected()) {
                    if (flag == 0) {
                        trayMenu.add("back");
                        trayMenu.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Gui.getFrame().setVisible(true);
                            }
                        });
                        icon.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Gui.getFrame().setVisible(true);
                            }
                        });
                        try {
                            systemTray.add(icon);
                        } catch (AWTException ex) {
                            ex.printStackTrace();
                        }
                        Gui.getFrame().setDefaultCloseOperation(JFrame.ICONIFIED);

                    }
                    flag++;
                }
                if (checkbox1.isSelected() == false) {
                    systemTray.remove(icon);
                    Gui.setFrame(JFrame.EXIT_ON_CLOSE);
                    flag = 0;
                }
            }
        }
    }

    private class exit implements ActionListener {
        /**
         * choose the exit depends on system tray check box in option.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkbox1.isSelected()) {
                Gui.getFrame().setVisible(false);
            } else
                System.exit(0);
        }
    }

    /**
     * side bar is the left panel or panel 1.
     */
    private class SideBar implements ActionListener {
        int flag = 0;

        @Override
        public void actionPerformed(ActionEvent e) {
            Gui.sideBar(flag);
            flag++;
        }
    }

    public static JCheckBox getCheckbox() {
        return checkbox;
    }
}

