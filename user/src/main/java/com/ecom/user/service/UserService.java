package com.ecom.user.service;

import com.ecom.user.dto.AddressDTO;
import com.ecom.user.dto.UserRequest;
import com.ecom.user.dto.UserResponse;
import com.ecom.user.models.Address;
import com.ecom.user.models.User;
import com.ecom.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapUserToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUserById(long id) {
        return userRepository.findById(id).map(this::mapUserToUserResponse);
    }

    //    public Optional<User> fetchUserByName(String name){
//        return userRepository.findByName
//    }
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        return mapUserToUserResponse(userRepository.save(user));
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress()!=null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }

    public boolean updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id).map(existingUser -> {
            updateUserFromRequest(existingUser, userRequest);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setPhone(user.getPhone());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDTO);
        }
        return userResponse;
    }
}
