package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.service.dto.*;
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
    ProdutoRequestDTO paraProdutoRequestDTO(Produto produto);

    @Mapping(source = "categoria.nome", target = "nomeCategoria")
    ProdutoResponseDTO paraProdutoResponseDTO(Produto produto);

    @Mapping(source = "categoria.nome", target = "nomeCategoria")
    ProdutoResumoDTO paraProdutoResumoDTO(Produto produto);

    @Mapping(source = "categoriaId", target = "categoria.id")
    Produto toEntity(ProdutoRequestDTO dto);

    public ProdutoDTO paraProdutoDTO(Produto produto);

    @Mapping(target = "produto", ignore = true)
    public Categoria paraCategoriaEntity(CategoriaDTO categoriaDTO);

    public CategoriaDTO paraCategoriaDTO(Categoria categoria);

    List<ProdutoDTO> paraDTOList(List<Produto> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    public Produto updateProduto(ProdutoDTO dto, @MappingTarget Produto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    public Categoria updateCategoria(CategoriaDTO dto, @MappingTarget Categoria entity);



}
