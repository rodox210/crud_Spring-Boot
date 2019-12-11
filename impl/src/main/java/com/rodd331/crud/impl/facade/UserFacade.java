package com.rodd331.crud.impl.facade;

import com.rodd331.crud.impl.model.UserModel;
import com.rodd331.crud.impl.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rodd331.crud.impl.mapper.UserMapper.mapToEntity;
import static com.rodd331.crud.impl.mapper.UserMapper.mapToModel;

@Component
@AllArgsConstructor
public class UserFacade {

    private UserService userService;


    public UserModel create(UserModel user) {

        userService.checkForResgistredExistenceInDataBaseName(user);
        userService.checkForResgistredExistenceInDataBaseEmail(user);
        return mapToModel(userService.create(mapToEntity(user)));
    }

    public List<UserModel> allUsers() {
        userService.validationEmptyList();
        return userService.listAll();
    }

    public UserModel findById(String id) {
        return mapToModel(userService.findById(id));
    }

    public UserModel update(UserModel user, String id) {
        userService.validatorId(id);
        user.setId(id);
        return mapToModel(userService.update(mapToEntity(user)));
    }

    public void delete(String id) {
        userService.validatorId(id);
        userService.delete(id);
    }
}