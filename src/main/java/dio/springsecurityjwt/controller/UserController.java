package dio.springsecurityjwt.controller;

import dio.springsecurityjwt.dto.UserDto;
import dio.springsecurityjwt.handler.user.UserException;
import dio.springsecurityjwt.model.User;
import dio.springsecurityjwt.repository.UserRepository;
import dio.springsecurityjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    private UserDto userDto;

//    @GetMapping
//    public ResponseEntity<List<UserDto>> findAllUsers(){
//        List<User> listUserForDatabase = repository.findAll();
//        List<UserDto> listUserDto = new ArrayList<>();
//
//        userDto = new UserDto();
//        for (User user : listUserForDatabase){
//            userDto.setId(user.getId());
//            userDto.setName(user.getName());
//            userDto.setUsername(user.getUsername());
//            userDto.setRoles(user.getRoles());
//            listUserDto.add(userDto);
//        }
//        return ResponseEntity.ok(listUserDto);
//    }
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(){
        List<User> listUserRoleUsers = repository.findUsersWithUserRole();
        List<UserDto> listUserDto = new ArrayList<>();

        userDto = new UserDto();
        listUserRoleUsers.forEach(User -> {
            userDto.setId(User.getId());
            userDto.setName(User.getName());
            userDto.setUsername(User.getUsername());
            userDto.setRoles(User.getRoles());
            listUserDto.add(userDto);
        });

        return ResponseEntity.ok(listUserDto);

    }

    @PostMapping
    public void postUser(@RequestBody User user){
        User validadeUser = repository.findByUsername(user.getUsername());
        if(validadeUser != null){
            throw new UserException("Já existe um usuario cadastrado com o username: " + user.getUsername());
        }
        service.createUser(user);
    }
}