package com.example.kodeemployees.domain.usecases

import com.example.kodeemployees.domain.models.Department
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor() {
    operator fun invoke(): List<Department> {
        return Department.values().toList()
    }
}