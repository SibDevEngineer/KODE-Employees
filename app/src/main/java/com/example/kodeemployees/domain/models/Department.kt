package com.example.kodeemployees.domain.models

import androidx.annotation.StringRes
import com.example.kodeemployees.R

/** Класс, перечисляющий типы департаментов */
enum class Department(@StringRes val title: Int) {
    ALL(R.string.department_title_all),
    ANDROID(R.string.department_title_android),
    IOS(R.string.department_title_ios),
    DESIGN(R.string.department_title_design),
    MANAGEMENT(R.string.department_title_management),
    QA(R.string.department_title_qa),
    BACK_OFFICE(R.string.department_title_back_office),
    FRONTEND(R.string.department_title_frontend),
    HR(R.string.department_title_hr),
    PR(R.string.department_title_pr),
    BACKEND(R.string.department_title_backend),
    SUPPORT(R.string.department_title_support),
    ANALYTICS(R.string.department_title_analytics)
}