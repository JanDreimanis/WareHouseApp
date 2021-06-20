package warehouse.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import warehouse.configuration.HibernateUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public Button customersButton;
    public Button suppliersButton;
    public Button productsButton;
    public Button ordersButton;
    public AnchorPane navigation;
    public Button closeSession;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main controller initialized!!");
    }


    @FXML
    private BorderPane content;

    @FXML
    public void switchCustomerPanel(ActionEvent event) throws Exception {
        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/customer/customer_list.fxml"))));
        content.setLeft(pane);
    }

    @FXML
    public void switchProductPanel(ActionEvent event) throws Exception {
        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/product/product_list.fxml"))));
        content.setLeft(pane);
    }
    @FXML
    public void switchSupplierPanel(ActionEvent event) throws Exception {
        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/supplier/supplier_list.fxml"))));
        content.setLeft(pane);
    }

    @FXML
    public void switchOrderPanel(ActionEvent event) throws Exception {
        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/order/order_list.fxml"))));
        content.setLeft(pane);
    }

    @FXML
    public void closeSession(ActionEvent event) throws Exception {
        Platform.exit();
    }


}
