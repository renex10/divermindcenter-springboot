package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.UserRegistrationDTO;
import divermindcenterterapia.divermindcenter.dto.UserResponseDTO;
import divermindcenterterapia.divermindcenter.exception.*;

public interface UserService {
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO)
            throws InvalidRutException, DuplicateEmailException,
            ResourceNotFoundException, PasswordMismatchException;

    UserResponseDTO getUserById(Long id) throws ResourceNotFoundException;
}