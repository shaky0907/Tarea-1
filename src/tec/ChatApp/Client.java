package tec.ChatApp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    int port;
    String msg;

    public Client(int port,String msg){
        this.port = port;
        this.msg = msg;
    }

    @Override
    public void run() {
        final String Host = "127.0.0.1";
        DataOutputStream out;

        try{

            Socket client = new Socket(Host,port);

            out = new DataOutputStream(client.getOutputStream());

            out.writeUTF(msg);

            client.close();

        }catch (IOException ex){
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
}
