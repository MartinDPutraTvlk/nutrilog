package com.martinezdputra.nutrilog.onboarding_presentation.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinezdputra.nutrilog.core.domain.model.Gender
import com.martinezdputra.nutrilog.core.domain.preferences.Preferences
import com.martinezdputra.nutrilog.core.navigation.Route
import com.martinezdputra.nutrilog.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {

    private val _selectedGender = MutableStateFlow<Gender>(Gender.Male)
    val selectedGender = _selectedGender.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGenderClick(gender: Gender) {
        _selectedGender.value = gender
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGender(selectedGender.value)
            _uiEvent.send(UiEvent.Navigate(Route.AGE))
        }
    }
}
