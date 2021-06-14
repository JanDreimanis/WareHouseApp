package warehouse.controllers.suppliers;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import warehouse.controllers.customers.AddCustomerController;
import warehouse.controllers.view.ViewLoader;
import warehouse.entities.Customer;
import warehouse.entities.Supplier;
import warehouse.repository.SupplierRepository;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public Button backButton;

    SupplierRepository supplierRepository = new SupplierRepository();

    @FXML
    private TableView<Supplier> supplierTableView;

    @FXML
    private void addSupplier(ActionEvent event) {
        AddSupplierController addSupplierController = (AddSupplierController) ViewLoader
                .load(getClass().getResource("/userInterface/supplier/add_supplier.fxml"), "Add supplier");
        addSupplierController.addPostOperationCallback(this::populateTable);
    }

    @FXML
    private void deleteSupplier(ActionEvent event) {
        Supplier supplier = supplierTableView.getSelectionModel().getSelectedItem();
        if (supplier == null) {
            return;
        }
        supplierRepository.delete(supplier.getId());
        populateTable();
    }

    @FXML
    private void editSupplier(ActionEvent event) {
        Supplier supplier = supplierTableView.getSelectionModel().getSelectedItem();
        if (supplier == null) {
            return;
        }
        AddSupplierController addSupplierController = (AddSupplierController) ViewLoader.load(getClass()
                .getResource("/userInterface/supplier/add_csupplier.fxml"), "Edit supplier");
        addSupplierController.setEditable(supplier);
        addSupplierController.addPostOperationCallback(this::populateTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTable();
        populateTable();
    }

    private void configureTable() {
        TableColumn<Supplier, Long> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Supplier, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Supplier, String> column3 = new TableColumn<>("Address");
        column3.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Supplier, String> column4 = new TableColumn<>("Phone");
        column4.setCellValueFactory(new PropertyValueFactory<>("phone"));

        supplierTableView.getColumns().add(column1);
        supplierTableView.getColumns().add(column2);
        supplierTableView.getColumns().add(column3);
        supplierTableView.getColumns().add(column4);
    }


    private void populateTable() {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        list.addAll(supplierRepository.findAll());
        supplierTableView.setItems(list);
    }

//    @FXML
//    public void switchSupplierPanel(ActionEvent event) throws Exception {
//        Pane pane = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/userInterface/customer/supplier_list.fxml"))));
//    }

    public void backToPreviousScene(ActionEvent event) throws Exception {

        Parent rootScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/userInterface/main.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootScene);
        stage.setScene(scene);
        stage.show();
    }


}
