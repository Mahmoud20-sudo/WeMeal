package com.wemeal.domain.usecase.onboarding

import com.wemeal.domain.repository.onboarding.GetCountriesRepository

class GetCountriesUseCase(private val getCountriesRepository: GetCountriesRepository) {
    suspend fun execute(page: Int) = getCountriesRepository.getCountries(page = page)
}