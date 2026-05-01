package neoflex.chulkov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import neoflex.chulkov.dto.ClientDto;
import neoflex.chulkov.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnCreated() throws Exception {
        ClientDto dto = new ClientDto("Ivan", "Ivanov", 30, "Moscow");
        when(clientService.create(any(ClientDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("Ivan"))
            .andExpect(jsonPath("$.lastName").value("Ivanov"));
    }

    @Test
    void getById_ShouldReturnOk_WhenClientExists() throws Exception {
        ClientDto dto = new ClientDto("Ivan", "Ivanov", 30, "Moscow");
        when(clientService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/clients/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Ivan"));
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        List<ClientDto> clients = List.of(new ClientDto("Ivan", "Ivanov", 30, "Moscow"));
        when(clientService.getAll()).thenReturn(clients);

        mockMvc.perform(get("/api/clients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].firstName").value("Ivan"));
    }

    @Test
    void update_ShouldReturnOk() throws Exception {
        ClientDto dto = new ClientDto("Anna", "Petrova", 25, "SPb");
        when(clientService.update(any(ClientDto.class))).thenReturn(dto);

        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Anna"));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/clients/10"))
            .andExpect(status().isNoContent());

        verify(clientService, times(1)).delete(10L);
    }

    @Test
    void generateNClients_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/clients/generate/5"))
            .andExpect(status().isOk());

        verify(clientService, times(1)).generateClients(5);
    }
}