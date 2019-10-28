package br.com.charleston.data.repository.mappers

import br.com.charleston.data.cloud.responses.MakeResponse
import br.com.charleston.data.local.entity.MakeEntity
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.MakeModel

class MakeResponseToEntityMapper : IMapper<MakeResponse, MakeEntity> {

    override fun transform(entity: MakeResponse): MakeEntity {
        return MakeEntity(
            id = entity.id,
            name = entity.name
        )
    }
}