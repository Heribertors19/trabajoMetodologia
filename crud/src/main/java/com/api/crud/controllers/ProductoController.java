package com.api.crud.controllers;

import com.api.crud.models.ProductoModel;
import com.api.crud.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos desde la vista 'vista_productos'
    @CrossOrigin(origins = "*")  // Permitir todos los orígenes
    @GetMapping("/getall")
    public ArrayList<ProductoModel> getProductos() {
        return this.productoService.getProductos();
    }

    // Obtener un producto por su ID
    @CrossOrigin(origins = "*")  // Permitir todos los orígenes
    @GetMapping(path = "/{id}")
    public Optional<ProductoModel> getProductoById(@PathVariable("id") Long id) {
        return this.productoService.getById(id);
    }

    // Editar un producto, llamando al procedimiento almacenado para modificar el precio
    @CrossOrigin(origins = "*")  // Permitir todos los orígenes
    @PutMapping(path = "/edit/{id}")
    public ProductoModel updateProductoById(@RequestBody ProductoModel request, @PathVariable("id") Long id) {
        return this.productoService.updateById(request, id);
    }
}
