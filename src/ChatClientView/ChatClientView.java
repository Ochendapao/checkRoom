package ChatClientView;

import socketLession.ChatClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;


public class ChatClientView extends Frame {

    private TextArea textArea = new TextArea(250,500);

    private Panel clientPanel = new Panel();

    private Panel clientPanelSent = new Panel();

    private Label labelAddress = new Label("Address:");

    private Label labelPort = new Label("port:");

    private Label labelName = new Label("name:");

    private TextField textAdd = new TextField(10);

    private TextField textPort = new TextField(5);

    private TextField textName = new TextField(5);

    private TextField textContent = new TextField(30);

    private Button buttonConnect = new Button("connect");

    private Button buttonSend = new Button("send");

    private Socket socket;


    public ChatClientView(String title) throws IOException {
        super(title);
        init();
    }





    private void init() {
        this.buttonSend.addActionListener(new sendListener());
//        this.clientPanel.setBounds(0,0,250,500);
//        this.clientPanel.setBackground(Color.green);
//        this.clientPanelSent.setBackground(Color.blue);
        this.buttonConnect.addActionListener(new connectListener());
        this.add(textArea);
        this.add(clientPanel, BorderLayout.NORTH);
        this.add(clientPanelSent, BorderLayout.SOUTH);
        clientPanel.add(labelAddress);
        clientPanel.add(textAdd);
        clientPanel.add(labelPort);
        clientPanel.add(textPort);
        clientPanel.add(labelName);
        clientPanel.add(textName);
        clientPanel.add(buttonConnect);
        clientPanelSent.add(textContent);
        clientPanelSent.add(buttonSend);
        this.pack();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }



    class connectListener implements ActionListener {



        @Override
        public void actionPerformed(ActionEvent e) {

            String host = textAdd.getText();

            try {
                socket = new Socket(host,222);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                writer.write(textName.getText()+"\n");

                writer.flush();

                sbThread sbThread = new sbThread(socket);

                new Thread(sbThread).start();

            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }
    }




    class sendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String textsend = textContent.getText() +"\n";

//                textArea.append(textsend);

                textContent.setText(null);

                writer.write(textsend);

                writer.flush();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }



    class sbThread implements Runnable {

        public Socket socket;

        public sbThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String br;

                while ((br = reader.readLine()) != null) {
                    textArea.append(br + "\n");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }
    }





//
//    public static void main(String[] args) throws IOException {
//
////        ChatClientView chatClientView = new ChatClientView("Client");
////
////
////
////        ChatClientView chatClientView1 = new ChatClientView("mine");
//
//    }
//}
