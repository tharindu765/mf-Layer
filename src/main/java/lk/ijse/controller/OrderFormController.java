package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.bo.custom.*;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.PlaceOrder;
import lk.ijse.entity.Sale;
import lk.ijse.tdm.OrderTm;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderFormController {
    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    SaleBO saleBO = (SaleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALE);
    IncensePackageBO incensePackageBO = (IncensePackageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.IncensePackage);
    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACEORDER);
    public TableColumn<?,?> coldate;
    public TableColumn<?,?> colIncenseType;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colOrderID;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colQuantity;
    public TableColumn<?,?> colPrice;
    public TextField txtQty;
    public TextField txtPrice;
    public TextField txtName;
    public TextField txtIncenseType;
    public TableView<OrderTm> tblOrder;
    public DatePicker txtDate;
    public Label lblOrderId;
    public TextField txtUnitPrice;
    public JFXComboBox<String> cbmIncenseType;
    public TextField txtTotalPrice;

    public String transationID;
    public Label lblCustomerName;
    public TableColumn<?,?> colAction;
    public TextField txtCustomerID;
    public AnchorPane RootNood;
    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();

    public void initialize(){
//        getCustomerID();
      //  txtDate.setValue(LocalDate.now());
        getIncenseType();
        setCellValueFactor();
        loadAllOrder();
        getCurrentOrderId();
        setTotalPrice();
        getCurrentTransactionId();

        tblOrder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtDate.setValue(newSelection.getDate().toLocalDate());
                cbmIncenseType.setValue(newSelection.getIncenseType());
                try {
                    txtCustomerID.setText(String.valueOf(customerBO.getCustomerID(newSelection.getCustomerName())));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                txtQty.setText(String.valueOf(newSelection.getQty()));
                txtTotalPrice.setText(String.valueOf(newSelection.getTotalPrice()));
                double unitPrice = newSelection.getTotalPrice() / newSelection.getQty();
                txtUnitPrice.setText(String.valueOf(unitPrice));
                //lblOrderId.setText(String.valueOf(newSelection.getOrderID()));
//                try {
//                    cbmCustomerID.setValue(String.valueOf(CustomerRepo.getCustomerID(newSelection.getCustomerName())));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });

    }



    private void loadAllOrder() {
        ObservableList<OrderTm> obList = FXCollections.observableArrayList();
        try{
            List<OrderDTO> orderList = orderBO.getAll();
            for(OrderDTO o:orderList){
                OrderTm orderTm = new OrderTm(o.getDate(),
                        o.getIncenseType(),
                        o.getCustomerName(),
                        o.getQty(),
                        o.getTotalPrice(),
                        o.getOrderId()
                );
                obList.add(orderTm);
            }
            tblOrder.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactor() {
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colIncenseType.setCellValueFactory(new PropertyValueFactory<>("incenseType"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }



    public void btnAdd(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int orderId = Integer.parseInt(lblOrderId.getText());
        String cusId = txtCustomerID.getText();
        Date date = Date.valueOf(txtDate.getValue());
if(txtCustomerID.getText().isEmpty() || lblCustomerName.getText().isEmpty()|| txtQty.getText().isEmpty()||txtUnitPrice.getText().isEmpty()||txtTotalPrice.getText().isEmpty()||cbmIncenseType.getValue().isEmpty()||txtDate.getValue() == null){
    new Alert(Alert.AlertType.ERROR,"Please fill the detaill").show();
    return;
}
        var order = new Order(date,orderId,cusId);

        int qty = Integer.parseInt(txtQty.getText());
        List<OrderDetail> odList = new ArrayList<>();

        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            OrderTm tm = obList.get(i);
            //qty = tm.getQty();
            OrderDetail od = new OrderDetail(
                    incensePackageBO.getPackageId(tm.getIncenseType()),
                    orderId,
                    tm.getTotalPrice(),
                    tm.getQty()
            );

            odList.add(od);
        }
        var sale = new Sale(transationID,date,qty,orderId,"Pending");
        PlaceOrder po = new PlaceOrder(order,odList,sale);

        try {
            boolean isPlaced = placeOrderBO.placeOrder(po);
            if(isPlaced) {
                tblOrder.getItems().clear();
                getCurrentTransactionId();
                getCurrentOrderId();
                txtCustomerID.setDisable(false);
                loadAllOrder();
                clear();
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
    }
    public void btnReset(ActionEvent actionEvent){
        LocalDate localDate = LocalDate.now();
        txtDate.setValue(localDate);
        txtUnitPrice.setText("");
        txtQty.setText("");
        txtCustomerID.setText("");
        txtTotalPrice.setText("");
        loadAllOrder();
    }
public void clear(){
    LocalDate localDate = LocalDate.now();
    txtDate.setValue(localDate);
    txtUnitPrice.setText("");
    txtQty.setText("");
    txtCustomerID.setText("");
    txtTotalPrice.setText("");
}
    public void brnSearch(ActionEvent actionEvent) {
        String cuID = txtCustomerID.getText();
        try {
            List<OrderDTO> orderList = orderBO.searchById(cuID);
            tblOrder.getItems().clear();
            for (OrderDTO order : orderList) {
                OrderTm orderTm = new OrderTm(
                        order.getDate(),
                        order.getIncenseType(),
                        order.getCustomerName(),
                        order.getQty(),
                        order.getTotalPrice(),
                        order.getOrderId()
                );
                obList.add(orderTm);
            }
            tblOrder.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void dateOnAction(ActionEvent actionEvent){

    }
    private void getCurrentOrderId() {
        try {
            String currentId = orderBO.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
            /*String[] split = currentId.split("0");
            int idNum = Integer.parseInt(split[1]);
            return "0" + ++idNum;
        */
        }
        return "01";
    }
    private void getCurrentTransactionId() {
        try {
            String currentId = saleBO.getCurrentId();
            String nextTransationId = generateNextTransationId(currentId);
            transationID = nextTransationId;
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

//    private void getCustomerID(){
//        ObservableList<String> obList = FXCollections.observableArrayList();
//        try{
//            List<String> IDlist = CustomerRepo.getCustomerID();
//            for (String i:IDlist){
//                obList.add(i);
//            }
//            txtCustomerID.setText(obList.toString());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void getIncenseType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> incenseTypeList = incensePackageBO.getIncenseType();
            for (String i:incenseTypeList){
                obList.add(i);
            }
            cbmIncenseType.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setTotalPrice() {
        String qtyText = txtQty.getText();
        String unitPriceText = txtUnitPrice.getText();

        if (!qtyText.isEmpty() && !unitPriceText.isEmpty()) {
            try {
                int qty = Integer.parseInt(qtyText);
                double unitPrice = Double.parseDouble(unitPriceText);

                double totalPrice = qty * unitPrice;
                txtTotalPrice.setText(String.valueOf(totalPrice));
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        } else {
            txtTotalPrice.setText("");
        }
    }
    public void btnAddToCart(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String currentId = orderBO.getCurrentId();
        String orderID = generateNextOrderId(currentId);
        lblOrderId.setText(orderID);
        // String customerID = cbmCustomerID.getValue();
        String incenseType = cbmIncenseType.getValue();
        Date date = Date.valueOf(txtDate.getValue());
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double totalPrice = qty * unitPrice;
        JFXButton remove = new JFXButton("❌");
        remove.setCursor(Cursor.HAND);
        remove.setStyle("-fx-text-fill: red;");
        remove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int selectedIndex = tblOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                if (obList.isEmpty()) {
                    txtCustomerID.setDisable(false);
                }
                tblOrder.refresh();
            }
        });
        if (isValied()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                int colOrderId = (int) colOrderID.getCellData(i);
                String type = (String) colIncenseType.getCellData(i);
                String cName = (String) colName.getCellData(i);
                //String name = (String) colName.getCellData(i+1);
                String name = lblCustomerName.getText();
                if (orderID.equals(String.valueOf(colOrderId))) {
                    if (type.equals(incenseType) && name.equals(cName)) {
                        OrderTm tm = obList.get(i);
                        //       cbmCustomerID.setDisable(true);
                        qty += tm.getQty();
                        totalPrice = qty * unitPrice;
                        tm.setQty(qty);
                        tm.setTotalPrice(totalPrice);
                        tblOrder.refresh();
                        return;
                    } else {
                        new Alert(Alert.AlertType.WARNING, "one order can made by only one customer ! ");
                    }
                }
            }
            txtCustomerID.setDisable(true);
            String name = lblCustomerName.getText();
            OrderTm tm = new OrderTm(date, incenseType, name, qty, totalPrice, Integer.parseInt(orderID), remove);
            obList.add(tm);
            tblOrder.setItems(obList);
        }
    }
    public void txtUnitPrice(ActionEvent actionEvent) {
        txtQty.requestFocus();
        setTotalPrice();
    }

    public void txtQty(ActionEvent actionEvent) {
        setTotalPrice();
        txtDate.requestFocus();
    }

    public void btnUpdatePayemetDetail(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Sale_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Payment Form");
        stage.show();
    }

    public void btnAddIncenseType(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Package_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Package Form");
        stage.show();
    }

    public void btnAddCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Customer_form.fxml"));
        Parent load = fxmlLoader.load();
        RootNood.getChildren().clear();
        RootNood.getChildren().add(load);
    }

    public void CustomerIDAction(KeyEvent keyEvent) {
        try {
            String Name = customerBO.setCustomerName(txtCustomerID.getText());
            lblCustomerName.setText(Name);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void txtQtyActionRelease(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty);
    }

    public void txtUnitPriceActionRelease(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtUnitPrice);
    }
    public boolean isValied(){
        if(!Regex.setTextColor(lk.ijse.Util.TextField.PRICE,txtUnitPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.QTY,txtQty)) return false;
        return true;
    }
}
