package project.e_buyankina.common_network

class ServerException(
    val resultCode: Int,
    override val message: String,
) : Exception(message)