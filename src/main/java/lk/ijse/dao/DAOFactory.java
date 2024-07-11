package lk.ijse.dao;


import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,SUPPLIER,MATERIAL,SUPPLIERDETAIL,SALE,ORDER,IncensePackage,ORDERDETAIL,PACKAGE,BATCH,Dashbord,USERS
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case MATERIAL:
                return new MaterialDAOImpl();
            case SUPPLIERDETAIL:
                return new SupplierDetailDAOImpl();
            case SALE:
                return new SaleDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case IncensePackage:
                return new IncensePackageDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case PACKAGE:
                return new PackageDAOImpl();
            case BATCH:
                return new BatchDAOImpl();
            case Dashbord:
                return new DashbordDAOImpl();
            case USERS:
                return new UserDAOImpl();
            default:
                return null;
        }
    }


}