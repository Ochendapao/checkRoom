package ChatClientView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class LogonView extends Frame {


    private Label labelName = new Label("Name");

    private Label labelPassword = new Label("Password");

    private TextField textFieldName = new TextField();

    private TextField textFieldPassword = new TextField();

    private Button buttonOK = new Button("Ok");

    private Button newRegisterButton = new Button("Register");

    private Socket socket;



    public LogonView() {

        this.setSize(500,500);

        this.setVisible(true);

        this.labelName.setBounds(70,70,100,20);

        this.add(labelName);

        this.textFieldName.setBounds(200,70,100,20);

        this.add(textFieldName);

        this.labelPassword.setBounds(70,150,100,20);

        this.add(labelPassword);

        this.textFieldPassword.setBounds(200,150,100,20);

        this.add(textFieldPassword);

        this.buttonOK.setBounds(200,260,70,70);

        this.add(buttonOK);

        this.buttonOK.addActionListener(new buttonOKListener());

        this.newRegisterButton.addActionListener(new newRegisterButtonListener());

        this.newRegisterButton.setBounds(200,390,70,70);

        this.add(newRegisterButton);

    }






    class buttonOKListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                String name = textFieldName.getText();

                String password = textFieldPassword.getText();

                socket = new Socket("0.0.0.0",222);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                writer.write("log" + "\n" + name + "\n" + password + "\n");

                writer.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String suclog = reader.readLine();

                if (suclog.equals("恭喜你，登录成啦")) {

                    ChatClientView chatClientView = new ChatClientView(name,socket);

                }



            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }
    }










    class newRegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            RegisterView registerView = new RegisterView();



        }
    }


    public static void main(String[] args) {

        LogonView logonView = new LogonView();

    }


}



