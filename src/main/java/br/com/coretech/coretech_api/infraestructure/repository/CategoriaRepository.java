package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Override
    Optional<Categoria> findById(Long id);

    boolean existsBySlug(String slug);
    boolean existsByNome(String nome);

    Optional<Categoria> findBySlug(String slug);
    Optional<Categoria> findAllById(Long id);
    List<Categoria> findAll();
    @Transactional
    void deleteById(Long id);
}
