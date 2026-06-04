package br.com.coretech.coretech_api.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Produto extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaId(Long id);

    List<Produto> findByAtivoTrue();
}
