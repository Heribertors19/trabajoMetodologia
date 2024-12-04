package com.api.crud.service;

import com.api.crud.models.ProductoModel;
import com.api.crud.repositories.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    IProductoRepository productoRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener productos desde la vista 'vista_productos'
    public ArrayList<ProductoModel> getProductos() {
        String sql = "SELECT nombre, precio_venta, stock_actual FROM vista_productos";
        List<ProductoModel> productos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductoModel.class));
        return new ArrayList<>(productos);
    }

    // Obtener un producto por su ID
    public Optional<ProductoModel> getById(Long id) {
        return productoRepository.findById(id);
    }

    // Actualizar el precio de un producto mediante el procedimiento almacenado
    public ProductoModel updateById(ProductoModel request, Long id) {
        String sql = "{call modificar_precio_producto(?, ?)}";  // Llamada al procedimiento almacenado
        jdbcTemplate.update(sql, id, request.getPrecioVenta());

        // Recuperar el producto actualizado
        Optional<ProductoModel> productoOpt = productoRepository.findById(id);
        if (productoOpt.isPresent()) {
            ProductoModel product = productoOpt.get();
            product.setPrecioVenta(request.getPrecioVenta());
            return product;
        }
        return null;  // Si el producto no se encuentra
    }
}
