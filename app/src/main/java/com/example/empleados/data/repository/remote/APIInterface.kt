package com.example.empleados.data.repository.remote

import com.example.empleados.data.Employee
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIInterface {

    @GET("empleados")
    suspend fun getEmployees(): Response<List<Employee>>

    @GET("empleado/{id}")
    suspend fun getEmployeeById(@Path("id") id: Int): Response<Employee>

    @DELETE("empleados/{id}")
    suspend fun deleteEmployeeById(@Path("id") id: Int): Response<Int>

    @POST("empleados")
    suspend fun createEmployee(@Body employee: Employee): Response<Int>

    @PUT("empleados/{id}")
    suspend fun editEmployee(@Path("id") id: Int, @Body employee: Employee): Response<Int>
}