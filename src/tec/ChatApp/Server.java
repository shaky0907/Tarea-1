package tec.ChatApp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Random;

/**
 *
 */
public class Server extends Observable implements Runnable {
    /**
     *
     */
    int port;

    /**
     *Constructor
     */
    public Server(){
        this.port = port_seacrh();
    }

    /**
     *
     */
    @Override
    public void run() {
        ServerSocket server = null;
        Socket client = null;
        DataInputStream in;

        try{
            server = new ServerSocket(this.port);

            while(true){

                client = server.accept();

                in = new DataInputStream(client.getInputStream());

                String msg = in.readUTF();

                this.setChanged();
                this.notifyObservers(msg);
                this.clearChanged();

                client.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
            this.port = port_seacrh();
        }
    }

    /**
     *
     * @return
     */
    public static int port_seacrh(){
        Random port_r = new Random();
        int port = 0;
        while(port < 1000){
            port = port_r.nextInt(6000);
        }
        return port;
    }
}
