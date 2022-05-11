package com.example.composeui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeui.model.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpandableViewModel : ViewModel() {

    private val _cards = MutableStateFlow(listOf<SampleData>())
    val cards: StateFlow<List<SampleData>> get() = _cards

    private val _expandedCardList = MutableStateFlow(listOf<Int>())
    val expandedCardList: StateFlow<List<Int>> get() = _expandedCardList

    init {
        getSampleData()
    }

    private fun getSampleData() {
        viewModelScope.launch(Dispatchers.Default) {
            val sampleList = arrayListOf<SampleData>()
            repeat(10) {
                sampleList += SampleData(
                    it,
                    "Jetpack $it"
                )
            }
            _cards.emit(sampleList)
        }
    }

    fun onCardArrowClick(cardId: Int) {
        _expandedCardList.value = _expandedCardList.value.toMutableList().also { list ->
            if (list.contains(cardId)) {
                list.remove(cardId)
            } else {
                list.add(cardId)
            }
        }
    }
}