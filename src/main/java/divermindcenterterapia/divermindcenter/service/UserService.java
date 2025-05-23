package divermindcenterterapia.divermindcenter.service;

import divermindcenterterapia.divermindcenter.dto.UserPageResponseDTO;
import divermindcenterterapia.divermindcenter.dto.UserProfileResponseDTO;
import divermindcenterterapia.divermindcenter.dto.UserRegistrationDTO;
import divermindcenterterapia.divermindcenter.dto.UserResponseDTO;
import divermindcenterterapia.divermindcenter.exception.*;
import jakarta.transaction.Transactional;

public interface UserService {
    UserResponseDTO registerUser(UserRegistrationDTO registrationDTO)
            throws InvalidRutException, DuplicateEmailException,
            ResourceNotFoundException, PasswordMismatchException;

    UserResponseDTO getUserById(Long id) throws ResourceNotFoundException;

    UserPageResponseDTO getPagedUsers(int pageNumber, int pageSize);

    UserProfileResponseDTO getUserProfileWithDetails(Long userId) throws ResourceNotFoundException;

    @Transactional
    UserResponseDTO updateActiveStatus(Long id, boolean active) throws ResourceNotFoundException;
}