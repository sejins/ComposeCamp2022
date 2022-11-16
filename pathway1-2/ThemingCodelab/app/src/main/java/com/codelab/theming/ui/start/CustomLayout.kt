package com.codelab.theming.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 컴포지션을 기반으로 UI 컴포넌트를 배치하는 작업(layout)은 다음 3단계로 진행된다.
 * 모든 하위 요소 측정
 * 자체 크기 측정(leaf 노드의 컴포저블 순으로 크기를 측정하고 리턴한다.)
 * 하위 요소 배치
 */

@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = { measurables, constraints ->

            // 모든 하위 컴포넌트에 대한 측정 및 크기를 알 수 있게 되고 최종적으로 루트 컴포넌트(현재 커스텀 레이아웃)에 대한 크기를 알 수 있게 된다.
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            val height = placeables.sumOf { it.height }
            val width = placeables.maxOf { it.width }
            layout(width, height) {
                var yPosition = 0

                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )
}


@Preview
@Composable
fun MyBasicColumnPreview() {
    MyBasicColumn(
        modifier = Modifier.background(color = MaterialTheme.colors.secondary).size(100.dp)
    ) {
        Text(text = "Text1")
        Text(text = "Text2")
        Text(text = "Text3")
    }
}

@Preview
@Composable
fun ColumnPreview() {
    Column(
        modifier = Modifier.background(color = MaterialTheme.colors.secondary).size(100.dp)
    ) {
        Text(text = "Text1")
        Text(text = "Text2")
        Text(text = "Text3")
    }
}
