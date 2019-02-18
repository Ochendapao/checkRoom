package socketLession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import Thread.MyThread2;

import javax.swing.plaf.synth.SynthRadioButtonMenuItemUI;


public class ChatServer  {






    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(2222);


        while(true) {
            Socket socket = serverSocket.accept();

            MyThread2 myThread2 = new MyThread2("server1",socket);

            new Thread(myThread2).start();
        }



    }
}
