package warehouse.controllers.products;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.entities.Product;
import warehouse.entities.Supplier;
import warehouse.repository.ProductRepository;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {


    private final ProductRepository productRepository = new ProductRepository();

    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField unit;
    @FXML
    private TextField price;
    @FXML
    private TextField suppliersId;


    private Product editable;

    private Runnable closeDialogCallback;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addPostOperationCallback(Runnable callback) {
        this.closeDialogCallback = callback;
    }

    public void setEditable(Product product) {
        this.editable = product;
        this.name.setText(product.getName());
        this.quantity.setText(product.getQuantity().toString());
        this.unit.setText(product.getUnit());
        this.price.setText(product.getPrice().toString());
        this.suppliersId.setText(product.getSupplierId().toString());
    }

    @FXML
    private void addProduct(ActionEvent event) {
        String productName = name.getText();
        Integer productQuantity = Integer.parseInt(quantity.getText());
        String productUnit = unit.getText();
        Double productPrice =Double.parseDouble(price.getText()) ;
        Long productSupplierId =Long.parseLong(suppliersId.getText());

        if (productName.isEmpty() || productQuantity == 0 || productUnit.isEmpty()
        || productPrice == 0) {
            return;
        }

        if (editable == null) {
            productRepository.save(new Product(productName, productQuantity, productUnit,productPrice, productSupplierId));
        } else {
            Product product = productRepository.findOne(editable.getId());
            product.setName(productName);
            product.setQuantity(productQuantity);
            product.setUnit(productUnit);
            product.setPrice(productPrice);
            product.setSupplierId(productSupplierId);
            productRepository.merge(product);
        }
        clearEntries();
        closeStage();
        closeDialogCallback.run();
    }

    @FXML
    private void cancel(ActionEvent event) {
        closeStage();
    }

    private void clearEntries() {
        editable = null;
        name.clear();
        quantity.clear();
        unit.clear();
        price.clear();
        suppliersId.clear();
    }
    private void closeStage() {
        Stage stage = new Stage();
        stage.close();
    }
}
