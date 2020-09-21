package ChatApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public class App extends JFrame implements Observer {
    private JTextArea Chat_output;
    private JPanel panelMain;
    private JTextField Chat_input;
    private JButton Send_b;
    private JTextField Ip_input;
    private JButton Connect_b;
    private JButton Disconnect_b;
    private JTextField Port_input;
    private JTextField Name_input;
    private JLabel Name_label;
    private JTextField Your_port;
    private Server server;
    private Client client;
    int port;
    int connect_port = 0;


    public App() {
        add(panelMain);
        search_port();

        server = new Server();
        port = server.port;
        server.addObserver(this);
        Thread t = new Thread(server);
        t.start();

        Your_port.setText(String.valueOf(port));

        Send_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = Chat_input.getText();
                String name = Name_input.getText();
                String msg_send= name+":"+msg+"\n";

                if (!name.equals("")) {
                    if (!msg.equals("")) {
                        if (connect_port != 0){
                            Chat_output.append(msg_send);
                            Chat_input.setText("");

                            client = new Client(connect_port,msg_send);
                            Thread t = new Thread(client);
                            t.start();

                        }else{
                            JOptionPane.showMessageDialog(null, "Please write a port!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please write a message!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please write a name!");
                }

            }

        });

        Connect_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connect_port = Integer.parseInt(Port_input.getText());
                System.out.println(connect_port);
            }
        });
        Disconnect_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(connect_port);
            }
        });
    }


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App ChatAPP = new App();
                ChatAPP.setVisible(true);
                ChatAPP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ChatAPP.setSize(800,400);

            }
        });

    }


    @Override
    public void update(Observable o, Object arg) {
        this.Chat_output.append(String.valueOf(arg));

    }

    public void search_port(){
        Random port_r = new Random();
        while(port < 1000){
            port = port_r.nextInt(6000);
        }

    }
}
