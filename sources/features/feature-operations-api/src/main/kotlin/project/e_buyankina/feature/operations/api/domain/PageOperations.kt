package project.e_buyankina.feature.operations.api.domain

data class PageOperations(
    val page: Int,
    val operations: List<Operation>,
    val hasNext: Boolean,
)