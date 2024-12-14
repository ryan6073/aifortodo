

package com.intern.aifortodo.di

import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.ITaskRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import com.intern.aifortodo.domain.usecase.DeleteProjectUseCase
import com.intern.aifortodo.domain.usecase.DeleteTaskUseCase
import com.intern.aifortodo.domain.usecase.GetProjectSelectedIdUseCase
import com.intern.aifortodo.domain.usecase.GetProjectSelectedUseCase
import com.intern.aifortodo.domain.usecase.GetProjectsUseCase
import com.intern.aifortodo.domain.usecase.GetTaskUseCase
import com.intern.aifortodo.domain.usecase.InsertProjectUseCase
import com.intern.aifortodo.domain.usecase.InsertTaskUseCase
import com.intern.aifortodo.domain.usecase.SetAppThemePreferenceUseCase
import com.intern.aifortodo.domain.usecase.SetProjectSelectedUseCase
import com.intern.aifortodo.domain.usecase.SetTaskDoingUseCase
import com.intern.aifortodo.domain.usecase.SetTaskDoneUseCase
import com.intern.aifortodo.domain.usecase.UpdateProjectUseCase
import com.intern.aifortodo.domain.usecase.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetProjectsUseCase(
        projectRepository: IProjectRepository
    ) = GetProjectsUseCase(projectRepository)

    @Provides
    @ViewModelScoped
    fun provideGetTaskUseCase(
        taskRepository: ITaskRepository
    ) = GetTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideInsertTaskUseCase(
        userPreferencesRepository: IUserPreferencesRepository,
        taskRepository: ITaskRepository
    ) = InsertTaskUseCase(userPreferencesRepository, taskRepository)

    @Provides
    @ViewModelScoped
    fun provideDeleteTaskUseCase(
        taskRepository: ITaskRepository
    ) = DeleteTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetTaskDoingUseCase(
        taskRepository: ITaskRepository
    ) = SetTaskDoingUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetTaskDoneUseCase(
        taskRepository: ITaskRepository
    ) = SetTaskDoneUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateTaskUseCase(
        taskRepository: ITaskRepository
    ) = UpdateTaskUseCase(taskRepository)

    @Provides
    @ViewModelScoped
    fun provideSetProjectSelectedUseCase(
        userPreferencesRepository: IUserPreferencesRepository
    ) = SetProjectSelectedUseCase(userPreferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetProjectIdSelectedUseCase(
        userPreferencesRepository: IUserPreferencesRepository
    ) = GetProjectSelectedIdUseCase(userPreferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideGetProjectSelectedUseCase(
        userPreferencesRepository: IUserPreferencesRepository,
        projectRepository: IProjectRepository
    ) = GetProjectSelectedUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideInsertProjectUseCase(
        userPreferencesRepository: IUserPreferencesRepository,
        projectRepository: IProjectRepository
    ) = InsertProjectUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideDeleteProjectUseCase(
        userPreferencesRepository: IUserPreferencesRepository,
        projectRepository: IProjectRepository
    ) = DeleteProjectUseCase(userPreferencesRepository, projectRepository)

    @Provides
    @ViewModelScoped
    fun provideUpdateProjectUseCase(
        projectRepository: IProjectRepository
    ) = UpdateProjectUseCase(projectRepository)

    @Provides
    @ViewModelScoped
    fun provideSetAppThemePreferenceUseCase(
        userPreferencesRepository: IUserPreferencesRepository
    ) = SetAppThemePreferenceUseCase(userPreferencesRepository)
}
