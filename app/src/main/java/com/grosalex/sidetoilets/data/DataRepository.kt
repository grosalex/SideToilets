package com.grosalex.sidetoilets.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grosalex.sidetoilets.db.RecordsDb
import com.grosalex.sidetoilets.model.Record
import kotlinx.coroutines.launch

class DataRepository(context: Context) : ViewModel() {

    var db: RecordsDb = RecordsDb.getInstance(context)
    var records: List<Record>? = null
        set(value) {
            field = value
            value?.let {
                viewModelScope.launch {
                    db.recordDao().insertRecords(it)
                }
            }
        }
    init {
        viewModelScope.launch {
            records = db.recordDao().findAll()
        }
    }
}