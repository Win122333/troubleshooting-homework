package neoflex.chulkov.service;

import neoflex.chulkov.dto.ClientDto;
import neoflex.chulkov.entity.Client;
import neoflex.chulkov.exceptiom.ClientNotFoundException;
import neoflex.chulkov.mapper.ClientMapper;
import neoflex.chulkov.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientService clientService;

    @Test
    void getById_ShouldReturnClientDto_WhenClientExists() {
        Long clientId = 1L;
        Client client = new Client(clientId, "Ivan", "Ivanov", 30, "Moscow");
        ClientDto expectedDto = new ClientDto("Ivan", "Ivanov", 30, "Moscow");

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(expectedDto);

        ClientDto result = clientService.getById(clientId);

        assertNotNull(result);
        assertEquals("Ivan", result.firstName());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void getById_ShouldThrowClientNotFoundException_WhenIdWrong() {
        Long clientId = 2L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getById(clientId));
        verify(clientRepository, times(1)).findById(clientId);
    }
    @Test
    void getAll_ShouldReturnListOfClients() {
        Client client = new Client(1L, "Ivan", "Ivanov", 30, "Moscow");
        ClientDto expectedDto = new ClientDto("Ivan", "Ivanov", 30, "Moscow");
        List<Client> clients = List.of(client, client, client);

        when(clientRepository.findAll()).thenReturn(clients);
        when(clientMapper.toDto(any(Client.class))).thenReturn(expectedDto);

        List<ClientDto> actualList = clientService.getAll();

        assertNotNull(actualList);
        assertEquals(3, actualList.size());
        assertEquals(expectedDto, actualList.get(0));
        verify(clientRepository, times(1)).findAll();
        verify(clientMapper, times(3)).toDto(any(Client.class));
    }
    @Test
    void create_ShouldSaveAndReturnDto() {
        ClientDto requestDto = new ClientDto("Petr", "Petrov", 25, "SPb");
        Client entity = new Client(null, "Petr", "Petrov", 25, "SPb");
        Client savedEntity = new Client(1L, "Petr", "Petrov", 25, "SPb");

        when(clientMapper.toEntity(requestDto)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(savedEntity);
        when(clientMapper.toDto(savedEntity)).thenReturn(requestDto);

        ClientDto result = clientService.create(requestDto);

        assertNotNull(result);
        assertEquals("Petr", result.firstName());
        verify(clientRepository, times(1)).save(entity);
    }
    @Test
    void update_ShouldUpdateAndReturnDto() {
        ClientDto requestDto = new ClientDto("Anna", "Ivanova", 22, "Kazan");
        Client entity = new Client(1L, "Anna", "Ivanova", 22, "Kazan");

        when(clientMapper.toEntity(requestDto)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(entity);
        when(clientMapper.toDto(entity)).thenReturn(requestDto);

        ClientDto result = clientService.update(requestDto);

        assertNotNull(result);
        assertEquals("Anna", result.firstName());
        verify(clientRepository, times(1)).save(entity);
    }
    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        Long clientId = 10L;

        clientService.delete(clientId);

        verify(clientRepository, times(1)).deleteById(clientId);
    }
    @Test
    void generateClients_ShouldSaveNClients_WhenNIsPositive() {
        int n = 5;

        clientService.generateClients(n);

        verify(clientRepository, times(n)).save(any(Client.class));
    }

    @Test
    void generateClients_ShouldDoNothing_WhenNIsZeroOrLess() {
        clientService.generateClients(0);
        clientService.generateClients(-5);

        verify(clientRepository, never()).save(any(Client.class));
    }
}