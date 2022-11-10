package com.codelab.basics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }
}


@Composable
private fun MyApp(names: List<String> = List(20) { "$it" }) {
    Surface(color = MaterialTheme.colors.background) {
        // 상태를 공유해야하는 최소 레이어까지 호이스팅
        var shouldShowOnBoarding by rememberSaveable {
            mutableStateOf(true)
        }

        Greetings(names)

        AnimatedVisibility(visible = shouldShowOnBoarding, enter = fadeIn(), exit = fadeOut()) {
            OnBoardingScreen {
                shouldShowOnBoarding = false // 호이스팅 된 상태를 파라미터로 작접 전달하기 보다는 람다를 전달
            }
        }
    }
}

@Composable
private fun Greetings(names: List<String> = List(100) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        items(items = names) {
            Greeting(name = it)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        GreetingContent(name = name)
    }
}

@Composable
private fun GreetingContent(name: String) {
    
    var expended by rememberSaveable { // lazy list에서는 remember를 사용하는 경우 스크롤이 화면을 이탈하는 경우 상태 보장이 안됨; remember는 컴포지션에 상태를 저장하기 때문!
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize( // dp는 음수가 되는 경우 에러 방생하는 이슈가 있었음
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            IconButton(onClick = { expended = expended.not() }) {
                Icon(
                    imageVector = if (expended) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expended) stringResource(id = R.string.show_less) else stringResource(id = R.string.show_more)
                )
            }
        }

        // 생성시에만 애니메이션
        AnimatedVisibility(visible = expended, enter = fadeIn(), exit = ExitTransition.None) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy. ").repeat(4)
            )
        }
    }
}

@Composable
private fun OnBoardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.on_boarding_title))
            Button(
                modifier = Modifier.padding(top = 24.dp),
                onClick = onContinueClicked
            ) {
                Text(text = stringResource(id = R.string.continue_))
            }
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    BasicsCodelabTheme {
        OnBoardingScreen { }
    }
}

@Preview
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greetings()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun OnBoardingScreenPreviewDark() {
    BasicsCodelabTheme {
        OnBoardingScreen { }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GreetingsPreviewDark() {
    BasicsCodelabTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greetings()
        }
    }
}
