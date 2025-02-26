package tec.ChatApp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Random;

/**
 * Esta clase se encarga de implementar el servidor para recibir los mensajes de los clientes.
 * @author David
 * @see java.lang.Runnable
 * @see java.util.Observable
 */
public class Server extends Observable implements Runnable {

    //atributos
    /**
     * port es el puerto del servidor.
     */
    int port; // puerto que el servidor va a utilizar

    /**
     *Constructor del servidor donde crea un puerto random
     */
    public Server(){
        this.port = port_seacrh();
    }

    /**
     * Se encarga correr el servidor y estar esperando por algun input.
     */
    @Override
    public void run() {
        //instanciar clases necesarias
        ServerSocket server = null;
        Socket client = null;
        DataInputStream in;

        //try para prevenir problemas con la creacion del server socket
        try{

            server = new ServerSocket(this.port);//create server socket

            while(true){ // loop para tener el server corriendo

                client = server.accept();// accept client input

                in = new DataInputStream(client.getInputStream());// create DataInputStream

                String msg = in.readUTF();//read message to send to client

                this.setChanged();
                this.notifyObservers(msg);//notify observers(client)
                this.clearChanged();// clear

                client.close();// close client

            }
        } catch (IOException e) {
            e.printStackTrace();
            port = port_seacrh();// busca otro puerto que no este ocupado
        }
    }

    /**
     * Se encarga de buscar un puerto para el server
     * @return port random num entre 1000  y 6000
     */
    public static int port_seacrh(){
        Random port_r = new Random();// create random class
        int port = 0;
        while(port < 1500){ // buscar port hasta que sea mayor que 1500
            port = port_r.nextInt(6000);
        }
        return port;
    }
}
