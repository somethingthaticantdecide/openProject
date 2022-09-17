package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.exceptions.NotFoundException;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements UserDetailsService {
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

    public User findByLogin(String name) {
        return userRepository.findUserByLogin(name);
    }

    public User addNewUser(UserDto userDto) {
        User user = userRepository.findUserByLogin(userDto.getLogin());
        if (user == null) {
            user = new User(userDto);
            userRepository.save(user);
        }
        return user;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
