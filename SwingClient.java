import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class SwingClient implements ActionListener {

    private Document doc;
    private SimpleAttributeSet attributeSet;
    private JListSW list;
    private JTextField message;
    private JButton button;
    private MySocket socket;
    private String name;

    public SwingClient(String name, MySocket socket) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String text = message.getText();
        message.setText("");
        if (text.length() > 0) {
            socket.write(name + "> " + text);
            try {
                doc.insertString(doc.getLength(), text + "\n", attributeSet);
            } catch (BadLocationException e) {
                System.out.println("Error. Message not valid.");
            }
        }
    }

    public void addMessage(String text) {
        String[] rcv = text.split("> ", 2);
        list.addUser(rcv[0]);
        try {
            doc.insertString(doc.getLength(), rcv[0] + " -> " + rcv[1] + "\n", attributeSet);
        } catch (BadLocationException e) {
            System.out.println("Error. Message not valid.");
        }
    }

    public void createAndShowGUI(String name) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error setting look and feel.");
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      
        JPanel outPanel = new JPanel();
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.LINE_AXIS));
        
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        attributeSet = new SimpleAttributeSet();
        StyleConstants.setBold(attributeSet, true);
        textPane.setCharacterAttributes(attributeSet, true);
        attributeSet = new SimpleAttributeSet();
        doc = textPane.getStyledDocument();
        
        JScrollPane textScrollPane = new JScrollPane(textPane);
        textScrollPane.setPreferredSize(new Dimension(450, 150));
        outPanel.add(textScrollPane, BorderLayout.CENTER);

        
        list = new JListSW();
        JScrollPane listScrollPane = new JScrollPane(list.getJList());
        outPanel.add(listScrollPane);

        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        
        message = new JTextField(30);
        button = new JButton("Enviar");
        message.addActionListener(this);
        button.addActionListener(this);
        
        inputPanel.add(message);
        inputPanel.add(button);

  
        frame.add(outPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.PAGE_END);

        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
