package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Override
    Optional<Categoria> findById(Long id);
}
