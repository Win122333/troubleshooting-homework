package neoflex.chulkov.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "дто для клиента")
public record ClientDto(
    @Schema(description = "Имя клиента", example = "John")
    String firstName,

    @Schema(description = "Фамилия клиента", example = "Johnovich")
    String lastName,
    @Schema(description = "Возраст клиента", example = "12")
    Integer age,
    @Schema(description = "Адрес клиента", example = "Voronezh")
    String address
) {
}
