package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDTO;
//import lk.ijse.model.Material;
import lk.ijse.entity.Supplier;
import lk.ijse.tdm.MaterialTm;
import lk.ijse.model.tm.SupplierTm;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    public TableView<SupplierTm> tblSupplier;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colTel;
    public TextField txtSupplierName;
    public TextField txtTelNumber;
    public TableColumn<?,?> colNIC;
    public TextField txtSupplierID;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    public void initialize(){
        setCellValueFactor();
        loadAllMaterial();
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                SupplierTm selectedSupplier = newSelection;
                txtSupplierID.setText(String.valueOf(selectedSupplier.getSupplierID()));
                txtSupplierName.setText(selectedSupplier.getName());
                txtTelNumber.setText(selectedSupplier.getTel());
            }
        });
    }

    private void loadAllMaterial() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try{
            List<SupplierDTO> supplierList = supplierBO.getAll();
            for(SupplierDTO s:supplierList){
                SupplierTm tm = new SupplierTm(
                        s.getSupplierID(),
                        s.getName(),
                        s.getTel()
                );
                        obList.add(tm);
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactor() {
        colNIC.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }


    public void btnAdd(ActionEvent actionEvent) {
        String name = txtSupplierName.getText();
        String tel = txtTelNumber.getText();
        String NIC = txtSupplierID.getText();

        if (txtSupplierID.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtTelNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill the data to Add").show();
            return;
        }
        if (isValied()) {
            try {
                supplierBO.add(new SupplierDTO(NIC,name, tel));
                loadAllMaterial();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier added successfully.").show();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add supplier.").show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void clearFields() {
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtTelNumber.clear();
    }
    public void btnSearch(ActionEvent actionEvent) {
        String keyword = txtSupplierID.getText();
        try {
            Supplier searchedSupplier =  supplierBO.search(keyword);
            if (searchedSupplier != null) {
                ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
                SupplierTm tm = new SupplierTm(
                        searchedSupplier.getSupplierID(),
                        searchedSupplier.getName(),
                        searchedSupplier.getTel()
                );
                obList.add(tm);
                tblSupplier.setItems(obList);
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Can't Find.").show();
                tblSupplier.setItems(null);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a supplier to delete.").show();
            return;
        }
        String supplierID = selectedSupplier.getSupplierID();
        try {
            supplierBO.delete(supplierID);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnReset(ActionEvent actionEvent) {
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtTelNumber.clear();
    }

    public void btnPackageDetail(ActionEvent actionEvent) {

    }
    public void btnUpdate(ActionEvent actionEvent) {
        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.ERROR,"Please select a supplier to update.");
            return;
        }

        if (txtSupplierID.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtTelNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill the data to Update").show();
            return;
        }
        String name = txtSupplierName.getText();
        String tel = txtTelNumber.getText();
        String NIC = txtSupplierID.getText();

        try {
            supplierBO.update(new SupplierDTO(selectedSupplier.getSupplierID(), name, tel));
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,"Supplier details updated successfully.");

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to update supplier details.").show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtNameAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName);
    }

    public void txtTelAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtTelNumber);
    }

    public void txtIdAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtSupplierID);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtTelNumber)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtSupplierID)) return false;
        return true;
    }

}
