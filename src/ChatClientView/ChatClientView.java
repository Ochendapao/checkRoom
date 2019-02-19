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


    private Panel clientPanelSent = new Panel();


    private TextField textContent = new TextField(30);


    private Button buttonSend = new Button("send");

    private Socket socket;

    private String clientName;







    public ChatClientView(String title, Socket socket1) throws IOException {



        super(title);

        this.setSize(700,700);

        init();

        socket = socket1;

        clientName = title;

        sbThread sbThread = new sbThread(socket);

        new Thread(sbThread).start();
    }





    private void init() {
        this.buttonSend.addActionListener(new sendListener());

        this.add(textArea);

        this.add(clientPanelSent, BorderLayout.SOUTH);
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







    class sendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String textsend = textContent.getText() +"\n";


                textContent.setText(null);

                writer.write(textsend);

                writer.flush();

//                textArea.append(clientName + ":" + textsend);

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




