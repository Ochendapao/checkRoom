package ChatServerView;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

public class ChatServerView extends Frame {

    private  TextArea textArea = new TextArea(20, 50);

    private HashMap hashMap = new HashMap();

    private ArrayList arrayListUser = new ArrayList<user>();

    private String nameClient;



    public ChatServerView(String title) {

        super(title);

        init();
    }






    private void init() {
        this.setSize(500,500);
        this.add(textArea);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
            }
        });
        try {
            this.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void foracastboard(String content) {

        Set<String> set = hashMap.keySet();

        for (String key: set)  {
            Socket socket = (Socket) hashMap.get(key);

//            System.out.println(key);

            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                writer.write(content);

                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    public void storeUsers(String name1, String password1) {

        user user = new user(name1,password1);

        arrayListUser.add(user);

    }






    class serversThread implements Runnable {

        public Socket socket;

        public String clientName;

        public serversThread(Socket socket, String clientName) {

            this.socket = socket;

            this.clientName = clientName;


        }

        @Override
        public void run() {

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

                String br;



                while ((br = reader.readLine()) != null) {

                    textArea.append(clientName + ":" + br + "\n");

                    String clientContent = clientName + ":" + br + "\n";

                    foracastboard(clientContent);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public void startServer() throws IOException {



        ServerSocket serverSocket = new ServerSocket(222);

        String suc = "Successfully start socket\n";

        textArea.append(suc);

        while (true) {

            Socket socket = serverSocket.accept();

            String out = "new client comes, port:" + socket.getPort() + "\n";

            textArea.append(out);

            //Register and log
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String name;

            String password;

            String who;

            who = reader.readLine();

            if (who.equals("register")) {

                name = reader.readLine();

                password = reader.readLine();

                storeUsers(name,password);

                textArea.append(name + password);

                socket.close();

            }



            if (who.equals("log")) {

                name = reader.readLine();

                nameClient = name;

                password = reader.readLine();

                user userlog = new user(name, password);


                String finalName = name;
                arrayListUser.forEach(item -> {

                   if (userlog.name.equals(((user)item).name) && userlog.password.equals(((user)item).password) ){


                       hashMap.put(finalName,socket);


                       BufferedWriter bufferedWriter = null;
                       try {
                           bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       String sucLog = "恭喜你，登录成啦";

                       try {
                           bufferedWriter.write(sucLog + "\n");
                       } catch (IOException e) {
                           e.printStackTrace();
                       }

                       try {
                           bufferedWriter.flush();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }


                   }
               });




            }




            if (!socket.isClosed()){

                serversThread serversThread = new serversThread(socket,nameClient);


                new Thread(serversThread).start();
            }



        }
    }



    public static void main(String[] args){
        ChatServerView chatserver = new ChatServerView("server");
    }
}



