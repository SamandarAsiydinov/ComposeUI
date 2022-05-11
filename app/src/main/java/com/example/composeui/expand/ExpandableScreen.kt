package com.example.composeui.expand

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeui.R
import com.example.composeui.model.SampleData
import com.example.composeui.ui.theme.Purple500
import com.example.composeui.util.Constants.ExpandAnimation
import com.example.composeui.viewmodel.ExpandableViewModel

@ExperimentalAnimationApi
@Composable
fun ExpandableScreen(viewModel: ExpandableViewModel) {
    val cards = viewModel.cards.collectAsState()
    val expandedCard = viewModel.expandedCardList.collectAsState()

    Scaffold {
        LazyColumn {
            itemsIndexed(cards.value) { _, card ->
                ExpandableCard(
                    card = card,
                    onCardArrowClick = { viewModel.onCardArrowClick(card.id) },
                    expanded = expandedCard.value.contains(card.id)
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    card: SampleData,
    onCardArrowClick: () -> Unit,
    expanded: Boolean
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(
        targetState = transitionState,
        label = "Transition"
    )
    val cardBgColor by transition.animateColor({
        tween(durationMillis = ExpandAnimation)
    }, label = "Background Color Transition") {
        if (expanded) Purple500 else Purple500
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = ExpandAnimation)
    }, label = "Padding Transition") {
        20.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = ExpandAnimation)
    }, label = "Card Elevation") {
        20.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(durationMillis = ExpandAnimation, easing = FastOutSlowInEasing)
    }, label = "Corner Transition") {
        15.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = ExpandAnimation, easing = FastOutSlowInEasing)
    }, label = "Corner Transition") {
        if (expanded) 0f else 180f
    }
    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        Text(
                            text = card.title,
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(0.15f)
                    ) {
                        IconButton(onClick = {
                            onCardArrowClick()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
                                contentDescription = "Expandable Icon",
                                modifier = Modifier.rotate(degrees = arrowRotationDegree)
                            )
                        }
                    }
                }
            }
        }
    }
}