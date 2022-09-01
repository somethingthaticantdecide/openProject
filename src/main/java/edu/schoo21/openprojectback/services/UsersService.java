package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.exceptions.NotFoundException;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User addNewUser(UserDto userDto) {
        return userRepository.save(new User(userDto.getLogin(), userDto.getPassword(), userDto.getName(),
                userDto.getPhoneNumber(), userDto.getMail(), userDto.getAddress(), userDto.getAvatar(), userDto.getRanking()));
    }

    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    public User updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(NotFoundException::new);
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setMail(userDto.getMail());
        user.setAddress(userDto.getAddress());
        user.setAvatar(userDto.getAvatar());
        user.setRanking(userDto.getRanking());
        return userRepository.saveAndFlush(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByName(String name) {
        return userRepository.findUserByName(name);
    }
}
