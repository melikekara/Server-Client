/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoGameServer;

import Game.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SClient {

    int id;
    public String name = "NoName";
    Socket socket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;

    Listen listenThread;

    PairingThread pairThread;

    SClient rival; //rakip client

    public boolean paired = false;

    public SClient(Socket gelenSocket, int id) {
        this.socket = gelenSocket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.socket.getOutputStream());
            this.sInput = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);
    }
    //client mesaj gönderme

    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class Listen extends Thread {

        SClient theClient;

        Listen(SClient theClient) {
            this.theClient = theClient;
        }

        public void run() {
            while (theClient.socket.isConnected()) {
                try {
                    Message received = (Message) (theClient.sInput.readObject());

                    switch (received.type) {
                        case Name:
                            theClient.name = received.content.toString();
                            theClient.pairThread.start();
                            break;
                        case Disconnect:
                            break;
                        case Text:
                            Server.Send(theClient.rival, received);
                            break;
                        case Bitis:
                            break;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    Server.Clients.remove(theClient);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    Server.Clients.remove(theClient);
                }

            }
        }

    }

    class PairingThread extends Thread {

        SClient theClient;

        PairingThread(SClient theClient) {
            this.theClient = theClient;
        }

        public void run() {
            while (theClient.socket.isConnected() && theClient.paired == false) {
                try {
                    Server.pairTwo.acquire(1);

                    if (!theClient.paired) {
                        SClient cRival = null;
                        while (cRival == null && theClient.socket.isConnected()) {

                            for (SClient clnt : Server.Clients) {
                                if (theClient != clnt && clnt.rival == null) {
                                    cRival = clnt;
                                    cRival.paired = true;
                                    cRival.rival = theClient;
                                    theClient.rival = cRival;
                                    theClient.paired = true;
                                    break;
                                }
                            }

                            sleep(1000);
                        }
                        Message msg1 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = theClient.name;
                        Server.Send(theClient.rival, msg1);

                        Message msg2 = new Message(Message.Message_Type.RivalConnected);
                        msg2.content = theClient.rival.name;
                        Server.Send(theClient, msg2);
                    }
                    Server.pairTwo.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

}
