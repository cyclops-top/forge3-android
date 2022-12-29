package top.cyclops.forge.project

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import top.cyclops.forge.model.Page
import top.cyclops.forge.model.Project
import top.cyclops.forge.network.ApiCreator
import javax.inject.Singleton


internal interface ProjectApi {
    @GET("projects")
    suspend fun page(
        @Query("page") index: Int,
        @Query("size") size: Int
    ): Response<Page<Project>>
}

