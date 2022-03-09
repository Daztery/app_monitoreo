package com.example.monitoringapp.usecase.monitoring

import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.network.repository.MonitoringRepository
import com.example.monitoringapp.data.network.response.CollectionResponse
import com.example.monitoringapp.util.OperationResult
import javax.inject.Inject

class GetSelfPlansUseCase @Inject constructor(private val monitoringRepository: MonitoringRepository) {
    suspend operator fun invoke(
        active: Boolean
    ): OperationResult<CollectionResponse<Plan>> {
        return monitoringRepository.getSelfPlans(active)
    }
}