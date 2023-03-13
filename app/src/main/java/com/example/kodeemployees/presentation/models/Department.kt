package com.example.kodeemployees.presentation.models

/** Класс, описывающий департамент работников */
data class Department(
    val id: Int,
    val departmentType: DepartmentType
)

/** Класс, перечисляющий типы департаментов */
enum class DepartmentType(val title: String) {
    ALL("Все"), ANDROID("Android"), IOS("iOS"), DESIGN("Дизайн"),
    MANAGEMENT("Менеджмент"), QA("QA"), BACK_OFFICE("Бэк-офис"),
    FRONTEND("Frontend"), HR("HR"), PR("PR"),
    BACKEND("Backend"), SUPPORT("Техподдержка"), ANALYTICS("Аналитика")
}

