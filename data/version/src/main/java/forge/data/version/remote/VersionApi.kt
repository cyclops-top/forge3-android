package forge.data.version.remote

import forge.model.Page
import forge.model.Version
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


internal interface VersionApi {
    @GET("project/{project}/versions")
    suspend fun page(
        @Path("project") project:Long,
        @Query("page") index: Int,
        @Query("size") size: Int,
    ): Response<Page<Version>>

}
