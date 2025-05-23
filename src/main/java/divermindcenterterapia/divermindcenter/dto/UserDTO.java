package divermindcenterterapia.divermindcenter.dto;

import divermindcenterterapia.divermindcenter.entity.*;
// UserDTO.java
import divermindcenterterapia.divermindcenter.entity.PracticeType;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {


    private Long id;
    private String firstName;
    private String email;
    private String password;
    private UserRole role;
    private Dni dni;
    private Address address;
    private List<Phone> phones;
    private PracticeType practiceType;
    private RehabilitationCenter rehabilitationCenter;
    private LocalDateTime createdAt;
}
