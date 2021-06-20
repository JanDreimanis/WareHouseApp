package warehouse.controllers.products;

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
import javafx.stage.Stage;
import warehouse.controllers.view.ViewLoader;
import warehouse.entities.Product;
import warehouse.repository.ProductRepository;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    ProductRepository productRepository = new ProductRepository();
    public Button addButton;
    public Button deleteButton;
    public Button editButton;
    public Button backButton;
    @FXML
    private TableView<Product> productTableView;


    @FXML
    private void addProduct(ActionEvent event) {
        AddProductController addProductController = (AddProductController) ViewLoader
                .load(getClass().getResource("/userInterface/product/add_product.fxml"), "Add product");
        addProductController.addPostOperationCallback(this::populateTable);

    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        productRepository.delete(product.getId());
        populateTable();
    }

    @FXML
    private void editProduct(ActionEvent event) {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }
        AddProductController addProductController = (AddProductController) ViewLoader.load(getClass()
                .getResource("/userInterface/product/add_product.fxml"), "Edit product");
        addProductController.setEditable(product);
        addProductController.addPostOperationCallback(this::populateTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTable();
        populateTable();
    }

    private void configureTable() {
        TableColumn<Product, Long> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> column2 = new TableColumn<>("Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> column3 = new TableColumn<>("Quantity");
        column3.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, String> column4 = new TableColumn<>("Unit");
        column4.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Product, Double> column5 = new TableColumn<>("Price");
        column5.setCellValueFactory(new PropertyValueFactory<>("price"));

//        TableColumn<Product, Long> column6 = new TableColumn<>("Suppliers ID");
//        column6.setCellValueFactory(new PropertyValueFactory<>("suppliers_id"));

        productTableView.getColumns().add(column1);
        productTableView.getColumns().add(column2);
        productTableView.getColumns().add(column3);
        productTableView.getColumns().add(column4);
        productTableView.getColumns().add(column5);
//        productTableView.getColumns().add(column6);
    }


    private void populateTable() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        list.addAll(productRepository.findAll());
        productTableView.setItems(list);
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
