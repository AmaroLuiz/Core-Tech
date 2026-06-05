package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.service.dto.CategoriaDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoRequestDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoConverter {

    @Mapping(source = "id", target = "id")
    public Produto paraProdutoEntity(ProdutoDTO produtoDTO);

    @Mapping(target = "categoria.nome", ignore = true)
    @Mapping(target = "categoria.slug", ignore = true)
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "criadoEm", target = "criadoEm")
    ProdutoRequestDTO paraProdutoResponseDTO(Produto produto);


    @Mapping(source = "categoriaId", target = "categoria.id")
    Produto toEntity(ProdutoRequestDTO dto);

    public ProdutoDTO paraProdutoDTO(Produto produto);

    @Mapping(target = "produto", ignore = true)
    public Categoria paraCategoriaEntity(CategoriaDTO categoriaDTO);

    public CategoriaDTO paraCategoriaDTO(Categoria categoria);

    List<ProdutoDTO> paraDTOList(List<Produto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "sku", ignore = true)
    public Produto updateProduto(ProdutoDTO dto, @MappingTarget Produto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    public Categoria updateCategoria(CategoriaDTO dto, @MappingTarget Categoria entity);



}
