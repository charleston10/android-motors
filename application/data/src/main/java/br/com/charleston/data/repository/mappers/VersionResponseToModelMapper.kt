package br.com.charleston.data.repository.mappers

import br.com.charleston.data.cloud.responses.VersionResponse
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.VersionModel

class VersionResponseToModelMapper : IMapper<VersionResponse, VersionModel> {

    override fun transform(entity: VersionResponse): VersionModel {
        return VersionModel(
            modelId = entity.modelId,
            id = entity.id,
            name = entity.name
        )
    }
}