package com.mybootapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybootapp.main.model.Supplier;
import com.mybootapp.main.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public Supplier postSupplier(@RequestBody Supplier supplier) {
        return supplierService.insert(supplier);
    }

    @GetMapping("/getAll")
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/getOne/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable("supplierId") int supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        if (supplier == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        return ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/delete/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable("supplierId") int supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        if (supplier == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        supplierService.delete(supplier);
        return ResponseEntity.ok("Supplier deleted successfully");
    }

    @PutMapping("/update/{supplierId}")
    public ResponseEntity<?> updateSupplier(@PathVariable("supplierId") int supplierId, @RequestBody Supplier updatedSupplier) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        if (supplier == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");

        /* Update supplier details */
        supplier.setName(updatedSupplier.getName());
        supplier.setCity(updatedSupplier.getCity());

        /* Save updated supplier */
        supplier = supplierService.insert(supplier);

        return ResponseEntity.status(HttpStatus.OK).body(supplier);
    }
}
