package br.com.gabrielbcunha.sistemaraizesdonordeste.seeder;

import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.*;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.*;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ItemRepository itemRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final MenuUnidadeRepository menuUnidadeRepository;
    private final EstoqueUnidadeRepository estoqueUnidadeRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, FuncionarioRepository funcionarioRepository, UnidadeRepository unidadeRepository, ItemRepository itemRepository, ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, MenuUnidadeRepository menuUnidadeRepository, EstoqueUnidadeRepository estoqueUnidadeRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.itemRepository = itemRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.menuUnidadeRepository = menuUnidadeRepository;
        this.estoqueUnidadeRepository = estoqueUnidadeRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Optional<Unidade> unidadeExistente = unidadeRepository.findById(1L);
        if (unidadeExistente.isEmpty()) {
            List<CanalPedido> canaisPedidosSuportados = new ArrayList<>();
            canaisPedidosSuportados.add(CanalPedido.BALCAO);
            canaisPedidosSuportados.add(CanalPedido.WEB);
            canaisPedidosSuportados.add(CanalPedido.APP);
            canaisPedidosSuportados.add(CanalPedido.TOTEM);

            List<TipoEntrega> tiposEntregasSuportados = new ArrayList<>();
            tiposEntregasSuportados.add(TipoEntrega.RETIRADA_BALCAO);
            tiposEntregasSuportados.add(TipoEntrega.ENTREGA_CASA);

            Unidade unidade = new Unidade();
            unidade.setNome("Unidade Uninter");
            unidade.setLocalizacao("RUA SALDANHA MARINHO, 131 - CENTRO, CURITIBA - PR, 80410150");
            unidade.setCanaisSuportados(canaisPedidosSuportados);
            unidade.setEntregasSuportadas(tiposEntregasSuportados);
            unidadeRepository.save(unidade);
        }

        Optional<Item> primeiroItemExistente = itemRepository.findById(1L);
        if (primeiroItemExistente.isEmpty()) {
            criarItem("Cuscuz",
                    "Cuscuz nordestino feito com flocos de milho cozidos no vapor, servido com manteiga, queijo coalho, carne seca e ovos",
                    1000,
                    new BigDecimal("10.00"));
        }

        Optional<Item> segundoItemExistente = itemRepository.findById(2L);
        if (segundoItemExistente.isEmpty()) {
            criarItem("Tapioca",
                    "Tapioca feito de goma de mandioca, recheada com carne de sol com queijo",
                    1500,
                    new BigDecimal("15.00"));
        }

        Optional<Item> terceiroItemExistente = itemRepository.findById(3L);
        if (terceiroItemExistente.isEmpty()) {
            criarItem("Baião de Dois",
                    "Mistura de arroz, feijão-de-corda, queijo coalho, carne-seca e manteiga de garrafa",
                    3000,
                    new BigDecimal("30.00"));
        }

        Optional<Item> quartoItemExistente = itemRepository.findById(4L);
        if (quartoItemExistente.isEmpty()) {
            criarItem("Carne de Sol com Macaxeira",
                    "Carne seca (bovina) desfiada com macaxeira (mandioca) cozida e frita",
                    2500,
                    new BigDecimal("25.00"));
        }

        Optional<Item> quintoItemExistente = itemRepository.findById(5L);
        if (quintoItemExistente.isEmpty()) {
            criarItem("Acarajé",
                    "Bolinho de massa de feijão-fradinho frito no azeite de dendê e recheado com vatapá, caruru e camarão seco",
                    2800,
                    new BigDecimal("28.00"));
        }

        Optional<Item> sextoItemExistente = itemRepository.findById(6L);
        if (sextoItemExistente.isEmpty()) {
            criarItem("Moqueca",
                    "Ensopado de peixe e frutos do mar preparado com azeite de dendê, leite de coco, pimentões e coentro, servido com arroz branco e pirão",
                    3500,
                    new BigDecimal("35.00"));
        }

        Optional<Item> setimoItemExistente = itemRepository.findById(7L);
        if (setimoItemExistente.isEmpty()) {
            criarItem("Bobó de Camarão",
                    "Creme espesso de mandioca misturado com camarões, leite de coco e azeite de dendê",
                    2600,
                    new BigDecimal("26.00"));
        }

        Optional<Item> oitavoItemExistente = itemRepository.findById(8L);
        if (oitavoItemExistente.isEmpty()) {
            criarItem("Buchada de Bode",
                    "Miúdos do bode temperados e cozidos dentro do próprio estômago do animal",
                    3000,
                    new BigDecimal("30.00"));
        }

        Optional<Item> nonoItemExistente = itemRepository.findById(9L);
        if (nonoItemExistente.isEmpty()) {
            criarItem("Bolo de Rolo",
                    "Bolo de massa fina enrolada em várias camadas com recheio de goiabada derretida",
                    1000,
                    new BigDecimal("10.00"));
        }

        Optional<Item> decimoItemExistente = itemRepository.findById(10L);
        if (decimoItemExistente.isEmpty()) {
            criarItem("Coca-Cola 2L",
                    "Refrigerante de Cola de 2 Litros",
                    1200,
                    new BigDecimal("12.00"));
        }


        Optional<MenuUnidade> primeiroMenuUnidadeExistente = menuUnidadeRepository.findById(1L);
        if (primeiroMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 1L);
        }

        Optional<MenuUnidade> segundoMenuUnidadeExistente = menuUnidadeRepository.findById(2L);
        if (segundoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 2L);
        }

        Optional<MenuUnidade> terceiroMenuUnidadeExistente = menuUnidadeRepository.findById(3L);
        if (terceiroMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 3L);
        }

        Optional<MenuUnidade> quartoMenuUnidadeExistente = menuUnidadeRepository.findById(4L);
        if (quartoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 4L);
        }

        Optional<MenuUnidade> quintoMenuUnidadeExistente = menuUnidadeRepository.findById(5L);
        if (quintoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 5L);
        }

        Optional<MenuUnidade> sextoMenuUnidadeExistente = menuUnidadeRepository.findById(6L);
        if (sextoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 6L);
        }

        Optional<MenuUnidade> setimoMenuUnidadeExistente = menuUnidadeRepository.findById(7L);
        if (setimoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 7L);
        }

        Optional<MenuUnidade> oitavoMenuUnidadeExistente = menuUnidadeRepository.findById(8L);
        if (oitavoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 8L);
        }

        Optional<MenuUnidade> nonoMenuUnidadeExistente = menuUnidadeRepository.findById(9L);
        if (nonoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 9L);
        }

        Optional<MenuUnidade> decimoMenuUnidadeExistente = menuUnidadeRepository.findById(10L);
        if (decimoMenuUnidadeExistente.isEmpty()) {
            criarMenuUnidade(1L, 10L);
        }

        Optional<EstoqueUnidade> primeiroEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(1L);
        if (primeiroEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 1L, 58);
        }

        Optional<EstoqueUnidade> segundoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(2L);
        if (segundoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 2L, 80);
        }

        Optional<EstoqueUnidade> terceiroEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(3L);
        if (terceiroEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 3L, 78);
        }

        Optional<EstoqueUnidade> quartoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(4L);
        if (quartoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 4L, 39);
        }

        Optional<EstoqueUnidade> quintoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(5L);
        if (quintoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 5L, 42);
        }

        Optional<EstoqueUnidade> sextoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(6L);
        if (sextoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 6L, 46);
        }

        Optional<EstoqueUnidade> setimoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(7L);
        if (setimoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 7L, 32);
        }

        Optional<EstoqueUnidade> oitavoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(8L);
        if (oitavoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 8L, 100);
        }

        Optional<EstoqueUnidade> nonoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(9L);
        if (nonoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 9L, 100);
        }

        Optional<EstoqueUnidade> decimoEstoqueUnidadeExistente = estoqueUnidadeRepository.findById(10L);
        if (decimoEstoqueUnidadeExistente.isEmpty()) {
            criarEstoqueUnidade(1L, 10L, 100);
        }

        String userNameAdmin = "admin@raizesdonordeste.com.br";
        String senhaAdmin = "SenhaDoAdmin";

        Optional<Usuario> adminExistente = usuarioRepository.findByUserName(userNameAdmin);
        if (adminExistente.isEmpty()) {

            Usuario usuario = new Usuario();
            usuario.setUserName(userNameAdmin);
            usuario.setSenha(passwordEncoder.encode(senhaAdmin));
            usuario.setPerfil(Perfil.ROLE_ADMIN);
            usuarioRepository.save(usuario);
        }

        String crachaPrimeiroGerente = "10001";
        String senhaPrimeiroGerente = "SenhaPrimeiroGerente";

        Optional<Usuario> primeiroGerenteExistente = usuarioRepository.findByUserName(crachaPrimeiroGerente);
        if (primeiroGerenteExistente.isEmpty()) {
            criarFuncionario("Primeiro Gerente",
                    crachaPrimeiroGerente,
                    senhaPrimeiroGerente,
                    Perfil.ROLE_GERENTE,
                    Cargo.GERENTE,
                    1L);
        }

        String crachaSegundoGerente = "10002";
        String senhaSegundoGerente = "SenhaSegundoGerente";

        Optional<Usuario> segundoGerenteExistente = usuarioRepository.findByUserName(crachaSegundoGerente);
        if (segundoGerenteExistente.isEmpty()) {
            criarFuncionario("Segundo Gerente",
                    crachaSegundoGerente,
                    senhaSegundoGerente,
                    Perfil.ROLE_GERENTE,
                    Cargo.GERENTE,
                    1L);
        }

        String crachaPrimeiroAtendente = "20001";
        String senhaPrimeiroAtendente = "SenhaPrimeiroAtendente";

        Optional<Usuario> primeiroAtendenteExistente = usuarioRepository.findByUserName(crachaPrimeiroAtendente);
        if (primeiroAtendenteExistente.isEmpty()) {
            criarFuncionario("Primeiro Atendente",
                    crachaPrimeiroAtendente,
                    senhaPrimeiroAtendente,
                    Perfil.ROLE_ATENDENTE,
                    Cargo.ATENDENTE,
                    1L);
        }

        String crachaSegundoAtendente = "20002";
        String senhaSegundoAtendente = "SenhaSegundoAtendente";

        Optional<Usuario> segundoAtendenteExistente = usuarioRepository.findByUserName(crachaSegundoAtendente);
        if (segundoAtendenteExistente.isEmpty()) {
            criarFuncionario("Segundo Atendente",
                    crachaSegundoAtendente,
                    senhaSegundoAtendente,
                    Perfil.ROLE_ATENDENTE,
                    Cargo.ATENDENTE,
                    1L);
        }

        String crachaTerceiroAtendente = "20003";
        String senhaTerceiroAtendente = "SenhaTerceiroAtendente";

        Optional<Usuario> terceiroAtendenteExistente = usuarioRepository.findByUserName(crachaTerceiroAtendente);
        if (terceiroAtendenteExistente.isEmpty()) {
            criarFuncionario("Terceiro Atendente",
                    crachaTerceiroAtendente,
                    senhaTerceiroAtendente,
                    Perfil.ROLE_ATENDENTE,
                    Cargo.ATENDENTE,
                    1L);
        }

        String crachaPrimeiroCozinheiro = "30001";
        String senhaPrimeiroCozinheiro = "SenhaPrimeiroCozinheiro";

        Optional<Usuario> primeiroCozinheiroExistente = usuarioRepository.findByUserName(crachaPrimeiroCozinheiro);
        if (primeiroCozinheiroExistente.isEmpty()) {
            criarFuncionario("Primeiro Cozinheiro",
                    crachaPrimeiroCozinheiro,
                    senhaPrimeiroCozinheiro,
                    Perfil.ROLE_COZINHEIRO,
                    Cargo.COZINHEIRO,
                    1L);
        }

        String crachaSegundoCozinheiro = "30002";
        String senhaSegundoCozinheiro = "SenhaSegundoCozinheiro";

        Optional<Usuario> segundoCozinheiroExistente = usuarioRepository.findByUserName(crachaSegundoCozinheiro);
        if (segundoCozinheiroExistente.isEmpty()) {
            criarFuncionario("Segundo Cozinheiro",
                    crachaSegundoCozinheiro,
                    senhaSegundoCozinheiro,
                    Perfil.ROLE_COZINHEIRO,
                    Cargo.COZINHEIRO,
                    1L);
        }

        String crachaTerceiroCozinheiro = "30003";
        String senhaTerceiroCozinheiro = "SenhaTerceiroCozinheiro";

        Optional<Usuario> terceiroCozinheiroExistente = usuarioRepository.findByUserName(crachaTerceiroCozinheiro);
        if (terceiroCozinheiroExistente.isEmpty()) {
            criarFuncionario("Terceiro Cozinheiro",
                    crachaTerceiroCozinheiro,
                    senhaTerceiroCozinheiro,
                    Perfil.ROLE_COZINHEIRO,
                    Cargo.COZINHEIRO,
                    1L);
        }

        String crachaPrimeiroAdministrativo = "40001";
        String senhaPrimeiroAdministrativo = "SenhaPrimeiroAdministrativo";

        Optional<Usuario> primerioAdministrativoExistente = usuarioRepository.findByUserName(crachaPrimeiroAdministrativo);
        if (primerioAdministrativoExistente.isEmpty()) {
            criarFuncionario("Primeiro Administrativo",
                    crachaPrimeiroAdministrativo,
                    senhaPrimeiroAdministrativo,
                    Perfil.ROLE_ADMINISTRATIVO,
                    Cargo.ADMINISTRATIVO,
                    1L);
        }

        String emailPrimeiroCliente = "emailPrimeiroCliente@outlook.com";
        String senhaPrimeiroCliente = "SenhaPrimeiroCliente";

        Optional<Usuario> primeiroClienteExistente = usuarioRepository.findByUserName(emailPrimeiroCliente);
        if (primeiroClienteExistente.isEmpty()) {
            criarCliente("Primeiro Cliente", "0000000001", "55999999999", emailPrimeiroCliente, senhaPrimeiroCliente, true, 0);
        }

        String emailSegundoCliente = "emailSegundoCliente@outlook.com";
        String senhaSegundoCliente = "SenhaSegundoCliente";

        Optional<Usuario> segundoClienteExistente = usuarioRepository.findByUserName(emailSegundoCliente);
        if (segundoClienteExistente.isEmpty()) {
            criarCliente("Segundo Cliente", "0000000002", "55999999998", emailSegundoCliente, senhaSegundoCliente, false, 0);
        }

        Optional<Pedido> primeiroPedidoExistente = pedidoRepository.findById(1L);
        if (primeiroPedidoExistente.isEmpty()) {


            List<ItemPedido> itens = new ArrayList<>();
            itens.add(criarItemPedido(1L, 2));
            itens.add(criarItemPedido(9L, 2));

            criarPedido(1L,
                    1L,
                    CanalPedido.WEB,
                    itens,
                    LocalDateTime.now(),
                    StatusPedido.EM_PREPARO,
                    FormaPagamento.PIX,
                    StatusPagamento.PAGAMENTO_CONFIRMADO,
                    TipoEntrega.ENTREGA_CASA);
        }

        Optional<Pedido> segundoPedidoExistente = pedidoRepository.findById(2L);
        if (segundoPedidoExistente.isEmpty()) {

            List<ItemPedido> itens = new ArrayList<>();
            itens.add(criarItemPedido(5L, 4));
            itens.add(criarItemPedido(3L, 3));

            criarPedido(1L,
                    1L,
                    CanalPedido.TOTEM,
                    itens,
                    LocalDateTime.now(),
                    StatusPedido.EM_PREPARO,
                    FormaPagamento.PIX,
                    StatusPagamento.PAGAMENTO_CONFIRMADO,
                    TipoEntrega.ENTREGA_CASA);
        }

        Optional<Pedido> terceiroPedidoExistente = pedidoRepository.findById(3L);
        if (terceiroPedidoExistente.isEmpty()) {

            List<ItemPedido> itens = new ArrayList<>();
            itens.add(criarItemPedido(2L, 5));
            itens.add(criarItemPedido(9L, 3));

            criarPedido(2L,
                    1L,
                    CanalPedido.WEB,
                    itens,
                    LocalDateTime.now(),
                    StatusPedido.EM_PREPARO,
                    FormaPagamento.PIX,
                    StatusPagamento.PAGAMENTO_CONFIRMADO,
                    TipoEntrega.ENTREGA_CASA);
        }

        Optional<Pedido> quartoPedidoExistente = pedidoRepository.findById(4L);
        if (quartoPedidoExistente.isEmpty()) {

            List<ItemPedido> itens = new ArrayList<>();
            itens.add(criarItemPedido(1L, 2));
            itens.add(criarItemPedido(9L, 2));

            criarPedido(2L,
                    1L,
                    CanalPedido.BALCAO,
                    itens,
                    LocalDateTime.now(),
                    StatusPedido.EM_PREPARO,
                    FormaPagamento.PIX,
                    StatusPagamento.PAGAMENTO_CONFIRMADO,
                    TipoEntrega.ENTREGA_CASA);
        }

    }

    private void criarItem(String nome, String descricao, Integer quantidadePontosFidelidade, BigDecimal preco) {
        Item item = new Item();
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setQuantidadePontosFidelidade(quantidadePontosFidelidade);
        item.setPreco(preco);
        itemRepository.save(item);
    }

    private void criarMenuUnidade(Long idUnidade, Long idItem){
        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));

        Item item = itemRepository.findById(idItem)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado"));

        MenuUnidade menuUnidade = new MenuUnidade();
        menuUnidade.setUnidade(unidade);
        menuUnidade.setItem(item);
        menuUnidade.setDisponivel(true);
        menuUnidadeRepository.save(menuUnidade);
    }

    private void criarEstoqueUnidade(Long idUnidade, Long idItem, Integer quantidade){
        Unidade unidade = unidadeRepository.findById(idUnidade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada"));

        Item item = itemRepository.findById(idItem)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado"));

        EstoqueUnidade estoqueUnidade = new EstoqueUnidade();
        estoqueUnidade.setUnidade(unidade);
        estoqueUnidade.setItem(item);
        estoqueUnidade.setQuantidade(quantidade);
        estoqueUnidadeRepository.save(estoqueUnidade);
    }

    private void criarFuncionario(String nome, String cracha, String senha, Perfil perfil, Cargo cargo, Long idUnidade) {

        Usuario usuario = new Usuario();
        usuario.setUserName(cracha);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setPerfil(perfil);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);
        funcionario.setUnidade(unidadeRepository.findById(idUnidade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada")));
        funcionario.setUsuario(usuario);
        funcionarioRepository.save(funcionario);
    }

    private void criarCliente(String nome, String cpf, String numContato, String email, String senha, Boolean programaFidelidadeAtivo, Integer quantidadePontosFidelidade) {
        Usuario usuario = new Usuario();
        usuario.setUserName(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setPerfil(Perfil.ROLE_CLIENTE);

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setNumContato(numContato);
        cliente.setUsuario(usuario);
        cliente.setProgramaFidelidadeAtivo(programaFidelidadeAtivo);
        cliente.setQuantPontosFidelidade(quantidadePontosFidelidade);
        String numeroFidelidade = java.util.UUID.randomUUID().toString();
        cliente.setNumCadastroFidelidade(numeroFidelidade);
        clienteRepository.save(cliente);
    }

    private ItemPedido criarItemPedido(Long idItem, Integer quantidade) {
        ItemPedido itemPedido = new ItemPedido();

        Item item = itemRepository.findById(idItem)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Item naõ encontrado"));

        itemPedido.setItem(item);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setQuantidadeTotalParcialPontosFidelidade(item.getQuantidadePontosFidelidade() * quantidade);
        itemPedido.setValorUnitario(item.getPreco());

        BigDecimal quantidadeDecimal =  new BigDecimal(quantidade);
        itemPedido.setValorTotalParcial(itemPedido.getValorUnitario().multiply(quantidadeDecimal));
        return itemPedido;
    }


    private void criarPedido(Long idCliente, Long idUnidade, CanalPedido canalPedido, List<ItemPedido> itens, LocalDateTime dataPedido, StatusPedido statusPedido, FormaPagamento formaPagamento, StatusPagamento statusPagamento, TipoEntrega tipoEntrega) {
        Pedido pedido = new Pedido();
        pedido.setCliente(clienteRepository.findById(idCliente).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado")));
        pedido.setUnidade(unidadeRepository.findById(idUnidade).orElseThrow(() -> new RecursoNaoEncontradoException("Unidade não encontrada")));
        pedido.setCanalPedido(canalPedido);
        pedido.setItens(itens);
        pedido.setDataPedido(dataPedido);
        pedido.setStatusPedido(statusPedido);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setStatusPagamento(statusPagamento);
        pedido.setTipoEntrega(tipoEntrega);

        int totalPontos = 0;
        BigDecimal totalValor = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            item.setPedido(pedido);
            totalPontos += item.getQuantidadeTotalParcialPontosFidelidade();
            totalValor = totalValor.add(item.getValorTotalParcial());
        }

        pedido.setQuantidadeTotalPontosFidelidade(totalPontos);
        pedido.setValorTotal(totalValor);
        pedidoRepository.save(pedido);
    }


}


