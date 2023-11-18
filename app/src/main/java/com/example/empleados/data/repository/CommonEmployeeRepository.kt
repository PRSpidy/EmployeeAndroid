package com.example.empleados.data.repository

import com.example.empleados.data.Employee
import com.example.empleados.utils.Resource

interface CommonEmployeeRepository {

    suspend fun getEmployees() : Resource<List<Employee>>
    suspend fun getEmployeeById(id: Int) : Resource<Employee>
    suspend fun deleteEmployee(id: Int) : Resource<Int>
    suspend fun createEmployee(employee: Employee) : Resource<Int>
    suspend fun editEmployee(employee: Employee) : Resource<Int>
}