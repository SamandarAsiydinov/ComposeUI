package com.example.composeui.spin_calendar

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeui.ui.theme.Purple500
import com.example.composeui.viewmodel.SpinViewModel

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SpinnerView() {
    val sampleList =
        mutableListOf("Java", "Kotlin", "Android", "Dart", "Flutter", "Swift", "Objective-C", "iOS")
    var sampleName: String by remember { mutableStateOf(sampleList[0]) }
    var expanded by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(
        targetState = transitionState,
        label = "tran"
    )
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 300)
    }, label = "rotation") {
        if (expanded) 180f else 0f
    }
    val context = LocalContext.current
    val viewModel: SpinViewModel = viewModel()
    val dateTime = viewModel.time.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Spinner",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = sampleName,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(30.dp)
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Arrow",
                        modifier = Modifier.rotate(arrowRotationDegree)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        sampleList.forEach { data ->
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    sampleName = data
                                }
                            ) {
                                Text(text = data)
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Calender Date & Time Picker",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    onClick = {
                        viewModel.selectedDataTime(context)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Purple500)
                        .padding(5.dp)
                ) {
                    Text(text = "Select Date", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = dateTime.value ?: "No Time Set")
            }
        }
    }
}