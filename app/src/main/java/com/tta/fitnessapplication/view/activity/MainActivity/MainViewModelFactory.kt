package com.tta.fitnessapplication.view.activity.MainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tta.fitnessapplication.data.repository.RepositoryApi

class MainViewModelFactory(
    private val repositoryApi: RepositoryApi
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repositoryApi) as T
    }

}