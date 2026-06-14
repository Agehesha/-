package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.dto.UserRequestDto;
import ua.opnu.labwork2.entity.Booking;
import ua.opnu.labwork2.entity.User;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.repository.BookingRepository;
import ua.opnu.labwork2.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public UserService(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public User createUser(UserRequestDto dto) {
        User user = new User();
        fillUser(user, dto);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return findUserOrThrow(id);
    }

    public User updateUser(Long id, UserRequestDto dto) {
        User user = findUserOrThrow(id);
        fillUser(user, dto);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(Long id) {
        findUserOrThrow(id);
        return bookingRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public List<User> searchUsers(String query) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                query, query, query
        );
    }

    public User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private void fillUser(User user, UserRequestDto dto) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
    }
}
