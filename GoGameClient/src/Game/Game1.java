/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import GoGameClient.Client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author user
 */
public class Game1 extends javax.swing.JFrame {

    /**
     * Creates new form Game1
     */
    public static Game1 game1;
    public Thread tmr_slider;
    public int rivalSelection = -1;
    public int mySelection = -1;
    int myTurn = 0;
    public JFrame frame = new JFrame("Board");



    public Game1() throws IOException {

        initComponents();

        BufferedImage board = ImageIO.read(getClass().getClassLoader().getResource("img//board.png"));

        lbl_name.setText(FirstPage.firstPage.name);

        game1 = this;

        tmr_slider = new Thread(() -> {

            while (Client.socket.isConnected()) {
                final ImagePanel imgPane = new ImagePanel(board);
                try {
                    Thread.sleep(1000);

                    if (rivalSelection != -1 || mySelection != -1) {
                        if (mySelection == 1) {
                            myTurn = 1;
                            JScrollPane scrollPane = new JScrollPane(imgPane);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.add(scrollPane);
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);

                        } if (rivalSelection == 1) {
                            myTurn = 2;
                            JScrollPane scrollPane = new JScrollPane(imgPane);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.add(scrollPane);
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        }

                        //30 saniye sonra oyun bitsin tekrar bağlansın
                        Thread.sleep(30000);
                   
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

    }

    public class ImagePanel extends JPanel {

        private BufferedImage img;
        private BufferedImage image;
        private BufferedImage image2;
        private Point drawPoint;

        public ImagePanel(BufferedImage img) {
            this.img = img;

            try {
                image = ImageIO.read(getClass().getClassLoader().getResource("img//black.png"));
                image2 = ImageIO.read(getClass().getClassLoader().getResource("img//white.png"));

                addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        drawPoint = new Point(e.getPoint());
                        System.out.println(e.getPoint());
                        repaint();
                    }

                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }

        protected Point getImageLocation() {

            Point p = null;
            if (img != null) {
                int x = (getWidth() - img.getWidth()) / 2;
                int y = (getHeight() - img.getHeight()) / 2;
                p = new Point(x, y);
            }
            return p;

        }

        public Point toImageContext(Point p) {
            Point imgLocation = getImageLocation();
            Point relative = new Point(p);
            relative.x -= imgLocation.x;
            relative.y -= imgLocation.y;
            return relative;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                Point p = getImageLocation();
                g.drawImage(img, p.x, p.y, this);
            }

            Graphics2D g2d = (Graphics2D) g.create();
            if (drawPoint != null && myTurn == 1) {
                g2d.drawImage(image, drawPoint.x, drawPoint.y, this);
            } else if (drawPoint != null && myTurn == 2) {
                g2d.drawImage(image2, drawPoint.x, drawPoint.y, this);
            }
            g2d.dispose();

        }

    }

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_rival_name = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_area1 = new javax.swing.JTextArea();
        btn_send_msg = new javax.swing.JButton();
        lbl_name = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_area1.setColumns(20);
        txt_area1.setRows(5);
        jScrollPane2.setViewportView(txt_area1);

        btn_send_msg.setText("Send");
        btn_send_msg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_send_msgActionPerformed(evt);
            }
        });

        lbl_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btn_add.setText("Start");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        txt_area2.setColumns(20);
        txt_area2.setRows(5);
        jScrollPane1.setViewportView(txt_area2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(lbl_name, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178)
                .addComponent(lbl_rival_name, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_send_msg)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_name, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_rival_name, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(btn_send_msg)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_send_msgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_send_msgActionPerformed
        // TODO add your handling code here:

        //metin mesajı gönder
        Message msg = new Message(Message.Message_Type.Text);
        String x = txt_area1.getText();
        msg.content = txt_area1.getText();
        Client.Send(msg);
        txt_area1.setText("");

    }//GEN-LAST:event_btn_send_msgActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:

        mySelection = 1;
        System.out.println("girdi");

        Message msg = new Message(Message.Message_Type.Loc);
        msg.content = mySelection;
        Client.Send(msg);

    }//GEN-LAST:event_btn_addActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        //form kapanırken clienti durdur
        Client.Stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Game1.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    public javax.swing.JButton btn_send_msg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lbl_name;
    public javax.swing.JLabel lbl_rival_name;
    private javax.swing.JTextArea txt_area1;
    public javax.swing.JTextArea txt_area2;
    // End of variables declaration//GEN-END:variables

}
