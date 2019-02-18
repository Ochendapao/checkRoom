package socketLession;

import java.io.*;
import java.net.Socket;

public class ChatClient {

    public ChatClient() throws IOException {


        Socket socket = new Socket("0.0.0.0", 2222);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        String br;
        while ((br = reader.readLine()) != null) {
            System.out.println(br);
        }


    }
}
