import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * the class for send box in the panel 2.
 */
public class Send extends JPanel {
    private JTextArea requestArea;
    private JComboBox typeRequest;
    private JButton send;

    /**
     * constructor.
     */
    public Send() {
        requestArea = new JTextArea();
        requestArea.append("enter the Address");
        typeRequest = new JComboBox();
        typeRequest.addItem("GET");
        typeRequest.addItem("POST");
        typeRequest.addItem("DELETE");
        typeRequest.addItem("PUT");
        typeRequest.addItem("PATCH");
        send = new JButton("send");
        setLayout(new GridBagLayout());
        add(typeRequest);
        add(requestArea);
        requestArea.setPreferredSize(new Dimension(180, 30));
        send.setPreferredSize(new Dimension(63, 30));
        typeRequest.setPreferredSize(new Dimension(65, 30));
        add(send);
        Border border = BorderFactory.createLineBorder(Color.black, 1, true);
        requestArea.setBorder(border);
        send.addActionListener(new SendOption());


    }

    public static String getHeaders(ArrayList<JPanel> container) {
        String headers = "\"";
        for (JPanel panel : container) {
            JTextArea textArea = (JTextArea) panel.getComponent(0);
            JTextArea textArea2 = (JTextArea) panel.getComponent(1);
            if (textArea.getText().equals("key") == false && textArea2.getText().equals("value") == false) {
                headers = headers + textArea.getText() + ':';
                headers = headers + textArea2.getText() + ';';
            }

        }
        headers = headers.substring(0, headers.length() - 1);
        headers = headers + "\"";
        if (headers.equals("\""))
            return null;
        return headers;
    }

    public static String getParams(ArrayList<JPanel> containerForms) {
        String params = "\"";
        for (JPanel panel : containerForms) {
            JTextArea textArea = (JTextArea) panel.getComponent(0);
            JTextArea textArea2 = (JTextArea) panel.getComponent(1);
            if (textArea.getText().equals("key") == false && textArea2.getText().equals("value") == false) {
                params = params + textArea.getText() + '=';
                params = params + textArea2.getText() + '&';
            }

        }
        params = params.substring(0, params.length() - 1);
        params = params + "\"";
        if (params.equals("\""))
            return null;
        return params;
    }

    public String getTypeRequest() {
        return typeRequest.getSelectedItem().toString();
    }

    /**
     * save the request here.
     */
    private class SendOption implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String headers = getHeaders(Tabs.getContainer());
            String params = getParams(Tabs.getContainerForm());
            String commend = null;
            if (headers == null && params == null) {
                commend = requestArea.getText() + " -M " + (String) typeRequest.getSelectedItem();
            } else if (headers == null && params != null) {
                commend = requestArea.getText() + " -M " + (String) typeRequest.getSelectedItem() + " -d " + params;
            } else if (headers != null && params == null) {
                commend = requestArea.getText() + " -M " + (String) typeRequest.getSelectedItem() + " -H " + headers;
            } else {
                commend = requestArea.getText() + " -M " + (String) typeRequest.getSelectedItem() + " -H " + headers + " -d " + params;
            }
            if (Menu.getCheckbox().isSelected()) {
                commend = commend + " -f";
            }
            HttpRequest httpRequest = new HttpRequest(commend);
            httpRequest.execute();
            Gui.setInPanel(requestArea.getText());
        }
    }
}
