package br.com.coretech.coretech_api.infraestructure.repository;

import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
