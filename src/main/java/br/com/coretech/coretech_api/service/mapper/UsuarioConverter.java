package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Endereco;
import br.com.coretech.coretech_api.infraestructure.entity.Telefone;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.LoginDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {PedidoConverter.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioConverter {

    @Mapping(source = "id", target = "id")
    Usuario paraUsuarioEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO paraUsuarioDTO(Usuario usuario);

    Usuario paraUsuarioLogin(LoginDTO loginDTO);

    LoginDTO paraLoginDTO(Usuario usuario);

    @Mapping(target = "usuario", ignore = true)
    Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO);

    @Mapping(target = "usuario", ignore = true)
    Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO);

    List<EnderecoDTO> paraEnderecoDTO(List<Endereco> endereco);

    EnderecoDTO paraEnderecoDTO(Endereco endereco);

    List<TelefoneDTO> paraTelefoneDTO(List<Telefone> telefone);

    TelefoneDTO paraTelfoneDTO(Telefone telefone);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Usuario updateUsuario(UsuarioDTO dto, @MappingTarget Usuario entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    Endereco updateEndereco(EnderecoDTO dto, @MappingTarget Endereco entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    Telefone updateTelefone(TelefoneDTO dto, @MappingTarget Telefone entity);
}
