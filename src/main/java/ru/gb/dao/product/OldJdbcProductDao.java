package ru.gb.dao.product;

import ru.gb.entity.Product;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class OldJdbcProductDao implements ProductDao {

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/gb_shop?user=geek&password=geek");
    }

    private void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Product> findAll() {
        Connection connection = null;
        Set<Product> productSet = new HashSet<>();

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PRODUCT");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                final Product product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .cost(resultSet.getBigDecimal("cost"))
                        .title(resultSet.getString("title"))
                        .date(resultSet.getDate("manufacture_date").toLocalDate())
                        .build();

            productSet.add(product);

            }

            preparedStatement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return productSet;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Product product) {

    }
}
