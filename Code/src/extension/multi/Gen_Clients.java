package extension.multi;

public class Gen_Clients {

    public static void main(String[] args) {
        String mon_id = String.format("Client_%d", 1);//on crée un ID
        Thread_Client currrent_client = new Thread_Client(mon_id);//on crée un thread client
        Thread myThread= new Thread(currrent_client);
        myThread.start();//on lance Thread
        currrent_client.send("je suis le client tu me reçois ?");//on envoye au serveur un message textuel
    }

}
