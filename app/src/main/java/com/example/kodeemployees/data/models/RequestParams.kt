package com.example.kodeemployees.data.models

import com.example.kodeemployees.presentation.models.DepartmentType

data class RequestParams(
    var department: DepartmentType = DepartmentType.ALL,
    var searchText: String = "",
    var sourceType: DataSourceType = DataSourceType.SERVER,
    var sortedBy: SortUsersType = SortUsersType.ALPHABET
)
