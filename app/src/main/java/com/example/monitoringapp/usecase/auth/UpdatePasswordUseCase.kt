package com.example.monitoringapp.usecase.auth

import com.example.monitoringapp.data.network.repository.AuthenticationRepository
import com.example.monitoringapp.data.network.request.UpdatePasswordRequest
import com.example.monitoringapp.data.network.response.ObjectResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class UpdatePasswordUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(updatePasswordRequest: UpdatePasswordRequest): OperationResult<ObjectResponse<String>> {
        return authenticationRepository.updatePassword(updatePasswordRequest)
    }
}