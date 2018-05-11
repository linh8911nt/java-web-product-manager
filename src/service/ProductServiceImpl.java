package service;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String DB_URL = "jdbc:mysql://localhost:3306/productviewer" + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "12345678";


    public ProductServiceImpl() {
    }

    public Connection connection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<Product> findAll() throws ClassNotFoundException, SQLException {
//        Class.forName(JDBC_DRIVER);
//        Connection connection;
//        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement statement = null;
        Connection connection = connection();

        String sql;
        sql = "SELECT id, code, name FROM products";

        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);

        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setCode(resultSet.getString("code"));
            product.setName(resultSet.getString("name"));

            products.add(product);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return products;
    }

    @Override
    public void save(Product product) throws ClassNotFoundException, SQLException {
        Connection connection = connection();
        PreparedStatement statement = null;

        String sql;
        sql = "INSERT INTO products(code, name, price, category_id) VALUES (?, ?, ?, ?)";

        statement = connection.prepareStatement(sql);
        statement.setString(1, product.getCode());
        statement.setString(2, product.getName());
        statement.setFloat(3, product.getPrice());
        statement.setInt(4, product.getCategory_id());

        int resultSet = statement.executeUpdate(sql);
        System.out.println("it's work?" + resultSet);

        statement.close();
        connection.close();
    }

    @Override
    public void remove(int id) throws ClassNotFoundException, SQLException {
        Connection connection = connection();
        PreparedStatement statement = null;

        String sql;
        sql = "DELETE FROM products WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int resultSet = statement.executeUpdate(sql);
        System.out.println("it's work" + resultSet);

        statement.close();
        connection.close();
    }

    @Override
    public Product findById(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement statement = null;
        Connection connection = connection();

        String sql;
        sql = "SELECT id, code, name, price, category_id  FROM products WHERE id = ?";

        statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery(sql);

        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setCode(resultSet.getString("code"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getFloat("price"));
        product.setCategory_id(resultSet.getInt("category_id"));

        resultSet.close();
        statement.close();
        connection.close();

        return product;
    }

    @Override
    public void update(int id, Product product) throws ClassNotFoundException, SQLException {

    }
}
