package lk.ijse.bo;

import lk.ijse.bo.custom.IncensePackageBO;
import lk.ijse.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER,SUPPLIER,MATERIAL,PLACEMATERIAL,SALE,ORDER,IncensePackage,ORDERDETAIL,PLACEORDER,PACKAGE,BATCH,DASHBOARD,USERS

    }

    //Object creation logic for BO objects
    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case MATERIAL:
                return new MaterialBOImpl();
            case PLACEMATERIAL:
                return new PlaceMaterialBOImpl();
            case SALE:
                return new SaleBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case IncensePackage:
                return new IncensePackageBOImpl();
            case ORDERDETAIL:
                return new OrderDetailBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case PACKAGE:
                return new PackageBOImpl();
            case BATCH:
                return new BatchBOImpl();
            case DASHBOARD:
                return new DashbordBOImpl();
            case USERS:
                return new UserBOImpl();
            default:
                return null;
        }
    }

}