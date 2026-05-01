package neoflex.chulkov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import neoflex.chulkov.dto.ClientDto;
import neoflex.chulkov.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/clients")
public class ClientController {
    private final ClientService clientService;
    @Operation(
        summary = "добавление нового клиента",
        description = "сохраняет в базу данных клиента"
    )
    @ApiResponse(responseCode = "201", description = "Клиент успешно сохранен")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClientDto create(
        @RequestBody ClientDto request
    ) {
        return clientService.create(request);
    }

    @Operation(
        summary = "обновление клиента",
        description = "обновляет клиента"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Клиент успешно обновлен"),
        @ApiResponse(responseCode = "404", description = "Клиент с данным id не найден"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{clientId}")
    public ClientDto update(
        @RequestBody ClientDto request,
        @PathVariable("clientId") Long clientID
    ) {
        return clientService.update(request);
    }

    @Operation(
        summary = "удаляет клиента",
        description = "удаляет клиента по его id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "клиент успешно удален"),
        @ApiResponse(responseCode = "404", description = "клиент с данным id не найден")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{clientId}")
    public void delete(
        @Parameter(description = "ID клиента", example = "10") @PathVariable("clientId") Long clientId
    ) {
        clientService.delete(clientId);
    }

    @Operation(
        summary = "найти клиента",
        description = "возвращает клиент по его id"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "клиент успешно найдено"),
        @ApiResponse(responseCode = "404", description = "клиент с данным id не найдено")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{clientId}")
    public ClientDto getById(
        @Parameter(description = "ID клиента", example = "10")
        @PathVariable("clientId") Long clientId
    ) {
        return clientService.getById(clientId);
    }
    @Operation(
        summary = "вывести всех клиентов",
        description = "находит всех клиентов"
    )
    @ApiResponse(responseCode = "200", description = "Клиенты успешно найдены")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll();
    }
    @Operation(
        summary = "Сгенерировать n клиентов",
        description = "Генерирует n клиентов"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "клиенты успешно сгенерированы"),
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/generate/{n:\\d+}")
    public void generateNClients(
        @Parameter(description = "количество сгенерированных клиентов", example = "10")
        @PathVariable("n") Integer n
    ) {
        clientService.generateClients(n);
    }
}
