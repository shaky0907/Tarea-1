package tec.ChatApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Esta clase se encarga de implementar la ventana donde el cliente.
 * puede interactuar y realizar sus acciones.
 * @author David
 * @see javax.swing.JFrame
 * @see java.util.Observer
 * @see java.awt.Component
 * @see java.awt.Container
 * @see java.awt.Frame
 * @see java.awt.image.ImageObserver
 * @see java.awt.MenuContainer
 * @see java.awt.Window
 * @see java.io.Serializable
 * @see javax.accessibility.Accessible
 * @see javax.swing.RootPaneContainer
 * @see javax.swing.WindowConstants
 *
 */
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
    int port;
    int connect_port = 0;

    /**
     * Contructor de App donde añade todos los widgets de swing
     * y instancia al servidor.
     * Tambien se encarga de los Actionlistener para los botones.
     */
    public App() {

        add(panelMain);

        Server server = new Server();
        port = server.port;
        server.addObserver(this);
        Thread t = new Thread(server);
        t.start();

        Your_port.setText(String.valueOf(port));

        /*
        Listener de Send_b
        Se encarga de mandar el mensaje al puerto deseado
         */
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

                            Client client = new Client(connect_port,msg_send);
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
        /*
        Listener de Connect_b
        Se encarga de registrar el puerto a donde se quiere mandar
         */
        Connect_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect_port = Integer.parseInt(Port_input.getText());
                if (connect_port != 0) {
                    Chat_output.append("Connected to :"+connect_port+"\n");
                    Chat_input.setText("");

                    Client client = new Client(connect_port,Name_input.getText()+" "+"Connected\n");
                    Thread t = new Thread(client);
                    t.start();
                }else{
                    JOptionPane.showMessageDialog(panelMain,"Please write a port");
                }
            }
        });

        /*
        Listener de Disconnect_b
        Se encarga de "desconectar" al cliente del otro
         */
        Disconnect_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Port_input.setText("");
                Chat_output.setText("Disconnected\n");
                Chat_input.setText("");

                Client client = new Client(connect_port,Name_input.getText()+" "+"disconnected\n");
                Thread t = new Thread(client);
                t.start();

                connect_port = 0;

            }
        });
    }

    /**
     * main() method donde crea la instancia de la ventana.
     * @param args guarda los command line arguments para el programa.
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, UnsupportedLookAndFeelException, InstantiationException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App ChatAPP = new App();
                ChatAPP.setVisible(true);
                ChatAPP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ChatAPP.setSize(400,400);
                ChatAPP.setResizable(false);
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {
        this.Chat_output.append(String.valueOf(arg));

    }

}
