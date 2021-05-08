/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoGameClient;

import Game.FirstPage;
import Game.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Game.Game1;
import static GoGameClient.Client.sInput;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author INSECT
 */
// serverdan gelecek mesajları dinleyen thread
class Listen extends Thread {

    static int x1 ;
    static int y1 ;

    public void run() {

        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            
            try {
             
                Message received = (Message) (sInput.readObject());
               
                switch (received.type) {
                    case Name:
                        break;
                    case Loc:
                        Game1.game1.rivalSelection = (int) received.content;
                        break;
                    case RivalConnected:
                        String name = received.content.toString();
                        Game1.game1.lbl_rival_name.setText(name);
                        Game1.game1.btn_send_msg.setEnabled(true);
                        Game1.game1.tmr_slider.start();
                        break;
                    case Disconnect:
                        break;
                    case Text:
                        Game1.game1.txt_area2.setText(received.content.toString());
                        break;
                    case Bitis:
                        break;
                        
                }
                
            } catch (IOException ex) {
                
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
               
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            
                break;
            }
        }

    }

   

}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            msg.content = Game1.game1.lbl_name.getText();
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
