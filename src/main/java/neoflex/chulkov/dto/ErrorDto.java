package neoflex.chulkov.dto;

public record ErrorDto(
    Integer status,
    String message
) {
}
