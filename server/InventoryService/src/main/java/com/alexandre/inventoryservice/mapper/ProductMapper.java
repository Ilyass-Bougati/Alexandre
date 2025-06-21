package com.alexandre.inventoryservice.mapper;

import com.alexandre.inventoryservice.dto.ProductDTO;
import com.alexandre.inventoryservice.entity.Product;
import com.alexandre.orderservice.dto.OrderDTO;
import com.alexandre.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper extends MapperInterface<Product, ProductDTO> {
}
