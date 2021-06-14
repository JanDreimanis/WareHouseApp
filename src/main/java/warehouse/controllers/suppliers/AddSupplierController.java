package warehouse.controllers.suppliers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import warehouse.entities.Supplier;
import warehouse.repository.SupplierRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierController implements Initializable {

    private final SupplierRepository supplierRepository = new SupplierRepository();

    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    //    @FXML
//    private StackPane rootPane;

    private Supplier editable;

    private Runnable closeDialogCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addPostOperationCallback(Runnable callback) {
        this.closeDialogCallback = callback;
    }

    public void setEditable(Supplier supplier) {
        this.editable = supplier;
        this.name.setText(supplier.getName());
        this.address.setText(supplier.getAddress());
        this.phone.setText(supplier.getAddress());
    }
    @FXML
    private void addSupplier(ActionEvent event) {
        String supplierName = name.getText();
        String supplierAddress = address.getText();
        String supplierPhone = phone.getText();

        if (supplierName.isEmpty() || supplierAddress.isEmpty() || supplierPhone.isEmpty()) {
            return;
        }

        if (editable == null) {
            supplierRepository.save(new Supplier(supplierName, supplierAddress, supplierPhone));
        } else {
            Supplier supplier = supplierRepository.findOne(editable.getId());
            supplier.setName(supplierName);
            supplier.setAddress(supplierAddress);
            supplier.setPhone(supplierPhone);
            supplierRepository.merge(supplier);
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
        address.clear();
        phone.clear();
    }
    private void closeStage() {
        Stage stage = new Stage();
        stage.close();
    }
}
