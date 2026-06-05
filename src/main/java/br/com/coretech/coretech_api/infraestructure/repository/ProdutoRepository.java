package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaId(Long id);

    List<Produto> findByAtivoTrue();

    boolean existsBySku(String sku);
}
