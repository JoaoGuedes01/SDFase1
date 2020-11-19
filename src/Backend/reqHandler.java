package Backend;

import java.net.*;
import java.io.*;
import java.util.*;
import Backend.Message;
import Backend.App;
import Frontend.MenuController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class reqHandler extends Thread {

//Importar o Sistema que guarda os dados da classe App
    private Sistema sys = App.sys;

    Socket ligacao;
    ObjectInputStream in;
    ObjectOutputStream out;
    String QuemSouEu;

    public reqHandler(Socket ligacao, String QuemSouEu) throws IOException {
        this.ligacao = ligacao;
        this.QuemSouEu = QuemSouEu;

        try {

            InputStream inputStream = ligacao.getInputStream();

            this.out = new ObjectOutputStream(ligacao.getOutputStream());

            this.in = new ObjectInputStream(ligacao.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        
        try {
            Message msg = (Message) in.readObject();
            User usr = (User) msg.getUser();
            System.out.println(msg.getType());

            switch (msg.getType()) {
                case "addFriend":
                    System.out.println("Pedido de amizade recebido de " + msg.getUser().getNick());
                    if (sys.getRequestList().getRequestList().stream().anyMatch(o -> o.getNick().equals(usr.getNick()))) {
                        System.out.println("ja existe nos requests");
                    }
                    if (sys.getContactList().getContactList().stream().anyMatch(o -> o.getNick().equals(usr.getNick()))) {
                        System.out.println("ja existe na contact list");
                    } else {
                        sys.getRequestList().addContact(usr.getName(), usr.getNick(), usr.getEmail(), usr.getCourse(), usr.getIP(), usr.getPort(), false);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MenuController.getInstance().notifyReq();
                            }
                        });

                    }

                    System.out.println(sys.getRequestList().getRequestList().size());
                    out.writeObject(sys.getUserLogado());
                    break;
                case "reqAccepted":
                    System.out.println("Pedido de amizade aceite por " + msg.getUser().getNick());
                    if (sys.getContactList().getContactList().stream().anyMatch(o -> o.getNick().equals(usr.getNick()) && o.getAccepted() == true)) {
                        System.out.println("ja existe");
                    } else {
                        for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
                            if (sys.getContactList().getContactList().get(i).getNick().equals(usr.getNick())) {
                                sys.getContactList().getContactList().get(i).setAccepted(true);
                            }
                        }
                    }
                    break;
                case "privateMessager":
                    Boolean connected = true;
                    System.out.println("Messenger Iniciado");
                    try {
                        while (connected == true) {
                            Message msgPriv = (Message) in.readObject();
                            User usrPriv = (User) msgPriv.getUser();
                            if (msgPriv.getType().equals("CloseSocket")) {
                                connected = false;
                            } else {
                                System.out.println(msgPriv.getMessage());
                                System.out.println(MenuController.getInstance().getContactNick().getText());
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (MenuController.getInstance().getContactNick().getText().equals(msgPriv.getUser().getNick())) {
                                            MenuController.getInstance().addMsgToScreen(msgPriv);
                                            for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
                                                if (sys.getContactList().getContactList().get(i).getNick().equals(usr.getNick())) {
                                                    sys.getContactList().getContactList().get(i).getMessageList().add(msgPriv);
                                                }
                                            }
                                        } else {
                                            MenuController.getInstance().notifyFriends();
                                            for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
                                                if (sys.getContactList().getContactList().get(i).getNick().equals(usr.getNick())) {
                                                    sys.getContactList().getContactList().get(i).getMessageList().add(msgPriv);
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                        ligacao.close();
                        System.out.println("Socket Fechado");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case "msgBoard":
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (MenuController.getInstance().isBoardOn().getText().equals("on")) {
                                System.out.println("Mensagem de Board Recebida");
                                MenuController.getInstance().addMsgToBoard(msg);
                                System.out.println(msg.getMessage());
                            } else {
                                MenuController.getInstance().notifyBoard();
                                System.out.println("Mensagem de Board Recebida");
                                MenuController.getInstance().addMsgToBoard(msg);
                                System.out.println(msg.getMessage());
                            }

                        }
                    });
                    break;

                default:
                    System.out.println("Pedido Normal");
            }

        } catch (Exception e) {
            // out.println("Conexao sem sucesso");
            e.printStackTrace();
        }
    }
}
