package ru.gb.dao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gb.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


//@Component
@RequiredArgsConstructor
public class NamedParameterJdbcTemplateProductDao implements ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Iterable<Product> findAll() {
        String sql = "SELECT * FROM product";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> Product.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .date(rs.getDate("manufacture_date").toLocalDate())
                .cost(rs.getBigDecimal("cost"))
                .build());
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = :productId";
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("productId", id);
        return namedParameterJdbcTemplate.query(sql, namedParameters, new ProductExtractor());
    }

    @Override
    public String findNameById(Long id) {

        String sql = "SELECT title FROM product WHERE id = :productId";
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("productId", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
    }

    @Override
    public void insert(Product product) {

    }

    private static class ProductMapper implements RowMapper<Product>{

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Product.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .date(rs.getDate("manufacture_date").toLocalDate())
                    .cost(rs.getBigDecimal("cost"))
                    .build();
        }
    }

    private static class ProductExtractor implements ResultSetExtractor<Product> {

        @Override
        public Product extractData(ResultSet rs) throws SQLException, DataAccessException {
            Product product = null;

            while (rs.next()) {
                product = Product.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .date(rs.getDate("manufacture_date").toLocalDate())
                        .cost(rs.getBigDecimal("cost"))
                        .build();
            }

            return product;
        }
    }
}
