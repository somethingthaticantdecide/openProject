package edu.schoo21.openprojectback.services;

import edu.schoo21.openprojectback.exceptions.NotFoundException;
import edu.schoo21.openprojectback.models.Feedback;
import edu.schoo21.openprojectback.models.User;
import edu.schoo21.openprojectback.models.dto.UserDto;
import edu.schoo21.openprojectback.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public User updateUser(UserDto userDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        if (userDto.getLogin() != null) user.setLogin(userDto.getLogin());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getPhoneNumber() != null) user.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getMail() != null) user.setMail(userDto.getMail());
        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if (userDto.getAvatar() != null) user.setAvatar(userDto.getAvatar());
        if (userDto.getRanking() != null) user.setRanking(userDto.getRanking());
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

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public void countRanking(User user) {
        List<Integer> rankings = new ArrayList<>();
        for (Feedback f : user.getFeedbacks()) {
            rankings.add(f.getRating());
        }
        long sum = 0;
        long count = 0;
        for (Integer r : rankings) {
            sum += r;
            count++;
        }
        user.setRanking(count > 0 ? (float) ((double) sum / count) : (float) 0);
    }

}
