package forge.data.project.remote

import forge.model.Page
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


internal interface ProjectApi {
    @GET("projects")
    suspend fun page(
        @Query("page") index: Int,
        @Query("size") size: Int,
    ): Response<Page<ProjectRemote>>

    @GET("project/{id}")
    suspend fun detail(@Path("id") id:Long):Response<ProjectRemote>

    @POST("project/{id}/collect")
    suspend fun collect(@Path("id") id: Long): Response<Boolean>

    @DELETE("project/{id}/collect")
    suspend fun cancelCollect(@Path("id") id: Long): Response<Boolean>
}

