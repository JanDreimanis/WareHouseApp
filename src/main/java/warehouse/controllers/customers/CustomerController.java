package warehouse.controllers.customers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.WarehouseApplication;
import warehouse.controllers.view.ViewLoader;
import warehouse.entities.Customer;
import warehouse.repository.CustomerRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {


    private final CustomerRepository customerRepository = new CustomerRepository();
    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public Button backButton;
    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private void addCustomer(ActionEvent event) {
        AddCustomerController addCustomerController = (AddCustomerController) ViewLoader
                .load(getClass().getResource("/userInterface/customer/add_customer.fxml"), "Add customer");
        addCustomerController.addPostOperationCallback(this::populateTable);

    }

    @FXML
    private void deleteCustomer(ActionEvent event) {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) {
            return;
        }
        customerRepository.delete(customer.getId());
        populateTable();
    }

    @FXML
    private void editCustomer(ActionEvent event) {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) {
            return;
        }
        AddCustomerController addCustomerController = (AddCustomerController) ViewLoader.load(getClass()
                .getResource("/userInterface/customer/add_customer.fxml"), "Edit customer");
        addCustomerController.setEditable(customer);
        addCustomerController.addPostOperationCallback(this::populateTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTable();
        populateTable();
    }

    private void configureTable() {
        TableColumn<Customer, Long> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> column3 = new TableColumn<>("Address");
        column3.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> column4 = new TableColumn<>("Phone");
        column4.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTableView.getColumns().add(column1);
        customerTableView.getColumns().add(column2);
        customerTableView.getColumns().add(column3);
        customerTableView.getColumns().add(column4);
    }


    private void populateTable() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        list.addAll(customerRepository.findAll());
        customerTableView.setItems(list);
    }

//    @FXML
//    public void switchCustomerPanel(ActionEvent event) throws Exception {
//        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/customer/customer_list.fxml"))));
//    }

    public void backToPreviousScene(ActionEvent event) throws Exception {

        Parent rootScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/userInterface/main.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootScene);
        stage.setScene(scene);
        stage.show();
    }




}
