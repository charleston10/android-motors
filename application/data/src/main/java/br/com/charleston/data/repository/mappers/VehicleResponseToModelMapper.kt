package br.com.charleston.data.repository.mappers

import br.com.charleston.data.cloud.responses.VehicleResponse
import br.com.charleston.data.cloud.responses.VersionResponse
import br.com.charleston.data.repository.IMapper
import br.com.charleston.domain.model.VehicleModel
import br.com.charleston.domain.model.VersionModel

class VehicleResponseToModelMapper : IMapper<VehicleResponse, VehicleModel> {

    override fun transform(entity: VehicleResponse): VehicleModel {
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