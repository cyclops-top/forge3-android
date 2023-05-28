package forge.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.math.BigDecimal

class NetworkBigDecimalConverter : Converter<ResponseBody, BigDecimal> {
    override fun convert(value: ResponseBody): BigDecimal? {
        return value.string().toBigDecimalOrNull()
    }

    companion object{
        fun factory(): Converter.Factory{
            val converter = NetworkBigDecimalConverter()
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if(type == BigDecimal::class.java){
                        return converter
                    }
                    return null
                }

            }
        }

    }
}