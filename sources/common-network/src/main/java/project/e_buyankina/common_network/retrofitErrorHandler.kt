package project.e_buyankina.common_network

import org.json.JSONObject
import retrofit2.Response

fun <T> retrofitErrorHandler(res: Response<T>): T {
    if (res.isSuccessful) {
        return res.body()!!
    } else {
        val errorObject = res.errorBody()?.string().orEmpty().run { JSONObject(this) }
        throw ServerException(
            resultCode = errorObject.getInt("code"),
            message = errorObject.getString("error"),
        )
    }
}