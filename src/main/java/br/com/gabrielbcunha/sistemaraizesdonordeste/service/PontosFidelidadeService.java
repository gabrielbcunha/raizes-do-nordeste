package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeGetResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.PontosFidelidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.PontosFidelidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.PedidoRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.PontosFidelidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PontosFidelidadeService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final PontosFidelidadeRepository  pontosFidelidadeRepository;
    private final PontosFidelidadeMapper pontosFidelidadeMapper;

    public PontosFidelidadeService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository, PontosFidelidadeRepository pontosFidelidadeRepository, PontosFidelidadeMapper pontosFidelidadeMapper) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.pontosFidelidadeRepository = pontosFidelidadeRepository;
        this.pontosFidelidadeMapper = pontosFidelidadeMapper;
    }

    @Transactional
    public PontosFidelidadeCreateResponse criarPontosFidelidade(PontosFidelidadeCreateRequest request) {

        Integer pontosAdicionados = request.getQuantidadePontos();
        String numFidelidadeCliente = request.getNumCadastroFidelidade();
        Long pedidoId = request.getPedidoId();

        Cliente cliente = clienteRepository.findClienteByNumCadastroFidelidade(numFidelidadeCliente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));

        Integer quantidadePontosFidelidadeAnterior = cliente.getQuantPontosFidelidade();

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        LocalDateTime dataPedido = pedido.getDataPedido();

        PontosFidelidade pontosFidelidade = new PontosFidelidade();
        pontosFidelidade.setPedido(pedido);
        pontosFidelidade.setCliente(cliente);
        pontosFidelidade.setNumCadastroFidelidade(numFidelidadeCliente);
        pontosFidelidade.setQuantidadePontos(pontosAdicionados);
        pontosFidelidade.setDataTransacao(dataPedido);
        pontosFidelidadeRepository.save(pontosFidelidade);

        Integer quantidadePontosFidelidadeNova = quantidadePontosFidelidadeAnterior + pontosAdicionados;

        return new PontosFidelidadeCreateResponse(cliente.getId(), pedidoId, numFidelidadeCliente, quantidadePontosFidelidadeAnterior, quantidadePontosFidelidadeNova);
    }

    public Page<PontosFidelidadeGetResponse> listarPontosFidelidadePorCliente(Long clienteId, Pageable pageable) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));

        Page<PontosFidelidade> paginaMenuFidelidadeCliente = pontosFidelidadeRepository.findPontosFidelidadeByCliente(cliente, pageable);
        return paginaMenuFidelidadeCliente.map(pontosFidelidadeMapper::toDto);
    }



}
