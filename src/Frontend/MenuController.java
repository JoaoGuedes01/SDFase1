/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend;

import Backend.App;
import Backend.Contact;
import Backend.Message;
import Backend.Sistema;
import Backend.User;
import Backend.reqHandler;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author joaog
 */
public class MenuController implements Initializable {

    //Importar o Sistema que guarda os dados da classe App
    Sistema sys = App.sys;

    //Variaveis necessarias para arrastar a janela UNDECORATED
    double x, y;

    //Elementos JAVAFX
    //Elementos SideBar estática
    @FXML
    private Label nick_user;

    //Janelas do Menu
    //Menu
    @FXML
    private Pane menu_pane;

    @FXML
    private Label welcome_user;

    //Adicionar Amigos
    @FXML
    private Pane addFriends_pane;

    //Definições de utilizador
    @FXML
    private Pane accountSet_pane;

    @FXML
    private TextField nome_txtfld, nick_txtfld, email_txtfld, course_txtfld;

    @FXML
    private TextField contact_ip, contact_port;

    @FXML
    private GridPane friendsGrid, pendingGrid, requestsGrid, messengerGrid, BoardGrid;

    @FXML
    private Pane messenger_pane, friendsPane, requestsPane, PendingPane;

    @FXML
    private Label contactNick;

    @FXML
    private Button msgsend_btn, closeSocketButton;

    @FXML
    private TextField msg_txtfld, msgBoard_txtfld;

    @FXML
    private Pane MuralPane;
    
    @FXML
    private TextField ip_txtfld,port_txtfld;
    
    @FXML
    private Label addFriend_statusLabel,SucessLabel;
    
    @FXML
    private ImageView notif_Req,notif_Friends,notif_Board;

    private static MenuController instance;

    //Funções
    @Override

