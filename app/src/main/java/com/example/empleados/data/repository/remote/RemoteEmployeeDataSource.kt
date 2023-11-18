package com.example.empleados.data.repository.remote

import com.example.empleados.data.Employee
import com.example.empleados.data.repository.CommonEmployeeRepository
import com.example.empleados.utils.Resource

class RemoteEmployeeDataSource: BaseDataSource(), CommonEmployeeRepository {
    override suspend fun getEmployees() = getResult {
        RetrofitClient.apiInterface.getEmployees()
    }

    override suspend fun getEmployeeById(id: Int) = getResult {
        RetrofitClient.apiInterface.getEmployeeById(id)
    }

    override suspend fun deleteEmployee(id: Int) = getResult {
        RetrofitClient.apiInterface.deleteEmployeeById(id)
    }

    override suspend fun createEmployee(employee: Employee) = getResult {
        RetrofitClient.apiInterface.createEmployee(employee)
    }

    override suspend fun editEmployee(employee: Employee) = getResult {
        RetrofitClient.apiInterface.editEmployee(employee.id, employee)
    }


}