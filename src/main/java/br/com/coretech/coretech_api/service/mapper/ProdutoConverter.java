package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.service.dto.CategoriaDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoConverter {

    @Mapping(source = "id", target = "id")
    public Produto paraProdutoEntity(ProdutoDTO produtoDTO);

    public ProdutoDTO paraProdutoDTO(Produto produto);

    @Mapping(target = "produtos", ignore = true)
    public Categoria paraCategoriaEntity(CategoriaDTO categoriaDTO);

    public CategoriaDTO paraCategoriaDTO(Categoria categoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    public Produto updateProduto(ProdutoDTO dto, @MappingTarget Produto entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    public Categoria updateCategoria(CategoriaDTO dto, @MappingTarget Categoria entity);



}
