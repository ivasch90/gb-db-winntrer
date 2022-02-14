package ru.gb.dao.product;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gb.entity.Product;


//@Component
@RequiredArgsConstructor
public class JdbcTemplateProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public String findNameById(Long id) {
        return jdbcTemplate.queryForObject("SELECT title FROM product WHERE id = ?", String.class, id);
    }

    @Override
    public void insert(Product product) {

    }
}
