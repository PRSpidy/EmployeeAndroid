package com.example.empleados.ui.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.empleados.data.Employee
import com.example.empleados.databinding.ItemEmployeeBinding

class EmployeeAdapter (
    private val onClickListener: (Employee) -> Unit
) : ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder>(EmployeeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
        holder.itemView.setOnClickListener {
            onClickListener(employee)
        }

    }

    inner class EmployeeViewHolder(private val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: Employee) {
            binding.textViewTitle.text = employee.name
            binding.textViewSubtitle1.text = employee.position
            binding.textViewSubtitle2.text = employee.salary.toString()
        }
    }

    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {

        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return (oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.salary == newItem.salary && oldItem.position == newItem.position)
        }

    }
}