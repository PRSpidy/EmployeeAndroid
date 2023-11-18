package com.example.empleados.ui.employees

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.empleados.R
import androidx.activity.viewModels
import com.example.empleados.data.Employee
import com.example.empleados.data.repository.remote.RemoteEmployeeDataSource
import com.example.empleados.databinding.ActivityMainBinding
import com.example.empleados.utils.Resource


class EmployeeActivity : ComponentActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter
    private val employeeRepository = RemoteEmployeeDataSource()
    private val viewModel: EmployeeViewModel by viewModels { EmployeeViewModelFactory(employeeRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeAdapter = EmployeeAdapter(::onEmployeesListClickItem)
        binding.employeesList.adapter = employeeAdapter

        viewModel.items.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        employeeAdapter.submitList(it.data)
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.buttonBuscar.setOnClickListener {
            if(!binding.employeeInputId.text.isNullOrEmpty()) {
                viewModel.employeeById( binding.employeeInputId.text.toString().toInt())
            }else {
                viewModel.updateEmployeeList()
            }
        }

        binding.buttonDelete.setOnClickListener {
            if(!binding.employeeInputId.text.isNullOrEmpty()) {
                viewModel.deleteEmployeeById(binding.employeeInputId.text.toString().toInt())
            }
        }
        binding.buttonCreate.setOnClickListener {
            if(
                !binding.employeeInputName.text.isNullOrEmpty() &&
                !binding.employeeInputPosition.text.isNullOrEmpty() &&
                !binding.employeeInputSalary.text.isNullOrEmpty()
                ) {
                    val employee = Employee(
                        1,
                        binding.employeeInputName.text.toString(),
                        binding.employeeInputPosition.text.toString(),
                        binding.employeeInputSalary.text.toString().toInt()
                    )
                    viewModel.createEmployeeById(employee)
            }
        }

        binding.buttonEdit.setOnClickListener{
            if(
                !binding.employeeInputId.text.isNullOrEmpty() &&
                !binding.employeeInputName.text.isNullOrEmpty() &&
                !binding.employeeInputPosition.text.isNullOrEmpty() &&
                !binding.employeeInputSalary.text.isNullOrEmpty()
            ) {
                val employee = Employee(
                    binding.employeeInputId.text.toString().toInt(),
                    binding.employeeInputName.text.toString(),
                    binding.employeeInputPosition.text.toString(),
                    binding.employeeInputSalary.text.toString().toInt()
                )
                viewModel.editEmployee(employee)
            }
        }

        viewModel.item.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val employeeList = listOfNotNull(resource.data)
                    employeeAdapter.submitList(employeeList)
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.delete.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.updateEmployeeList()
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.create.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.updateEmployeeList()
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.edit.observe(this, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.updateEmployeeList()
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                }

                Resource.Status.LOADING -> {
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}
private fun onEmployeesListClickItem(employee: Employee) {
    Log.i("PRUEBA1", "va2")
    Log.i("PRUEBA1", employee.name)
}


