package org.blockchain.converters;

public interface Converter<DTO, Entity> {

    Entity fromDTOtoEntity(DTO dto);

    DTO fromEntityToDTO(Entity entity);
}
