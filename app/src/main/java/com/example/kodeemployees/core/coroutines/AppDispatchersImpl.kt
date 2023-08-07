package com.example.kodeemployees.core.coroutines

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class AppDispatchersImpl @Inject constructor() : AppDispatchers {
    override val ui = Dispatchers.Main
    override val io = Dispatchers.IO
    override val default = Dispatchers.Default
}