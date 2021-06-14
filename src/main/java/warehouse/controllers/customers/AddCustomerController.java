package warehouse.controllers.customers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import warehouse.entities.Customer;
import warehouse.repository.CrudRepository;
import warehouse.repository.CustomerRepository;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    private final CustomerRepository customerRepository = new CustomerRepository();

    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;

//    @FXML
//    private StackPane rootPane;

    private Customer editable;

    private Runnable closeDialogCallback;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addPostOperationCallback(Runnable callback) {
        this.closeDialogCallback = callback;
    }

    public void setEditable(Customer customer) {
        this.editable = customer;
        this.name.setText(customer.getName());
        this.address.setText(customer.getAddress());
        this.phone.setText(customer.getAddress());
    }

    @FXML
    private void addCustomer(ActionEvent event) {
        String customerName = name.getText();
        String customerAddress = address.getText();
        String customerPhone = phone.getText();

        if (customerName.isEmpty() || customerAddress.isEmpty() || customerPhone.isEmpty()) {
            return;
        }

        if (editable == null) {
            customerRepository.save(new Customer(customerName, customerAddress, customerPhone));
        } else {
            Customer customer = customerRepository.findOne(editable.getId());
            customer.setName(customerName);
            customer.setAddress(customerAddress);
            customer.setPhone(customerPhone);
            customerRepository.merge(customer);
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
