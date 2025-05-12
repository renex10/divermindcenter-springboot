package divermindcenterterapia.divermindcenter.entity;

/**
 * Roles disponibles para los usuarios del sistema
 */
public enum UserRole {
    THERAPIST,      // Terapeuta (puede ser independiente o de centro)
    CENTER_ADMIN,   // Administrador de un centro
    SYSTEM_ADMIN    // Administrador del sistema completo
}