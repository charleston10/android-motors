package br.com.charleston.data.repository.mappers

import br.com.charleston.data.cloud.responses.MakeResponse
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.MakeModel

class MakeResponseToModelMapper : IMapper<MakeResponse, MakeModel> {

    override fun transform(entity: MakeResponse): MakeModel {
        return MakeModel(
            id = entity.id,
            name = entity.name
        )
    }
}