package com.example.inventorymanager.domain.usecase

import com.example.inventorymanager.data.repository.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {

    suspend fun execute() = repository.getProducts()
}
