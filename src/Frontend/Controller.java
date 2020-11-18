/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend;

import Backend.App;
import Backend.Sistema;
import Backend.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author joaog
 */
public class Controller {

    //Importar o Sistema que guarda os dados da classe App
    Sistema sys = App.sys;

    //Variaveis necessarias para arrastar a janela UNDECORATED
    double x, y;

    //Elementos JAVAFX
    //Elementos de janela Informações
    @FXML
    private TextField nome_txtfld;

    @FXML
    private TextField nick_txtfld;

    @FXML
    private TextField email_txtfld;

    @FXML
    private TextField course_txtfld;

    
    //Funções
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
    

    //Gravar os dados e avançar para a lista de amigos
    @FXML
    private void saveInfo(ActionEvent event) throws IOException {
        //Criar uma instância User e grava-la no sistema de dados
        User user = new User(nome_txtfld.getText(), nick_txtfld.getText(), email_txtfld.getText(), course_txtfld.getText());
        sys.setUserLogado(user);
        
        //Mudar de janela para a janela da lista de Amigos
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuForm.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1320, 865));
    }



    //Fechar a App
    @FXML
    private void closeApp() {
        System.out.println("A fechar a app...");
        System.exit(0);
    }

}
