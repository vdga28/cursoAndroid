package com.example.demoandroid.koin

import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.viewmodels.CharactersViewModel
import com.example.demoandroid.viewmodels.ComicsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient(get()) }
    viewModel { CharactersViewModel(get()) }
    viewModel { ComicsViewModel(get()) }
}