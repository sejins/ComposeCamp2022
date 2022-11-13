package com.codelabs.basicstatecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.basicstatecodelab.ui.theme.BasicStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}

@Composable
private fun WellnessScreen(
    modifier: Modifier = Modifier,
    viewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        WaterCounter()

        WellnessTaskList(
            modifier = Modifier.padding(top = 16.dp),
            list = viewModel.tasks,
            onCheckedChange = viewModel::changeTaskChecked,
            onCloseTask = viewModel::removeTask,
        )
    }
}

@Composable
private fun RememberTestWaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember {
            mutableStateOf(0)
        }
        // 리멤버가 사라지는 조건
        // 리멤버가 호줄되는 코드 위치가 리컴포지션 동안 다시 호출되지 않은 경우 -> 컴포지션에 저장된 리멤버 객체 삭제
        // 리멤버를 할 필요가 없다고 생각하고 객체를 컴포지션에서 삭제
        if (count > 0) {
            var showTask by remember { // 리멤버가 사라지는 조건?
                mutableStateOf(true)
            }
            if (showTask) {
//                WellnessTaskItem(taskName = stringResource(id = R.string.have_you_walk_15_min)) {
//                    showTask = false
//                }
            }
            Text(text = stringResource(R.string.water_counter_count_guide, count))
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Button(
                enabled = count < 10,
                onClick = { count += 1 }
            ) {
                Text(text = stringResource(id = R.string.add_one))
            }
            Button(
                modifier = Modifier.padding(start = 4.dp),
                onClick = { count = 0 }) {
                Text(text = stringResource(id = R.string.clear_water_count))
            }
        }
    }
}

@Composable
private fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text(text = stringResource(R.string.water_counter_count_guide, count))
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text(text = stringResource(id = R.string.add_one))
        }
    }
}

@Composable
private fun WellnessTaskList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCheckedChange: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked ->
                    onCheckedChange(task, checked)
                }
            ) {
                onCloseTask(task)
            }
        }
    }
}

//@Composable
//private fun WellnessTaskItem(
//    modifier: Modifier = Modifier,
//    taskName: String,
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    onClose: () -> Unit
//) {
////    var checkedState by rememberSaveable {
////        mutableStateOf(false)
////    }
//
//    WellnessTaskItem(
//        modifier = modifier,
//        checked = checked,
//        onCheckedChange = onCheckedChange,
//        taskName = taskName,
//        onClose = onClose
//    )
//}

@Composable
private fun WellnessTaskItem(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    taskName: String,
    onClose: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.close)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessScreenPreview() {
    BasicStateCodelabTheme {
        WellnessScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessTaskItemPreview() {
    BasicStateCodelabTheme {
        WellnessTaskItem(taskName = "This is a task", checked = false, onCheckedChange = {}) {}
    }
}
