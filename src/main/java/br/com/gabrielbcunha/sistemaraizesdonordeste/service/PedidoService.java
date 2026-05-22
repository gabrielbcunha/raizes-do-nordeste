package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.EstoqueInsuficienteException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.PedidoMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.*;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

            item.setPedido(novoPedido);
            itensDoPedido.add(item);
        }

        novoPedido.setItens(itensDoPedido);
        novoPedido.setValorTotal(valorTotal);

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

    public Page<PedidoCreateResponse> listarTodosPedidos(Pageable pageable) {
        Page<Pedido> paginaPedidos = pedidoRepository.findAll(pageable);
        return paginaPedidos.map(pedidoMapper::toDto);
    }


}
