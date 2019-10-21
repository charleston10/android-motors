package br.com.charleston.data.repository.mappers

import br.com.charleston.data.local.entity.VehicleEntity
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.VehicleModel

class VehicleEntityToModelMapper : IMapper<VehicleEntity, VehicleModel> {

    override fun transform(entity: VehicleEntity): VehicleModel {
        return VehicleModel(
            id = entity.id,
            price = entity.price,
            model = entity.model,
            color = entity.color,
            version = entity.version,
            image = entity.image,
            km = entity.km,
            make = entity.make,
            yearFab = entity.yearFab,
            yearModel = entity.yearModel
        )
    }
}