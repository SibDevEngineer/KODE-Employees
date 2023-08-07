package com.example.kodeemployees.data.models

import com.example.kodeemployees.domain.models.Department

data class RequestParams(
    var department: Department = Department.ALL,
    var searchText: String = "",
    var sourceType: DataSourceType = DataSourceType.SERVER,
    var sortedBy: SortUsersType = SortUsersType.ALPHABET
)
