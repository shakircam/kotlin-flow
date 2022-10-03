package com.shakircam.flowtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel :ViewModel() {

    /** Life-cycle awareness.It trigger when data are changes.
     * In the same state it's emit same value again.
     * If state change for configuration change like screen rotation then it emit same value automatically.
     * It's don't need initial value when initialize.
     */
    private val _liveData = MutableLiveData("Init live data")
    val liveData:LiveData<String> = _liveData

    /** Called hot flow. That means it keep emitting even if there are no collector.
     * In the same state it's not emit same value again.
     * If state change for configuration change like screen rotation then it emit same value automatically.
     * Using alternative for live data.
     * It's need initial value when initialize
     */
    private val _stateFlow = MutableStateFlow("Init State flow")
    val stateFlow = _stateFlow.asStateFlow()

    /** Called cold flow. That means it would not emit anything if there is no collector.
     * In the same state it's emit same value again.
     * It's don't need initial value when initialize.
     */
    private val _shareFlow = MutableSharedFlow<String>()
    val shareFlow = _shareFlow.asSharedFlow()

    fun triggerLiveData(){
        _liveData.value = "live Data"
    }

    fun triggerStateFlowData(){
        _stateFlow.value = "State Flow"
    }

    fun triggerShareFlowData(){
        viewModelScope.launch {
            _shareFlow.emit("share flow")
        }
    }

    fun triggerFlow():Flow<String>{
        return flow {
            repeat(5){
                emit("value $it")
                delay(1000L)
            }
        }
    }
}