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
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoPromocao;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemRepository itemRepository;
    private final UnidadeRepository unidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EstoqueUnidadeRepository estoqueUnidadeRepository;
    private final PromocaoRepository promocaoRepository;
    private final PedidoMapper pedidoMapper;


    public PedidoService(PedidoRepository pedidoRepository, ItemRepository itemRepository, UnidadeRepository unidadeRepository, ClienteRepository clienteRepository, EstoqueUnidadeRepository estoqueUnidadeRepository, PromocaoRepository promocaoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.itemRepository = itemRepository;
        this.unidadeRepository = unidadeRepository;
        this.clienteRepository = clienteRepository;
        this.estoqueUnidadeRepository = estoqueUnidadeRepository;
        this.promocaoRepository = promocaoRepository;
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

        if (pedidoCreateRequest.isUsarPontosFidelidade() && pedidoCreateRequest.isUsarCodigoDeDesconto()){
            log.warn("Regra de Negócio: Cliente ID [{}] tentou utilizar pontos de fidelidade e cupom simultaneamente. Ação bloqueada.", pedidoCreateRequest.getClienteId());
            throw new RegraNegocioException("Não é permitido a utilização de dois métodos de desconto simultaneamente ");
        }

        if (pedidoCreateRequest.isUsarPontosFidelidade()) {
            BigDecimal valorDesconto = calcularDescontoFidelidade(clienteBuscado.getQuantPontosFidelidade(), valorTotal);
            novoPedido.setValorDesconto(valorDesconto);
            novoPedido.setValorComDesconto(valorTotal.subtract(valorDesconto));
        }

        if (pedidoCreateRequest.isUsarCodigoDeDesconto()) {
            BigDecimal valorDesconto = calcularDescontoCodigo(pedidoCreateRequest.getCodigoDesconto(), valorTotal, unidadeBuscada);
            novoPedido.setValorDesconto(valorDesconto);
            novoPedido.setValorComDesconto(valorTotal.subtract(valorDesconto));
        }

        BigDecimal valorBaseParaPontos = novoPedido.getValorComDesconto() != null ? novoPedido.getValorComDesconto() : valorTotal;
        novoPedido.setQuantidadeTotalPontosFidelidade(new BigDecimal(100).multiply(valorBaseParaPontos).intValue());

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
        log.info("Novo pedido criado com sucesso! Pedido ID: [{}], Cliente ID: [{}], Valor Total: R$ [{}]", pedidoSalvo.getId(), clienteBuscado.getId(), pedidoSalvo.getValorTotal());
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
        log.info("Pedido ID [{}] foi CANCELADO. Os itens foram devolvidos ao estoque da unidade.", id);
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


    public BigDecimal calcularDescontoFidelidade(Integer quantidadePontosCliente, BigDecimal valorTotal){

        BigDecimal quantidadePontos = BigDecimal.valueOf(quantidadePontosCliente);
        BigDecimal total = valorTotal;
        BigDecimal reguaPontos = new BigDecimal("50");

        BigDecimal[] divisaoPontos = total.divideAndRemainder(reguaPontos);

        BigDecimal vezesDivisao =  divisaoPontos[0];
        BigDecimal resto = divisaoPontos[1];

        BigDecimal descontoMaximoDePontos = vezesDivisao.multiply(new BigDecimal(10000));

        if (quantidadePontos.compareTo(descontoMaximoDePontos) > 0 || quantidadePontos.compareTo(descontoMaximoDePontos) == 0) {
            BigDecimal valorDesconto = descontoMaximoDePontos.divide(new BigDecimal(1000));
            return valorDesconto;
        } else {
            BigDecimal valorDesconto = quantidadePontos.divide(new BigDecimal(1000));
            return valorDesconto;
        }
    }

    public BigDecimal calcularDescontoCodigo(String codigoPromocao, BigDecimal valorTotal, Unidade unidade){
        Promocao promocao = promocaoRepository.findByCodigoPromocao(codigoPromocao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Promoção não encontrada"));

        BigDecimal valorDesconto;
        LocalDateTime dataAtual = LocalDateTime.now();


        if (dataAtual.isBefore(promocao.getInicioPromocao()) || dataAtual.isAfter(promocao.getTerminoPromocao())) {
            throw new RegraNegocioException("A promoção está encerrada ou não ainda não foi iniciada");
        }

        boolean unidadeValida = promocao.getUnidades().stream().anyMatch(u -> u.getId().equals(unidade.getId()));

        if (!unidadeValida) {
            throw new RegraNegocioException("Sua unidade não está incluída nessa promoção");
        }

        if (promocao.getTipoPromocao() == TipoPromocao.VALOR_FIXO){
            valorDesconto = promocao.getValorPromocao();
        } else {
            valorDesconto = valorTotal.multiply(promocao.getValorPromocao()).divide(new BigDecimal(100));
        }

        if (valorDesconto.compareTo(valorTotal) > 0) {
            return valorTotal;
        }

        return valorDesconto;
    }

}
