package com.alexandre.inventoryservice.service.product;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.service.CrudService;

import java.util.UUID;

public interface ProductService extends CrudService<ProductDTO, UUID> {
}
