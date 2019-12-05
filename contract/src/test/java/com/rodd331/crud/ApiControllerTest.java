package com.rodd331.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodd331.crud.impl.UserFacade;
import com.rodd331.crud.impl.mapper.UserMapper;
import com.rodd331.crud.impl.model.UserModel;
import com.rodd331.crud.impl.repository.UserEntity;
import com.rodd331.crud.impl.repository.UserRepository;
import com.rodd331.crud.impl.service.PersistenceService;
import com.rodd331.crud.impl.service.ValidationService;
import com.rodd331.crud.v1.ApiController;
import com.rodd331.crud.v1.UserContractFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static com.rodd331.crud.stubs.UserEntityStub.generationUserEntity;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ApiController.class)
@ContextConfiguration(classes = {ApiController.class,
        UserContractFacade.class,
        UserFacade.class,
        ValidationService.class,
        PersistenceService.class})
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersistenceService persistenceService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private UserContractFacade userContractFacade;
    @Autowired
    private UserFacade userFacade;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void findById_ReturnCode_OK() throws Exception {
        given(userRepository.findById("someid")).willReturn(java.util.Optional.of(generationUserEntity()));
        this.mockMvc.perform(get("/v1/crud/user/someid"))
                .andExpect(status().isOk());
    }

    @Test
    public void allUsers_ReturnCode_OK() throws Exception {
        List<UserEntity> teste = new ArrayList<UserEntity>();
        teste.add(generationUserEntity());
        given(userRepository.findAll()).willReturn(teste);
        this.mockMvc.perform(get("/v1/crud/user")).andExpect(status().isOk());
    }

  /*  @Test
    public void createUser_ReturnCode_Created() throws Exception {

        given(userRepository.save(UserEntity.builder().id("123456").userName("jonas").email("jacare@live.com").userPassword("123456")
                .build())).willReturn(UserEntity
                .builder()
                .id("someid")
                .userName("jonas")
                .email("jacare@live.com")
                .userPassword("123456")
                .build());

        this.mockMvc.perform(post("/v1/crud/user").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(UserEntity
                                .builder()
                                .id("123456")
                                .userName("jonas")
                                .email("jacare@live.com")
                                .userPassword("123456")
                                .build()))).andExpect(status().isCreated());
    }*/

    @Test
    public void deleteFindById_ReturnCode_Ok() throws Exception {
        UserEntity userEntityExample = new UserEntity("someid", "teste", "test@mail.com", "123456");
        given(userRepository.findById("someid")).willReturn(java.util.Optional.of(userEntityExample));
        this.mockMvc.perform(delete("/v1/crud/user/someid")).andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_ReturnCode_OK() throws Exception {
        UserModel userModelExample = new UserModel("someid", "teste", "test@hotmail.com", "123456");
        UserEntity userEntityExample = new UserEntity("someid", "teste", "test@hotmail.com", "123456");
        given(userRepository.findById("someid")).willReturn(java.util.Optional.of(userEntityExample));
        given(userRepository.save(UserMapper.mapToEntity(userModelExample))).willReturn(userEntityExample);
        this.mockMvc.perform(put("/v1/crud/user/someid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper()
                        .writeValueAsString(UserEntity.builder().userName("teste").email("test@hotmail.com").userPassword("123456").build()))).andExpect(status().isOk());
    }
}