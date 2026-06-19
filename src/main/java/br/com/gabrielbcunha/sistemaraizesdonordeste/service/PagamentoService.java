package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.PontosFidelidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class PagamentoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;
    private final PontosFidelidadeService pontosFidelidadeService;
    private final ClienteRepository clienteRepository;

    public PagamentoService(PedidoRepository pedidoRepository, PedidoService pedidoService, PontosFidelidadeService pontosFidelidadeService, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoService = pedidoService;
        this.pontosFidelidadeService = pontosFidelidadeService;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public PagamentoResponse tentarPagamento(Long id, PagamentoRequest request){
        Pedido pedidoProcurado = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado!"));

        Cliente clientePedido = clienteRepository.findById(pedidoProcurado.getCliente().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado!"));

        String clienteNumeroFidelidade = clientePedido.getNumCadastroFidelidade();
        Long pedidoId = pedidoProcurado.getId();
        Integer quantidadePontos = pedidoProcurado.getQuantidadeTotalPontosFidelidade();

        boolean programaFidelidadeAtivo = clientePedido.isProgramaFidelidadeAtivo();

        Integer quantidadeExistentePontosFidelidade = clientePedido.getQuantPontosFidelidade();
        Integer quantidadePontosFidelidade = pedidoProcurado.getQuantidadeTotalPontosFidelidade();

        if (request.getFormaPagamento() == FormaPagamento.DINHEIRO || request.getFormaPagamento() == FormaPagamento.PIX) {
            pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_CONFIRMADO);
            pedidoProcurado.setStatusPedido(StatusPedido.CONFIRMADO);
            if (programaFidelidadeAtivo) {
                criarPontoFidelidade(clienteNumeroFidelidade, pedidoId, quantidadePontos);
                clientePedido.setQuantPontosFidelidade(quantidadeExistentePontosFidelidade + quantidadePontosFidelidade);
                descontarPontosFidelidade(pedidoProcurado, clientePedido, pedidoId, clienteNumeroFidelidade);
            }
        } else if (request.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO ||  request.getFormaPagamento() == FormaPagamento.CARTAO_DEBITO || request.getFormaPagamento() == FormaPagamento.VALE_ALIMENTACAO) {
            Boolean pago = testeDeLimiteCartao();
            if (pago) {
                log.info("Mock Pagamento: Pagamento CONFIRMADO para o Pedido ID [{}]. Valor processado com sucesso.", id);
                pedidoProcurado.setStatusPedido(StatusPedido.CONFIRMADO);
                pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_CONFIRMADO);
                if (programaFidelidadeAtivo) {
                    clientePedido.setQuantPontosFidelidade(quantidadeExistentePontosFidelidade + quantidadePontosFidelidade);
                    criarPontoFidelidade(clienteNumeroFidelidade, pedidoId, quantidadePontos);
                    descontarPontosFidelidade(pedidoProcurado, clientePedido, pedidoId, clienteNumeroFidelidade);
                }
            } else {
                pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_RECUSADO);
                log.warn("Mock Pagamento: Pagamento RECUSADO para o Pedido ID [{}]. Limite indisponível.", id);
                pedidoService.cancelarPedido(id);
            }
        }

        pedidoRepository.save(pedidoProcurado);

        return new PagamentoResponse(pedidoProcurado.getId(),
                pedidoProcurado.getFormaPagamento(),
                pedidoProcurado.getStatusPedido(),
                pedidoProcurado.getStatusPagamento(),
                pedidoProcurado.getQuantidadeTotalPontosFidelidade());
    }

    private Boolean testeDeLimiteCartao() {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(11);
        boolean pago;
        if (numeroAleatorio <= 8) {
            pago = true;
        } else {
            pago = false;
        }
        return pago;
    }

    public void criarPontoFidelidade(String numCadastroFidelidade, Long pedidoId, Integer quantidadePontos) {
        PontosFidelidadeCreateRequest request = new PontosFidelidadeCreateRequest();
        request.setNumCadastroFidelidade(numCadastroFidelidade);
        request.setPedidoId(pedidoId);
        request.setQuantidadePontos(quantidadePontos);
        pontosFidelidadeService.criarPontosFidelidade(request);
    }

    public void descontarPontosFidelidade(Pedido pedido, Cliente cliente, Long pedidoId, String numeroFidelidade) {
        if (pedido.isUsarPontosFidelidade() && pedido.getValorDesconto() != null) {
            Integer quantidadeDePontosDescontados = (pedido.getValorDesconto().intValue() * 1000) * (-1);
            criarPontoFidelidade(numeroFidelidade, pedidoId, quantidadeDePontosDescontados);
            cliente.setQuantPontosFidelidade(cliente.getQuantPontosFidelidade() + quantidadeDePontosDescontados);
        }
    }

}
