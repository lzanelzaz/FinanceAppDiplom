package project.e_buyankina.common.network

class ServerException(
    val resultCode: Int,
    override val message: String,
) : Exception(message)