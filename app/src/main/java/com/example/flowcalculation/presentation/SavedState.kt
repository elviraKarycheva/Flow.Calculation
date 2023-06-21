package com.example.flowcalculation.presentation

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle


object SavedState {
    const val KEY_SAVED_STATE = "KEY_SAVED_STATE"
}

inline fun <reified S : Parcelable> SavedStateHandle.provideSavedState(
    key: String = SavedState.KEY_SAVED_STATE,
    crossinline provider: () -> S,
): S? {
    setSavedStateProvider(key) {
        bundleOf(key to provider())
    }
    val bundle = get<Bundle>(key)
    return when {
        SDK_INT >= 33 -> bundle?.getParcelable(key, S::class.java)
        else -> @Suppress("DEPRECATION") bundle?.getParcelable(key) as? S
    }
}