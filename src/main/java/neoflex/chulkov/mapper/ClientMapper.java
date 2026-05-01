package neoflex.chulkov.mapper;

import neoflex.chulkov.dto.ClientDto;
import neoflex.chulkov.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientDto dto);
    ClientDto toDto(Client client);
}
