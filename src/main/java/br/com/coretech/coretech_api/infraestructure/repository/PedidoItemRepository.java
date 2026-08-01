package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    @Override
    Optional<PedidoItem> findById(Long id);


}
