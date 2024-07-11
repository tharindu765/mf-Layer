package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.MaterialBO;
import lk.ijse.bo.custom.PlaceMaterialBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.dto.PlaceMaterialDTO;
import lk.ijse.dto.SupplierDetailDTO;
import lk.ijse.entity.Material;
import lk.ijse.entity.PlaceMaterial;
import lk.ijse.entity.SupplierDetail;
import lk.ijse.model.*;
import lk.ijse.tdm.CustomerTm;
import lk.ijse.tdm.MaterialTm;
import lk.ijse.tdm.OrderTm;


import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialFormController {
    MaterialBO materialBO  = (MaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MATERIAL);
    SupplierBO supplierBO  = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    PlaceMaterialBO placeMaterialBO = (PlaceMaterialBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEMATERIAL);
    public TableView<MaterialTm> tblSupplier;
    public TableColumn<?,?> colMaterialName;
    public TableColumn<?,?> colQty;
    public TableColumn<?,?> colSupplierName;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colTotalPrice;

    public ComboBox<String> cmbSupplierName;
    public Label lblMaterialId;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public TableColumn<?,?> colAction;
    public DatePicker datePicker;
    public ComboBox<String> cmdMaterialName;
    public AnchorPane RootNood;

    private ObservableList<MaterialTm> cart = FXCollections.observableArrayList();
    public void initialize(){
        setCellValueFactor();
        loadAllMaterial();
        getCurrentMaterialId();
        getSupplierName();
        setCmbMaterialName();
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                cmdMaterialName.setValue(newSelection.getMaterialName());
                txtQty.setText(String.valueOf(newSelection.getQty()));
                cmbSupplierName.setValue(newSelection.getSupplierName());
                datePicker.setValue(newSelection.getDate().toLocalDate());
                txtUnitPrice.setText(String.valueOf(newSelection.getUnitPrice()));
            }
        });
    }

    private void loadAllMaterial() {
        ObservableList<MaterialTm> spList = FXCollections.observableArrayList();
        try{
            List<MaterialDTO> materialList = materialBO.getAll();
            for(MaterialDTO material:materialList){
                MaterialTm tm = new MaterialTm(
                        material.getMaterialName(),
                        material.getQty(),
                        material.getName(),
                        material.getDate(),
                        material.getUnitPrice());
                spList.add(tm);
            }
            tblSupplier.setItems(spList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void setCellValueFactor() {
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("MaterialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }


    public void txtQty(ActionEvent actionEvent) {


    }

    public void txtUnitPrice(ActionEvent actionEvent) {

    }

    public void btnUpdate(ActionEvent actionEvent) {
        MaterialTm selectedMaterial = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedMaterial == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a material to update.").show();
            return;
        }
        System.out.println(txtQty.getText());
        System.out.println(selectedMaterial.getRawMaterialId());
        try {
            materialBO.update(
                    materialBO.getRawMaterialID(cmdMaterialName.getValue()),
                    cmdMaterialName.getValue(),
                    Integer.parseInt(txtQty.getText()),
                    cmbSupplierName.getValue(),
                    Date.valueOf(datePicker.getValue()),
                    Double.parseDouble(txtUnitPrice.getText()));

            loadAllMaterial();
            new Alert(Alert.AlertType.INFORMATION, "Material updated successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update material: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnDelete(ActionEvent actionEvent) {

    }

    public void btnReset(ActionEvent actionEvent) {
clearFields();
loadAllMaterial();
    }
    public void brnSearch(ActionEvent actionEvent) {
        String supplierName = cmbSupplierName.getValue();

        try {
            List<MaterialDTO> searchResult = materialBO.searchAllName(supplierName);
            ObservableList<MaterialTm> searchResultList = FXCollections.observableArrayList();
            for (MaterialDTO material : searchResult) {
                MaterialTm tm = new MaterialTm(material.getMaterialName(),
                        material.getQty(),
                        material.getName(),
                        material.getDate(),
                        material.getUnitPrice());
                searchResultList.add(tm);
            }
            tblSupplier.setItems(searchResultList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to search materials: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnAdd(ActionEvent actionEvent) {
        /*for (MaterialTm material : cart) {
            try {
                MaterialRepo.addMaterial(material.getMaterialName(), material.getQty(),
                        material.getName(), material.getDate(), material.getUnitPrice(),
                        material.getRawMaterialId());
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add material.").show();
                e.printStackTrace();
            }
        }
        cart.clear();
        loadAllMaterial();
        new Alert(Alert.AlertType.INFORMATION, "Materials added successfully.").show();
        */
        if (txtQty.getText().isEmpty()||txtUnitPrice.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields.").showAndWait();
            return;
        }
        String materialName = cmdMaterialName.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        String supplierName = cmbSupplierName.getValue();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Date date = Date.valueOf(datePicker.getValue());
        int rawMaterialId = Integer.parseInt(lblMaterialId.getText());

        var material = new MaterialDTO(rawMaterialId,materialName,qty);

        List<SupplierDetailDTO> spmList = new ArrayList<>();

        for (int i = 0; i < tblSupplier.getItems().size(); i++) {
            MaterialTm tm = cart.get(i);
            SupplierDetailDTO supplierDetail = new SupplierDetailDTO(
                            tm.getRawMaterialId(),
                            tm.getSupplierName(),
                            tm.getDate(),
                            tm.getUnitPrice()
);
            spmList.add(supplierDetail);
        }
        PlaceMaterialDTO pm = new PlaceMaterialDTO(material,spmList);

        try {
            boolean isPlaced = placeMaterialBO.placeMaterial(pm);
            if(isPlaced) {
                tblSupplier.getItems().clear();
                getCurrentMaterialId();
                clearFields();
                cmbSupplierName.setDisable(false);
                new Alert(Alert.AlertType.CONFIRMATION, "Material Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Material Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        cmdMaterialName.getSelectionModel().clearSelection();
        txtQty.clear();
        cmbSupplierName.getSelectionModel().clearSelection();
        txtUnitPrice.clear();
    }

    public void txtMaterialNameAction(ActionEvent actionEvent) {

    }

    public void cmbSupplierNameAction(ActionEvent actionEvent) {

    }

    public void btnBatch(ActionEvent actionEvent) {

    }

    public void btnAddSupplier(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Supplier_form.fxml"));
        Parent load = fxmlLoader.load();
        RootNood.getChildren().clear();
        RootNood.getChildren().add(load);
    }

    private void getCurrentMaterialId() {
        try {
            String currentId = materialBO.getCurrentId();
            String materialID = generateNextMaterialId(currentId);
            lblMaterialId.setText(materialID);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextMaterialId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }
    private void getSupplierName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> SupplierList = supplierBO.getSupplierName();
            for (String s:SupplierList){
                obList.add(s);
            }
            cmbSupplierName.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnAddToCart(ActionEvent actionEvent) {
        String materialName = cmdMaterialName.getValue();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int rawMaterialId = Integer.parseInt(lblMaterialId.getText());
        String supplierName = cmbSupplierName.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        Date date = Date.valueOf(datePicker.getValue());

        JFXButton remove = new JFXButton("❌");
        remove.setCursor(Cursor.HAND);
        remove.setStyle("-fx-text-fill: red;");
        remove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int selectedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
                cart.remove(selectedIndex);
                if (cart.isEmpty()) {
                    cmbSupplierName.setDisable(false);
                }
                tblSupplier.refresh();
            }
        });

        if (isValied()) {
            if (!tblSupplier.getItems().isEmpty()) {

                for (int i = 0; i < cart.size(); i++) {
                    String colMaterialNameCellData = (String) colMaterialName.getCellData(i);
                    int colQuantity = (int) colQty.getCellData(i);
                    String supplierNAme = (String) colSupplierName.getCellData(i);
                    System.out.println(supplierNAme);
                    System.out.println(supplierName);
                    Date date1 = (Date) colDate.getCellData(i);
                    if (colMaterialNameCellData.equals(String.valueOf(materialName))) {
                        MaterialTm tm = cart.get(i);
                        qty += tm.getQty();
                        tm.setQty(qty);
                        tblSupplier.refresh();
                        return;
                    }
                }
            }
            cmbSupplierName.setDisable(true);

            MaterialTm tm = new MaterialTm(materialName, qty, supplierName, date, unitPrice, remove, rawMaterialId, supplierName);
            cart.add(tm);
            tblSupplier.setItems(cart);
        }
    }
    public void cmdmaterialOnAction(ActionEvent actionEvent) {

    }
    public void setCmbMaterialName(){
        ObservableList<String> materialList = FXCollections.observableArrayList(
                "Black Powder", "Nice Powder", "Patta", "Saptham", "Sapthaswaram", "BB", "Stick");
        cmdMaterialName.setItems(materialList);
    }

    public void txtDateAction(KeyEvent keyEvent) {

    }

    public void txtQtyAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty);
    }

    public void txtPriceAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtUnitPrice);
    }

    public boolean isValied(){
        if(!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtUnitPrice)) return false;
        return true;
    }
}
