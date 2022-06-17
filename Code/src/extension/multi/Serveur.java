package extension.multi;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Serveur extends ServerSocket {
    private final static int port = 12000; /* Port d'écoute */

    public Serveur() throws IOException {
        super(port);
        System.out.println("[Serveur] : Serveur Jouet lancé sur " + (port));// affiche message de demarage
    }
    public void execute() throws IOException, InterruptedException {// execute le serveur
        Socket maConnection;
        List<Thread> myThreads = new ArrayList<Thread>();
        int numClient = 0;
        while (numClient < 5){//tant qu'il y a moin de 5 cient
            System.out.println("[Serveur]: waiting for connexion number "+ numClient);
            maConnection = accept();//attendre la connexion d'un client
            numClient+=1;
            String c_ip = maConnection.getInetAddress().toString();//on recupere l'ip de la machine
            int c_port = maConnection.getPort();//on recupere le port de la machne
            System.out.format("[Serveur]: Arrivé du Client IP %s sur %d\n", c_ip, c_port);
            System.out.format("[Serveur]: Creation du thread T_%d\n", c_port);

            ServiceClientI client = new ServiceClientI(maConnection, "T_" + c_port);//on crée l'object Service client
            Thread cThread= new Thread(client);//on crée le Thread clients
            myThreads.add(cThread);

            client.sleep();//on met le client en veille
            Thread.sleep(2000);
            client.wakeUp();//on sort le client de la veille
            Thread.sleep(2000);
            client.say("et la je dit des mot via la methode say");//on affiche le texte sur l'ecran du client
            Thread.sleep(2000);
            String var =client.input("est ce que tu peu entrez une valeur pour le client");//on demande au client de rentré une valeur
            System.out.println(var);

        }
        System.out.format("Closing all Connections\n"); //ferme les connections
        for (Thread item: myThreads)
            item.interrupt();
        close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        try (Serveur connectionManager = new Serveur()) {
            connectionManager.execute();
        }
    }
}
