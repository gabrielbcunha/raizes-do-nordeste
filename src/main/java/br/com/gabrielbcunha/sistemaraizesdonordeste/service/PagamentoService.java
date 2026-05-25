package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PagamentoService {

    private PedidoRepository pedidoRepository;

    public PagamentoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PagamentoResponse tentarPagamento(Long id, PagamentoRequest request){
        Pedido pedidoProcurado = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado!"));
        if (request.getFormaPagamento() == FormaPagamento.DINHEIRO) {
            pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_CONFIRMADO);
            pedidoProcurado.setStatusPedido(StatusPedido.CONFIRMADO);
        } else if (request.getFormaPagamento() == FormaPagamento.PIX) {
            pedidoProcurado.setStatusPedido(StatusPedido.CONFIRMADO);
            pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_CONFIRMADO);
        } else if (request.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO ||  request.getFormaPagamento() == FormaPagamento.CARTAO_DEBITO || request.getFormaPagamento() == FormaPagamento.VALE_ALIMENTACAO) {
            Boolean pago = testeDeLimiteCartao();
            if (pago) {
                pedidoProcurado.setStatusPedido(StatusPedido.CONFIRMADO);
                pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_CONFIRMADO);
            } else {
                pedidoProcurado.setStatusPagamento(StatusPagamento.PAGAMENTO_RECUSADO);
            }
        }
        pedidoRepository.save(pedidoProcurado);

        return new PagamentoResponse(pedidoProcurado.getId(),
                pedidoProcurado.getFormaPagamento(),
                pedidoProcurado.getStatusPedido(),
                pedidoProcurado.getStatusPagamento());
    }

    private Boolean testeDeLimiteCartao() {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(11);
        boolean pago;
        if  (numeroAleatorio <= 8) {
            pago = true;
        } else {
            pago = false;
        }
        return pago;
    }

}
