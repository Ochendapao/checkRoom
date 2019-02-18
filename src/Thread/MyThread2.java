package Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyThread2 implements Runnable {
    public String name;
    private Socket socket;

    public MyThread2 (String name, Socket socket) {

        this.name = name;
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String br;

            while ((br = reader.readLine()) != null){
                System.out.println(br);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}