    public void initialize(URL location, ResourceBundle resources) {
        String QuemSouEu = sys.getUserLogado().getNick();
        new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket(0);
                    sys.getUserLogado().setIP(server.getInetAddress());
                    sys.getUserLogado().setPort(server.getLocalPort());
                    System.out.println("Servidor a escutar em " + sys.getUserLogado().getIP() + " : " + sys.getUserLogado().getPort());

                    while (true) {
                        Socket ligacao = server.accept();
                        reqHandler thread = new reqHandler(ligacao, QuemSouEu);
                        thread.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        updateUserLabels();
        friendsGrid.setVgap(20);
        showMenu();
        clearNotifyReq();
        clearNotifyFriends();
        clearNotifyBoard();
    }

    public MenuController() {
        instance = this;
    }

    public static MenuController getInstance() {
        return instance;
    }
    
    @FXML
    public void notifyReq(){
        notif_Req.setOpacity(1);
    }
    
    @FXML
    public void notifyFriends(){
        notif_Friends.setOpacity(1);
    }
    
    @FXML
    public void notifyBoard(){
        notif_Board.setOpacity(1);
    }
    
    @FXML
    public void clearNotifyReq(){
        notif_Req.setOpacity(0);
    }
    
    @FXML
    public void clearNotifyFriends(){
        notif_Friends.setOpacity(0);
    }
    
    @FXML
    public void clearNotifyBoard(){
        notif_Board.setOpacity(0);
    }

    //Arrastar a janela apenas com a barra de cima
    @FXML
    private void dragWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    private void pressWindow(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    private void addFriendTest() {
        Button btn = new Button("Amigo");
        Label newmsg = new Label("1");
        btn.setId("ContactBtn");
        btn.getStylesheets().add("styles/style.css");
        newmsg.setId("newMessage");
        newmsg.getStylesheets().add("styles/style.css");
        int rows = friendsGrid.getChildren().size();
        friendsGrid.add(btn, 0, rows);
        friendsGrid.add(newmsg, 1, rows);
        System.out.println(friendsGrid.getChildren().size());
    }

    @FXML
    private void addPendingTest() {
        Button btn = new Button("Pending");
        Button btnA = new Button("Aceitar");
        Button btnR = new Button("Recusar");
        btn.setId("ContactBtn");
        btn.getStylesheets().add("styles/style.css");
        int rows = pendingGrid.getChildren().size();
        pendingGrid.add(btn, 0, rows);
        pendingGrid.add(btnA, 1, rows);
        pendingGrid.add(btnR, 2, rows);
        System.out.println(pendingGrid.getChildren().size());
    }

    @FXML
    private void addRequestTest() {
        Button btn = new Button("Request");
        btn.setId("ContactBtn");
        btn.getStylesheets().add("styles/style.css");
        int rows = requestsGrid.getChildren().size();
        requestsGrid.add(btn, 0, rows);
        System.out.println(requestsGrid.getChildren().size());
    }

    @FXML
    private void updateUserLabels() {
        welcome_user.setText("Bem vindo, " + sys.getUserLogado().getNick());
        nick_user.setText(sys.getUserLogado().getNick());
    }

    @FXML
    private void showFriends() {
        friendsGrid.getChildren().clear();
        clearNotifyFriends();
        System.out.println(sys.getContactList().getContactList().size());
        for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
            if (sys.getContactList().getContactList().get(i).getAccepted() == true) {
                Contact contact = sys.getContactList().getContactList().get(i);
                //Criar um Botão novo para o adicionar na lista de Pedidos Pendentes
                Button btn = new Button(contact.getNick());
                //Aplicar estilo no botão com css
                btn.setId("ContactBtn");
                btn.getStylesheets().add("styles/style.css");
                //Adicionar na lista de pedidos pendentes
                int rows = friendsGrid.getChildren().size();
                friendsGrid.add(btn, 0, rows);
                btn.setOnMouseClicked(event -> {
                    try {
                        System.out.println("Abrindo um socket para " + contact.getNick());
                        contactNick.setText(contact.getNick());
                        messengerGrid.getChildren().clear();
                        messenger_pane.toFront();
                        if (contact.getMessageList().size() > 0) {
                            for (int i2 = 0; i2 < contact.getMessageList().size(); i2++) {
                                if (contact.getMessageList().get(i2).getUser().getNick().equals(contact.getNick())) {
                                    addMsgToScreen(contact.getMessageList().get(i2));
                                } else if (contact.getMessageList().get(i2).getUser().getNick().equals(sys.getUserLogado().getNick())) {
                                    addMsgToScreenYou(contact.getMessageList().get(i2));
                                }
                            }
                        }

                        InetAddress destIP = contact.getIP();
                        Integer destPort = contact.getPort();
                        Socket client = new Socket(destIP, destPort);
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        Message Pedido = new Message("privateMessager", "", sys.getUserLogado());
                        out.writeObject(Pedido);
                        System.out.println("Pedido de socket para abrir chat enviado");
                        System.out.println(messengerGrid.getChildren().size());
                        msgsend_btn.setOnMouseClicked(eventSendMsg -> {
                            try {
                                Message message = new Message("privateMessage", msg_txtfld.getText(), sys.getUserLogado());
                                out.writeObject(message);
                                System.out.println("Mensagem enviada");
                                contact.getMessageList().add(message);
                                addMsgToScreenYou(message);
                                msg_txtfld.clear();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
                        closeSocketButton.setOnMouseClicked(eventCloseSocket -> {
                            try {
                                Message message = new Message("CloseSocket", "", sys.getUserLogado());
                                out.writeObject(message);
                                System.out.println("Socket Fechado");
                                menu_pane.toFront();
                                contactNick.setText("");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
        }
        friendsPane.toFront();
    }

    @FXML
    private void addMsgToScreenYou(Message msg) {
        TextField tf = new TextField(msg.getMessage());
        Label lb = new Label("Tu:");
        tf.setId("msgTxtFld");
        tf.getStylesheets().add("styles/style.css");
        tf.setDisable(true);
        lb.setId("contactLabelMsg");
        lb.getStylesheets().add("styles/style.css");
        int rowsMsg = messengerGrid.getChildren().size();
        messengerGrid.add(lb, 1, rowsMsg);
        int rowsMsg2 = messengerGrid.getChildren().size();
        messengerGrid.add(tf, 1, rowsMsg2);
    }

    @FXML
    public void addMsgToScreen(Message msg) {
        TextField tf = new TextField(msg.getMessage());
        Label lb = new Label(msg.getUser().getNick() + ":");
        lb.setId("contactLabelMsg");
        lb.getStylesheets().add("styles/style.css");
        tf.setId("msgTxtFld");
        tf.getStylesheets().add("styles/style.css");
        tf.setDisable(true);
        int rowsMsg = messengerGrid.getChildren().size();
        messengerGrid.add(lb, 0, rowsMsg);
        int rowsMsg2 = messengerGrid.getChildren().size();
        messengerGrid.add(tf, 0, rowsMsg2);
    }

    @FXML
    public void addMsgToBoard(Message msg) {
        TextField tf = new TextField(msg.getMessage());
        Label lb = new Label(msg.getUser().getNick() + ":");
        lb.setId("contactLabelMsg");
        lb.getStylesheets().add("styles/style.css");
        tf.setId("BoardLabelMsg");
        tf.getStylesheets().add("styles/style.css");
        int rowsMsg = BoardGrid.getChildren().size();
        BoardGrid.add(lb, 0, rowsMsg);
        int rowsMsg2 = BoardGrid.getChildren().size();
        BoardGrid.add(tf, 0, rowsMsg2);

    }

    @FXML
    private void showPending() {
        pendingGrid.getChildren().clear();
        System.out.println(sys.getContactList().getContactList().size());
        for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
            if (sys.getContactList().getContactList().get(i).getAccepted() == false) {
                //Criar um Botão novo para o adicionar na lista de Pedidos Pendentes
                Button btn = new Button(sys.getContactList().getContactList().get(i).getNick());
                //Aplicar estilo no botão com css
                btn.setId("ContactBtn");
                btn.getStylesheets().add("styles/style.css");
                //Adicionar na lista de pedidos pendentes
                int rows = pendingGrid.getChildren().size();
                pendingGrid.add(btn, 0, rows);
            }
        }
        PendingPane.toFront();

    }

    @FXML
    private void showRequests() {
        requestsGrid.getChildren().clear();
        clearNotifyReq();
        System.out.println(sys.getRequestList().getRequestList().size());
        for (int i = 0; i < sys.getRequestList().getRequestList().size(); i++) {
            if (sys.getRequestList().getRequestList().get(i).getAccepted() == false) {
                Contact contact = sys.getRequestList().getRequestList().get(i);
                //Criar um Botão novo para o adicionar na lista de Pedidos Pendentes
                Button btn = new Button(sys.getRequestList().getRequestList().get(i).getNick());
                Button btnA = new Button("✔");
                Button btnR = new Button("✘");
                //Aplicar estilo no botão com css
                btn.setId("ContactBtn");
                btnA.setId("btnA");
                btnR.setId("btnR");
                btn.getStylesheets().add("styles/style.css");
                btnA.getStylesheets().add("styles/style.css");
                btnR.getStylesheets().add("styles/style.css");
                //Adicionar na lista de pedidos pendentes
                int rows = requestsGrid.getChildren().size();
                requestsGrid.add(btn, 0, rows);
                requestsGrid.add(btnA, 1, rows);
                requestsGrid.add(btnR, 2, rows);
                btnA.setOnMouseClicked(event -> {
                    try {
                        System.out.println("Aceitando o pedido de " + contact.getNick());
                        InetAddress destIP = contact.getIP();
                        Integer destPort = contact.getPort();

                        Socket client = new Socket(destIP, destPort);

                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                        //Envia o pedido de aceitação
                        Message message = new Message("reqAccepted", "", sys.getUserLogado());
                        out.writeObject(message);
                        out.flush();
                        System.out.println("Pedido de aceitar amizade enviado");

                        //Espera a resposta e ao recebe-la troca o atributo accept para true na lista local de amigos
                        sys.getContactList().addContact(contact.getName(), contact.getNick(), contact.getEmail(), contact.getCourse(), contact.getIP(), contact.getPort(), true);
                        sys.getRequestList().getRequestList().remove(contact);
                        showRequests();
                        client.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
                btnR.setOnMouseClicked(event -> {
                    System.out.println("Recusando o pedido de " + contact.getNick());
                });
            }
        }
        requestsPane.toFront();
    }

    //Mostrar diferentes paineis no Menu
    @FXML
    private void showMenu() {
        menu_pane.toFront();
    }

    @FXML
    private void showAddFriends() {
        SucessLabel.setOpacity(0);
        addFriend_statusLabel.setOpacity(0);
        addFriends_pane.toFront();
    }

    @FXML
    private void sendMessageToBoard() {
        try {
            Message message = new Message("msgBoard", msgBoard_txtfld.getText(), sys.getUserLogado());
            for (int i = 0; i < sys.getContactList().getContactList().size(); i++) {
                if (sys.getContactList().getContactList().get(i).getAccepted() == true) {
                    Contact contact = sys.getContactList().getContactList().get(i);

                    InetAddress destIP = contact.getIP();
                    Integer destPort = contact.getPort();

                    Socket client = new Socket(destIP, destPort);

                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                    out.writeObject(message);
                    out.flush();
                    System.out.println("Mensagem de Board enviada");
                }
            }
            addMsgToBoard(message);
            msgBoard_txtfld.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addContact() {

        try {
            InetAddress destIP = InetAddress.getByName(contact_ip.getText());
            Integer destPort = Integer.parseInt(contact_port.getText());
            Socket client = new Socket(destIP, destPort);

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            //Envia o pedido de amizade juntamente com o objeto User que o enviou
            Message message = new Message("addFriend", "", sys.getUserLogado());
            out.writeObject(message);
            out.flush();
            SucessLabel.setOpacity(1);


            //Recebe o objeto User a quem foi enviado o pedido
            User usr = (User) in.readObject();
            client.close();
            System.out.println("Resposta de " + usr.getName() + " " + usr.getNick() + " " + usr.getEmail() + " " + usr.getCourse() + " em " + usr.getIP() + ": " + usr.getPort());

            if (sys.getContactList().getContactList().stream().anyMatch(o -> o.getNick().equals(usr.getNick()))) {
                System.out.println("ja existe");
            } else {
                sys.getContactList().addContact(usr.getName(), usr.getNick(), usr.getEmail(), usr.getEmail(), usr.getIP(), usr.getPort(), false);
                System.out.println(sys.getContactList().getContactList().get(0).getName());

            }

        } catch (Exception e) {
                    addFriend_statusLabel.setOpacity(1);
            e.printStackTrace();
        }
    }

    @FXML
    private void showAccSettings() {
        nome_txtfld.setText(sys.getUserLogado().getName());
        nick_txtfld.setText(sys.getUserLogado().getNick());
        email_txtfld.setText(sys.getUserLogado().getEmail());
        course_txtfld.setText(sys.getUserLogado().getCourse());
        ip_txtfld.setText("IP: " + sys.getUserLogado().getIP());
        port_txtfld.setText("Port: " + sys.getUserLogado().getPort());
        accountSet_pane.toFront();
    }

    @FXML
    private void updateUserInfo() {
        sys.getUserLogado().setName(nome_txtfld.getText());
        sys.getUserLogado().setNick(nick_txtfld.getText());
        sys.getUserLogado().setCourse(course_txtfld.getText());
        updateUserLabels();
        showMenu();
    }

    @FXML
    private void showMessenger() {
        messenger_pane.toFront();
    }

    @FXML
    private void showBoard() {
        clearNotifyBoard();
        MuralPane.toFront();
    }

    //Fechar a App
    @FXML
    private void closeApp() {
        System.out.println("A fechar a app...");
        System.exit(0);
    }

    public GridPane getMessengerGrid() {
        return messengerGrid;
    }

    public Label getContactNick() {
        return contactNick;
    }

}
