package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Override
    boolean existsById(Long id);

    @Override
    Optional<Pedido> findById(Long id);


    Optional<Pedido> findByIdAndUsuarioEmail(Long id, String email);
}
