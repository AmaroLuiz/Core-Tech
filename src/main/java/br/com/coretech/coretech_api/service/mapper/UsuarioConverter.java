package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Endereco;
import br.com.coretech.coretech_api.infraestructure.entity.Telefone;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioConverter {

    @Mapping(source = "id", target = "id")
    Usuario paraUsuarioEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO paraUsuarioDTO(Usuario usuario);

    List<EnderecoDTO> paraEnderecoDTO(List<Endereco> endereco);
    List<Endereco> paraEnderecoEntity(List<EnderecoDTO> enderecoDTO);

    List<TelefoneDTO> paraTelefoneEntity(List<Telefone> telefone);
    List<Telefone> paraTelefoneDTO(List<TelefoneDTO> telefoneDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Usuario updateUsuario(UsuarioDTO dto, @MappingTarget Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Endereco updateEndereco(EnderecoDTO dto, @MappingTarget Endereco entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Telefone updateTelefone(TelefoneDTO dto, @MappingTarget Telefone entity);
}
