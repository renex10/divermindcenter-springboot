package divermindcenterterapia.divermindcenter.repository;

import divermindcenterterapia.divermindcenter.entity.Boy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * El Almacén de Niños - Donde guardamos y buscamos información de los niños
 * Funciona como un bibliotecario que sabe exactamente dónde está cada registro
 */
public interface BoyRepository extends JpaRepository<Boy, Long> {
    /*
    Por qué existe:
    - Es el encargado de hablar directamente con la base de datos
    - Proporciona operaciones básicas como guardar, buscar y eliminar
    - Spring implementa automáticamente estos métodos mágicamente

    Funcionalidades incluidas gratis:
    - save(): Guarda un nuevo niño o actualiza uno existente
    - findAll(): Obtiene todos los niños registrados
    - findById(): Busca un niño por su número de identificación
    - deleteById(): Elimina un niño usando su ID
    */
}