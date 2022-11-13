package com.codelabs.basicstatecodelab

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class WellnessViewModel : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<WellnessTask>
        get() = _tasks

    fun removeTask(task: WellnessTask) {
        _tasks.remove(task)
    }

    fun changeTaskChecked(item: WellnessTask, checked: Boolean) {
        _tasks.find { it.id == item.id }?.let { item ->
            item.checked = checked
        }
    }

    private fun getWellnessTasks() = List(30) { i -> WellnessTask(i, "Task # $i") }
}

//data class WellnessTask(val id: Int, val label: String, var checked: Boolean = false)
// 복잡성을 줄이기 위해서 데이터 클래스가 아닌 이 방법을 선택한 듯
class WellnessTask(
    val id: Int,
    val label: String,
    initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}
