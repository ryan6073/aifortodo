/*
 * Copyright 2020 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intern.aifortodo.di

import android.app.Application
import com.intern.aifortodo.data.localdatasource.IProjectLocalDataSource
import com.intern.aifortodo.data.localdatasource.ITaskLocalDataSource
import com.intern.aifortodo.data.repository.ProjectRepository
import com.intern.aifortodo.data.repository.TaskRepository
import com.intern.aifortodo.data.repository.UserPreferencesRepository
import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.ITaskRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskLocalDataSource: ITaskLocalDataSource): ITaskRepository =
        TaskRepository(taskLocalDataSource)

    @Provides
    @Singleton
    fun provideProjectRepository(projectLocalDataSource: IProjectLocalDataSource): IProjectRepository =
        ProjectRepository(projectLocalDataSource)

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(application: Application): IUserPreferencesRepository =
        UserPreferencesRepository(application)
}
