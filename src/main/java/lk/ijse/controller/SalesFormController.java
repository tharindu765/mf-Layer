package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Util.Regex;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.OrderBO;
import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.bo.custom.SaleBO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.SaleDTO;
import lk.ijse.entity.Sale;
import lk.ijse.tdm.SaleTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesFormController {
    SaleBO saleBO = (SaleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALE);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERDETAIL);
    public TableColumn<?,?> colOrderID;
    public TableColumn<?,?> colPackageType;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colQty;
    public TableView<SaleTm> tblSale;

    public JFXComboBox<String> cmbStatus;

    public Label lblTotalPrice;

    public Label lblTranslationID;
    public JFXComboBox<String> cmbOrderID;

    public JFXButton btnUpdate;
    public TextField txtNIC;

    public void initialize(){
        LoadAllSale();
        setCellValueFactor();
        setCmbStatus();
        getCurrentTransactionId();
        setcmbOrderID();
        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                try {
                    double t = orderDetailBO.calculateTotalPrice(Integer.parseInt(newValue));
                    lblTotalPrice.setText(String.valueOf(t));
                    txtNIC.setText(orderBO.getCustomerIDFromOrderID(newValue));
                    String saleStatus = saleBO.getSaleStatusByOrderID(newValue);
                    cmbStatus.setValue(saleStatus);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tblSale.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    //txtNIC.setText(OrderRepo.getCustomerIDFromOrderID(String.valueOf(sale.getOrderID())));
                    cmbStatus.setValue(newSelection.getStatus());
                    cmbOrderID.setValue(String.valueOf(newSelection.getOrderID()));
                    //lblTotalPrice.setText();
                    double t = orderDetailBO.calculateTotalPrice(Integer.parseInt(String.valueOf(newSelection.getOrderID())));
                    lblTotalPrice.setText(String.valueOf(t));
                    System.out.println("fuck");
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setcmbOrderID() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<String> OBlist = orderBO.getOrderId();
            for (String i:OBlist){
                obList.add(i);
            }
            cmbOrderID.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactor() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        //colPackageType.setCellValueFactory(new PropertyValueFactory<>("pType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void LoadAllSale() {
        ObservableList<SaleTm> obList = FXCollections.observableArrayList();
        try {
            List<SaleDTO> saleList = saleBO.getAll();
            for (SaleDTO s :saleList){
                SaleTm tm = new SaleTm(s.getOrderID(),s.getPType(),s.getSDate(),s.getStatus(),s.getQty());
                System.out.println(tm);
                obList.add(tm);
            }
            tblSale.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentTransactionId() {
        try {
            String currentId = saleBO.getCurrentId();
            String nextTransationId = generateNextTransationId(currentId);
            lblTranslationID.setText(nextTransationId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextTransationId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }

    public void btnUpdate(ActionEvent actionEvent) {
        if (!isValied()) {
            int OID = Integer.parseInt(cmbOrderID.getValue());
            String status = cmbStatus.getValue();
            try {
                saleBO.updateStatusByOrderIDAndCustomerID(String.valueOf(OID), status);
                tblSale.refresh();
                LoadAllSale();
                setCellValueFactor();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setCmbStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Pending","Paid");

        cmbStatus.setItems(status);
    }

    public void cmbOrderID(ActionEvent actionEvent) {

    }

    public void txtNICAction(ActionEvent actionEvent) {
        ObservableList<SaleTm> obList = FXCollections.observableArrayList();
        String NIC = txtNIC.getText();
        try{
            List<SaleDTO> saleList = saleBO.searchSalesByCustomerID(NIC);
            for (SaleDTO s :saleList){
            SaleTm tm = new SaleTm(s.getOrderID(),s.getPType(),s.getSDate(),s.getStatus(),s.getQty());
            obList.add(tm);
        }
        tblSale.setItems(obList);
            setcmbOrderID();
    } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBill(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Bill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> data = new HashMap<>();
        String OID = cmbOrderID.getValue();
        data.put("ID",OID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void txtNICReleceAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtNIC);
    }
    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NIC, txtNIC)) return false;
    return true;
    }

    }

