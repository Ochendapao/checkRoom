package ChatClientView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RegisterView extends Frame {


    private Label labelName = new Label("Name");

    private Label labelPassword = new Label("Password");

    private TextField textFieldName = new TextField();

    private TextField textFieldPassword = new TextField();

    private Button buttonRegister = new Button("Ok");

    private Socket socket;


    public RegisterView() {

        this.setVisible(true);

        init();
    }


    public void init() {

//        this.setVisible(true);

        this.setLayout(null);

        this.setSize(500, 500);


        this.labelName.setBounds(70, 70, 100, 20);

        this.add(labelName);

        this.textFieldName.setBounds(200, 70, 100, 20);

        this.add(textFieldName);

        this.labelPassword.setBounds(70, 150, 100, 20);

        this.add(labelPassword);

        this.textFieldPassword.setBounds(200, 150, 100, 20);

        this.add(textFieldPassword);

        this.buttonRegister.setBounds(200, 260, 70, 70);

        this.add(buttonRegister);


        this.buttonRegister.addActionListener(new registerListener());

//        this.setVisible(true);

    }


    class registerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String name = textFieldName.getText();

            String password = textFieldPassword.getText();

            try {
                socket = new Socket("0.0.0.0", 222);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                writer.write("register" + "\n" + name + "\n" + password + "\n");

                writer.flush();


            } catch (IOException e1) {
                e1.printStackTrace();
            }



        }

    }
}

