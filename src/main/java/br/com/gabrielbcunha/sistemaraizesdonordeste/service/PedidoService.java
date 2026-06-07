package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.*;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.EstoqueInsuficienteException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RegraNegocioException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.PedidoMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.*;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;
    private final UnidadeRepository unidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EstoqueUnidadeRepository estoqueUnidadeRepository;
    private final PedidoMapper pedidoMapper;


    public PedidoService(PedidoRepository pedidoRepository, ItemRepository itemRepository, UnidadeRepository unidadeRepository, ClienteRepository clienteRepository, EstoqueUnidadeRepository estoqueUnidadeRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.itemRepository = itemRepository;
        this.unidadeRepository = unidadeRepository;
        this.clienteRepository = clienteRepository;
        this.estoqueUnidadeRepository = estoqueUnidadeRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional
    public PedidoCreateResponse cadastrarPedido(PedidoCreateRequest pedidoCreateRequest) {

        Cliente clienteBuscado = clienteRepository.findById(pedidoCreateRequest.getClienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente de ID: " + pedidoCreateRequest.getClienteId() + " não encontrado"));

        Unidade unidadeBuscada = unidadeRepository.findById(pedidoCreateRequest.getUnidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID: " + pedidoCreateRequest.getUnidadeId() + " não encontrado"));

        Pedido novoPedido = pedidoMapper.toEntity(pedidoCreateRequest);
        novoPedido.setCliente(clienteBuscado);
        novoPedido.setUnidade(unidadeBuscada);
        novoPedido.setDataPedido(LocalDateTime.now());
        novoPedido.setStatusPedido(StatusPedido.AGUARDANDO_CONFIRMACAO);
        novoPedido.setStatusPagamento(StatusPagamento.AGUARDANDO_PAGAMENTO);

        List<ItemPedido> itensDoPedido = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        Integer valorTotalPontosFidelidade = 0;

        for (ItemPedidoCreateRequest itemRequest : pedidoCreateRequest.getItens()) {

            Item produto = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Item de ID: " + itemRequest.getItemId() + " Não encontrado"));

            EstoqueUnidade estoqueUnidade = estoqueUnidadeRepository.findByUnidadeIdAndItemId(pedidoCreateRequest.getUnidadeId(), produto.getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque da unidade de ID: " + pedidoCreateRequest.getUnidadeId() + " Não encontrado"));

            verificarEBaixarEstoque(itemRequest.getQuantidade(), estoqueUnidade);

            ItemPedido item = new ItemPedido();
            item.setItem(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setValorUnitario(produto.getPreco());

            BigDecimal totalItem = produto.getPreco().multiply(new BigDecimal(itemRequest.getQuantidade()));
            item.setValorTotalParcial(totalItem);
            valorTotal = valorTotal.add(totalItem);

            Integer totalParcialPontosFidelidade = produto.getQuantidadePontosFidelidade() * itemRequest.getQuantidade();
            item.setQuantidadeTotalParcialPontosFidelidade(totalParcialPontosFidelidade);
            valorTotalPontosFidelidade = valorTotalPontosFidelidade + totalParcialPontosFidelidade;

            item.setPedido(novoPedido);
            itensDoPedido.add(item);
        }

        novoPedido.setItens(itensDoPedido);
        novoPedido.setValorTotal(valorTotal);
        novoPedido.setQuantidadeTotalPontosFidelidade(valorTotalPontosFidelidade);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
        return pedidoMapper.toDto(pedidoSalvo);
    }

    private void verificarEBaixarEstoque(Integer quantidadePedida, EstoqueUnidade estoqueItem){

        if (estoqueItem.getQuantidade() < quantidadePedida) {
            throw new EstoqueInsuficienteException("A quantidade pedida do produto é maior do que a contida em estoque");
        }

        Integer novaQuantidade = estoqueItem.getQuantidade() - quantidadePedida;
        estoqueItem.setQuantidade(novaQuantidade);
    }

    public Page<PedidoCreateResponse> listarPedidos(CanalPedido canalPedido, Pageable pageable) {
        if (canalPedido == null) {
            Page<Pedido> paginaPedidos = pedidoRepository.findAll(pageable);
            return paginaPedidos.map(pedidoMapper::toDto);
        }
        else {
            Page<Pedido> paginaPedidosPorCanalPedido = pedidoRepository.findPedidoByCanalPedido(canalPedido, pageable);
            return paginaPedidosPorCanalPedido.map(pedidoMapper::toDto);
        }
    }

    @Transactional
    public PedidoCancelarResponse cancelarPedido(Long id) {
        Pedido pedidoCancelado = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado"));

        if (pedidoCancelado.getStatusPedido() != StatusPedido.CANCELADO){
            pedidoCancelado.setStatusPedido(StatusPedido.CANCELADO);

            for (ItemPedido itemPedido : pedidoCancelado.getItens()) {

                Long idDoProduto = itemPedido.getItem().getId();

                Long idDaUnidade = pedidoCancelado.getUnidade().getId();

                EstoqueUnidade estoqueUnidade = estoqueUnidadeRepository.findByUnidadeIdAndItemId(idDaUnidade, idDoProduto)
                       .orElseThrow(() -> new RecursoNaoEncontradoException("Estoque da unidade de ID: " + idDaUnidade + " Não encontrado"));

                reporEstoquePedidoCancelado(itemPedido.getQuantidade(), estoqueUnidade);

            }
        }
        return pedidoMapper.toDtoCancel(pedidoCancelado);
    }

    private void reporEstoquePedidoCancelado(Integer quantidadePedida, EstoqueUnidade estoqueItem){
        Integer novaQuantidade = estoqueItem.getQuantidade() + quantidadePedida;
        estoqueItem.setQuantidade(novaQuantidade);
    }

    @Transactional
    public PedidoPatchStatusResponse mudarStatusPedido(Long id, PedidoPatchStatusRequest statusRequest) {
        Pedido pedidoProcurado = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido de ID: " + id + " não encontrado"));

        StatusPedido statusOriginalPedido = pedidoProcurado.getStatusPedido();
        StatusPedido statusNovoDoPedido = statusRequest.getStatusPedido();

        switch (statusOriginalPedido){
            case AGUARDANDO_CONFIRMACAO:
                break;
            case CONFIRMADO:
                if (statusNovoDoPedido == StatusPedido.AGUARDANDO_CONFIRMACAO){
                    throw new RegraNegocioException("Não é possível retroceder um pedido já confirmado.");
                }
                break;
            case EM_PREPARO:
                if (statusNovoDoPedido == StatusPedido.AGUARDANDO_CONFIRMACAO || statusNovoDoPedido == StatusPedido.CONFIRMADO){
                    throw new RegraNegocioException("Não é possível retroceder um pedido em preparo");
                }
                break;
            case PRONTO_PARA_RETIRADA:
                if (statusNovoDoPedido == StatusPedido.AGUARDANDO_CONFIRMACAO || statusNovoDoPedido == StatusPedido.CONFIRMADO || statusNovoDoPedido == StatusPedido.EM_PREPARO){
                    throw new RegraNegocioException("Não é possível retroceder um pedido já pronto para retirada");
                }
                break;
            case FINALIZADO:
                throw new RegraNegocioException("O pedido já foi finalizado, seu status não pode ser alterado");
            case CANCELADO:
                throw new RegraNegocioException("O pedido foi cancelado, seu status não pode ser alterado");
            default:
                throw new RegraNegocioException("Status de origem inválido");
        }

        pedidoProcurado.setStatusPedido(statusNovoDoPedido);
        return new PedidoPatchStatusResponse(id, statusNovoDoPedido);
    }


}
