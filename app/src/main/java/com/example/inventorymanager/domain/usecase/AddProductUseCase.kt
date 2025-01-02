package com.example.inventorymanager.domain.usecase

import com.example.inventorymanager.data.model.Product
import com.example.inventorymanager.data.repository.ProductRepository

class AddProductUseCase(private val repository: ProductRepository) {

    suspend fun execute(product: Product) = repository.addProduct(product)
}
