package neoflex.chulkov.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.chulkov.dto.ClientDto;
import neoflex.chulkov.entity.Client;
import neoflex.chulkov.exceptiom.ClientNotFoundException;
import neoflex.chulkov.mapper.ClientMapper;
import neoflex.chulkov.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    public ClientDto create(ClientDto request) {
        log.trace(">> clientService.create()");
        log.debug("Входные параметр: clientDto: {}", request);
        return clientMapper.toDto(
            clientRepository.save(clientMapper.toEntity(request))
        );
    }

    public ClientDto update(ClientDto request) {
        log.trace(">> clientService.update()");
        log.debug("Входные параметр: clientDto: {}", request);
        return clientMapper.toDto(
            clientRepository.save(clientMapper.toEntity(request))
        );
    }

    public void delete(Long clientId) {
        log.trace(">> clientService.delete()");
        log.debug("Входные параметры: id: {}", clientId);
        clientRepository.deleteById(clientId);
    }

    public ClientDto getById(Long clientId) {
        log.trace(">> clientService.getById()");
        log.debug("Входные параметры: id: {}", clientId);
        return clientMapper.toDto(
            clientRepository.findById(clientId).orElseThrow(
                () -> {
                    log.warn("Клиент с id = {} не найден", clientId);
                    return new ClientNotFoundException("Клиент с данным id не найден");
                }
            )
        );
    }

    public List<ClientDto> getAll() {
        log.trace(">> clientService.getAll()");
        return clientRepository.findAll().stream()
            .map(clientMapper::toDto)
            .toList();
    }
    @Transactional
    public void generateClients(Integer n) {
        log.trace(">> clientService.generateClients()");
        log.debug("Входные параметры: n: {}", n);
        for (int i = 0; i < n; i++) {
            Client client = generateClient(i);

            log.debug("Создан клиент = {}", client);//Честно боюсь в цикле логировать))))

            clientRepository.save(client);
        }
        log.info("В базу сохранилось {} сгенерированных клиентов", n);
        log.trace("<< clientService.generateClients()");
    }

    private Client generateClient(Integer n) {
        Client client = new Client();
        client.setAge(n);
        client.setFirstName("genName");
        client.setLastName("genName");
        client.setAddress("genAddress");
        return client;
    }
}
