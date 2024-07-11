package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.*;
import lk.ijse.dto.IncensePackageDTO;
import lk.ijse.dto.SaleDTO;
import lk.ijse.entity.IncensePackage;
import lk.ijse.entity.Sale;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MiniDashboardFormController {
    SaleBO saleBO = (SaleBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALE);
    DashbordBO dashbordBO = (DashbordBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DASHBOARD);
    IncensePackageBO incensePackageBO = (IncensePackageBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.IncensePackage);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public Label lblNetWorth;
    public Label lblSupplierCount;
    public javafx.scene.chart.PieChart PieChart;
    public javafx.scene.chart.PieChart PieChart1;

    public Label lblMopnthlyProfit;

    public void initialize(){
        setCustomerCount();
        setSupplierName();
        setOrderCount();
        setMonthlyProfit();
        setNetWorth();
        populatePieChart();
        populatePieChart1();
    }

    private void setMonthlyProfit() {
        try {
            double monthlyProfit = dashbordBO.getCurrentMonthProfit();
            lblMopnthlyProfit.setText(String.format("%.2f", monthlyProfit));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void populatePieChart1() {
        try {
            List<SaleDTO> sales = saleBO.getAllSalesByPaymentStatus();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (SaleDTO sale : sales) {
                pieChartData.add(new PieChart.Data(sale.getPType(), sale.getCount()));
            }

            PieChart1.setData(pieChartData);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void populatePieChart() {
        try {
            List<IncensePackageDTO> packages = incensePackageBO.getAll();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (IncensePackageDTO pack : packages) {
                pieChartData.add(new PieChart.Data(pack.getDescription(), pack.getQty()));
            }

            PieChart.setData(pieChartData);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setSupplierName() {
        try {
            int totalSuppliers =supplierBO.getTotalSupplierCount();
           lblSupplierCount.setText(String.valueOf(totalSuppliers));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNetWorth() {
        try {
            double netWorth = orderBO.calculateNetWorth();
            lblNetWorth.setText(String.format(String.valueOf(netWorth)));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setOrderCount() {
        try {
            int orderCount = orderBO.getOrderCount();
             lblOrderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCustomerCount() {
        try {
            int customerCount = customerBO.getCustomerCount();
            lblCustomerCount.setText(String.valueOf(customerCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
   public void btnPackage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Package_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Package Form");
        stage.show();
    }
}
