package br.com.charleston.data.repository.mappers

import br.com.charleston.data.cloud.responses.ModelResponse
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.Model

class ModelResponseToModelMapper : IMapper<ModelResponse, Model> {

    override fun transform(entity: ModelResponse): Model {
        return Model(
            makeId =entity.makeId,
            id = entity.id,
            name = entity.name
        )
    }
}