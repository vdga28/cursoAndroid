package com.example.demoandroid.koin

import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.persistence.databases.CharacterRepository
import com.example.demoandroid.viewmodels.CharactersViewModel
import com.example.demoandroid.viewmodels.ComicsViewModel
import com.example.demoandroid.viewmodels.FavoriteCharactersViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient(get()) }
    single { CharacterRepository(androidContext()) }
    viewModel { CharactersViewModel(get(), get()) }
    viewModel { ComicsViewModel(get()) }
    viewModel { FavoriteCharactersViewModel(get()) }
}