package top.cyclops.forge.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NetworkStringConverter: Converter<ResponseBody, String> {
    override fun convert(value: ResponseBody): String {
        return value.string()
    }

    companion object{
        fun factory(): Converter.Factory{
            val converter = NetworkStringConverter()
            return object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    if(type == String::class.java){
                        return converter
                    }
                    return null
                }

            }
        }

    }
}