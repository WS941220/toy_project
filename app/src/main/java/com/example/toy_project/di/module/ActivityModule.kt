package com.example.toy_project.di.module

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.addeditmemo.AddEditMemoActivity
import com.example.toy_project.ui.addeditmemo.AddEditMemoModule
import com.example.toy_project.ui.gallery.GalleryActivity
import com.example.toy_project.ui.gallery.GalleryModule
import com.example.toy_project.ui.login.LoginActivity
import com.example.toy_project.ui.login.LoginModule
import com.example.toy_project.ui.main.MainActivity
import com.example.toy_project.ui.main.MainModule
import com.example.toy_project.ui.memo.MemoActivity
import com.example.toy_project.ui.memo.MemoModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MemoModule::class])
    abstract fun MemoActivity(): MemoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AddEditMemoModule::class])
    abstract fun addEditMemoActivity(): AddEditMemoActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [GalleryModule::class])
    abstract fun galleryActivity(): GalleryActivity

}