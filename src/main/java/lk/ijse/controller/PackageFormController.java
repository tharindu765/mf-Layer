package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BatchBO;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.PackageBO;
import lk.ijse.dto.PackageDTO;
import lk.ijse.entity.Package;
import lk.ijse.tdm.PackageTm;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PackageFormController {
    PackageBO packageBO = (PackageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PACKAGE);
    BatchBO bo = (BatchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BATCH);
    public TableView<PackageDTO> tblPackageType;
    public TableColumn<?, ?> colPackageType;
    public TableColumn<?, ?> colUnitPrice;
    public TableColumn<?, ?> colQuantity;
    public TableColumn<?, ?> colBatchID;
    public TextField txtPackageType;
    public TextField txtUnitPrice;
    public TextField txtQuantity;
    public ComboBox<String> cmbBatchID;
    public int packageID;

    public void initialize() {
        setCellValueFactory();
        loadAllOrder();
        getPackageId();
        getBatchID();
        tblPackageType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtPackageType.setText(newValue.getDescription());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtQuantity.setText(String.valueOf(newValue.getQuantity()));
                cmbBatchID.setValue(String.valueOf(newValue.getBatchID()));
            }
        });
    }

    private void loadAllOrder() {
        try {
            List<PackageDTO> packageList = packageBO.getAll();
            ObservableList<PackageDTO> observableList = FXCollections.observableArrayList(packageList);
            tblPackageType.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load package details").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBatchID.setCellValueFactory(new PropertyValueFactory<>("batchID"));
    }

    public void btnUpdate(ActionEvent actionEvent) {
        PackageDTO selectedPackage = tblPackageType.getSelectionModel().getSelectedItem();

        if (selectedPackage == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a package to update.").show();
            return;
        }

        String description = txtPackageType.getText().trim();
        String unitPriceText = txtUnitPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String batchID = cmbBatchID.getValue();

        if (description.isEmpty() || unitPriceText.isEmpty() || quantityText.isEmpty() || batchID == null) {
            new Alert(Alert.AlertType.ERROR, "All fields are required.").show();
            return;
        }

        try {
            double unitPrice = Double.parseDouble(unitPriceText);
            int quantity = Integer.parseInt(quantityText);

            boolean success = packageBO.updatePackage(selectedPackage.getPackageID(), description, unitPrice, quantity, batchID);

            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "Package updated successfully.").show();
                loadAllOrder();
                resetFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update package.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price or quantity.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update package.").show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        PackageDTO selectedPackage = tblPackageType.getSelectionModel().getSelectedItem();

        if (selectedPackage == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a package to delete.").show();
            return;
        }

        try {
            boolean success = packageBO.deletePackage(selectedPackage.getPackageID());

            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "Package deleted successfully.").show();
                loadAllOrder();
                resetFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete package.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete package.").show();
        }
    }


    public void btnReset(ActionEvent actionEvent) {
        resetFields();
    }

    public void brnSearch(ActionEvent actionEvent) {
        String batchID = cmbBatchID.getValue();
        if (batchID == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a batch ID.").show();
            return;
        }

        try {
            List<PackageDTO> searchResults = packageBO.searchPackagesByBatchID(batchID);
            ObservableList<PackageDTO> observableList = FXCollections.observableArrayList(searchResults);
            tblPackageType.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error occurred while searching for packages by batch ID.").show();
        }
    }


    public void btnAdd(ActionEvent actionEvent) {
        String description = txtPackageType.getText().trim();
        String unitPriceText = txtUnitPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String batchID = cmbBatchID.getValue();


        if (description.isEmpty() || unitPriceText.isEmpty() || quantityText.isEmpty() || batchID == null) {
            new Alert(Alert.AlertType.ERROR, "All fields are required.").show();
            return;
        }
        if (isValied()) {

            try {
                double unitPrice = Double.parseDouble(unitPriceText);
                int quantity = Integer.parseInt(quantityText);
                boolean success = packageBO.addPackage(description, unitPrice, quantity, batchID, packageID);
                if (success) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Package added successfully.").show();
                    loadAllOrder();
                    resetFields();
                    getPackageId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to add package.").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid unit price or quantity.").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add package.").show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void resetFields() {
        txtPackageType.clear();
        txtUnitPrice.clear();
        txtQuantity.clear();
        cmbBatchID.getSelectionModel().clearSelection();
    }

    public void btnAddBatch(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Batch_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Batch Form");
        stage.show();
    }

    private void getPackageId() {
        try {
            String currentId = packageBO.getCurrentId();
            String nextPackageId = generateNextPackageId(currentId);
            packageID = Integer.parseInt(nextPackageId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPackageId(String currentId) {
        if (currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }

    private void getBatchID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> IDlist = bo.getBatchID();
            for (String i : IDlist) {
                obList.add(i);
            }
            cmbBatchID.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void cmdBatchOnAction(ActionEvent actionEvent) {

    }

    public void txtUnitPriceReleaseAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE, txtUnitPrice);
    }

    public void txtQtyActionRelese(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY, txtQuantity);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE, txtUnitPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY, txtQuantity)) return false;
        return true;
    }
}