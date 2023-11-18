package com.example.empleados.ui.employees

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.empleados.data.Employee
import com.example.empleados.data.repository.CommonEmployeeRepository
import com.example.empleados.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel(
    private val employeeRepository: CommonEmployeeRepository
) : ViewModel()  {

    private val _items = MutableLiveData<Resource<List<Employee>>>()
    val items : LiveData<Resource<List<Employee>>> get() = _items
    private val _item = MutableLiveData<Resource<Employee>>()
    val item : LiveData<Resource<Employee>> get() = _item
    private val _delete = MutableLiveData<Resource<Int>>()
    val delete : LiveData<Resource<Int>> get() = _delete
    private val _create = MutableLiveData<Resource<Int>>()
    val create : LiveData<Resource<Int>> get() = _create
    private val _edit = MutableLiveData<Resource<Int>>()
    val edit : LiveData<Resource<Int>> get() = _edit

    init {
        updateEmployeeList();
    }

    fun updateEmployeeList() {
        viewModelScope.launch {
            val repoResponse = getEmployeesFromRepository()
            _items.value = repoResponse
        }
    }

    fun employeeById(id: Int) {
        viewModelScope.launch {
            val repoResponse = getEmployeeByIdFromRepository(id)
            _item.value = repoResponse
        }
    }

    fun deleteEmployeeById(id: Int) {
        viewModelScope.launch {
            val repoResponse = deleteEmployeeByIdFromRepository(id)
            _delete.value = repoResponse
        }
    }

    fun createEmployeeById(employee: Employee) {
        viewModelScope.launch {
            val repoResponse = createEmployeeFromRepository(employee)
            _create.value = repoResponse
        }
    }

    fun editEmployee(employee: Employee) {
        viewModelScope.launch {
            val repoResponse = editEmployeeByIdFromRepository(employee)
            _edit.value = repoResponse
        }
    }

    suspend fun getEmployeesFromRepository () : Resource<List<Employee>> {
        return withContext(Dispatchers.IO){
            employeeRepository.getEmployees()
        }
    }

    suspend fun getEmployeeByIdFromRepository(id: Int): Resource<Employee> {
        return withContext(Dispatchers.IO){
            employeeRepository.getEmployeeById(id)
        }
    }

    suspend fun deleteEmployeeByIdFromRepository(id: Int): Resource<Int> {
        return withContext(Dispatchers.IO){
            employeeRepository.deleteEmployee(id)
        }
    }

    suspend fun createEmployeeFromRepository(employee: Employee): Resource<Int> {
        return withContext(Dispatchers.IO){
            employeeRepository.createEmployee(employee)
        }
    }

    suspend fun editEmployeeByIdFromRepository (employee: Employee): Resource<Int> {
        return withContext(Dispatchers.IO) {
            employeeRepository.editEmployee(employee)
        }
    }

}




class EmployeeViewModelFactory(
    private val employeeRepository: CommonEmployeeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EmployeeViewModel(employeeRepository) as T
    }
}