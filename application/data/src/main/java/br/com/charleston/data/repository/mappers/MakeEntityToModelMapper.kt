package br.com.charleston.data.repository.mappers

import br.com.charleston.data.local.entity.MakeEntity
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.MakeModel

class MakeEntityToModelMapper : IMapper<MakeEntity, MakeModel> {

    override fun transform(entity: MakeEntity): MakeModel {
        return MakeModel(
            id = entity.id,
            name = entity.name
        )
    }
}