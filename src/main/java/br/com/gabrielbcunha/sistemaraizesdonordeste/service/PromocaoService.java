package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.PromocaoMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Promocao;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.MenuUnidadeRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.PromocaoRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PromocaoService {
	
	private final PromocaoRepository promocaoRepository;
	private final UnidadeRepository unidadeRepository; 
	private final MenuUnidadeRepository menuUnidadeRepository;
	private final PromocaoMapper promocaoMapper;
	
	public PromocaoService(PromocaoRepository promocaoRepository, UnidadeRepository unidadeRepository, MenuUnidadeRepository menuUnidadeRepository, PromocaoMapper promocaoMapper) {
		this.promocaoRepository = promocaoRepository;
		this.unidadeRepository = unidadeRepository;
		this.menuUnidadeRepository = menuUnidadeRepository; 
		this.promocaoMapper = promocaoMapper;
	}

	@Transactional
	public PromocaoCreateResponse criarPromocao (PromocaoCreateRequest request) {
		
		Promocao promocao = new Promocao();
		promocao.setNomePromocao(request.getNomePromocao()); 
		promocao.setCodigoPromocao(request.getCodigoPromocao());
		
		List<Unidade> unidadesList = new ArrayList<>();
		for (Long unidadeId : request.getUnidadesIds()){ 
			Unidade unidade = unidadeRepository.findById(unidadeId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada")); 	
			unidadesList.add(unidade);	
		}	
		Set<Unidade> unidadesSet = new HashSet<>(unidadesList);
		promocao.setUnidades(unidadesSet);
		
		List<MenuUnidade> menuUnidadesList = new ArrayList<>();
		for (Long menuId : request.getMenuUnidadeIds()) { 
			MenuUnidade menuUnidade = menuUnidadeRepository.findById(menuId)
				.orElseThrow(() -> new RecursoNaoEncontradoException("Menu de unidade não encontrado")); 
			menuUnidadesList.add(menuUnidade);			
		}
		Set<MenuUnidade> menuUnidadesSet = new HashSet<>(menuUnidadesList);
		promocao.setItensMenu(menuUnidadesSet);
		
		promocao.setEntregasEscritasNaPromocao(request.getEntregasEscritasNaPromocao());
		promocao.setCanaisEscritosNaPromocao(request.getCanaisEscritosNaPromocao());
		promocao.setPagamentosEscritosNaPromocao(request.getPagamentosEscritosNaPromocao());
		promocao.setTipoPromocao(request.getTipoPromocao());
		promocao.setValorPromocao(request.getValorPromocao());
		promocao.setInicioPromocao(request.getInicioPromocao());
		promocao.setTerminoPromocao(request.getTerminoPromocao());
		
		Promocao promocaoSalva = promocaoRepository.save(promocao);
		return promocaoMapper.toDto(promocaoSalva);
	}
}