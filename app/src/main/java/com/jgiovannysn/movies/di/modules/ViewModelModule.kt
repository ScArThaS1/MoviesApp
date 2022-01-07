package com.jgiovannysn.movies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgiovannysn.movies.di.viewModel.ViewModelFactory
import com.jgiovannysn.movies.di.viewModel.ViewModelKey
import com.jgiovannysn.movies.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author JGiovannySN 05/01/22.
 * @version 1.0
 * @since 1.0
 */
@Module
abstract class ViewModelModule {

    /**
     * Bind [mainViewModel] for injection.
     */
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class )
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    /**
     * Bind [ViewModelFactory] for injection.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):
            ViewModelProvider.Factory
}