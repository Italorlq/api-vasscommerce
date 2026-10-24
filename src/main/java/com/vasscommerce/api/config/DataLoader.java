package com.vasscommerce.api.config;

import com.vasscommerce.api.model.*;
import com.vasscommerce.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Insere dados de exemplo na primeira execução (só se o banco estiver vazio). */
@Component
public class DataLoader implements CommandLineRunner {

    private final EstadoRepository estados;
    private final CidadeRepository cidades;
    private final CategoriaRepository categorias;
    private final ProdutoRepository produtos;
    private final ClienteRepository clientes;
    private final CartaoRepository cartoes;
    private final PedidoRepository pedidos;

    public DataLoader(EstadoRepository estados, CidadeRepository cidades, CategoriaRepository categorias,
                      ProdutoRepository produtos, ClienteRepository clientes, CartaoRepository cartoes,
                      PedidoRepository pedidos) {
        this.estados = estados;
        this.cidades = cidades;
        this.categorias = categorias;
        this.produtos = produtos;
        this.clientes = clientes;
        this.cartoes = cartoes;
        this.pedidos = pedidos;
    }

    @Override
    public void run(String... args) {
        if (categorias.count() > 0) {
            return;
        }

        // Estados e cidades
        Estado rj = estado("RJ", "Rio de Janeiro");
        Estado sp = estado("SP", "São Paulo");
        Estado mg = estado("MG", "Minas Gerais");
        Cidade valenca = cidade("Valença", rj);
        cidade("Volta Redonda", rj);
        cidade("Rio de Janeiro", rj);
        cidade("São Paulo", sp);
        cidade("Campinas", sp);
        cidade("Belo Horizonte", mg);

        // Categorias e produtos
        Categoria eletronicos = categoria("eletronicos.png", "Eletrônicos", "Celulares, notebooks e acessórios");
        Categoria livros = categoria("livros.png", "Livros", "Livros técnicos e literatura");
        Produto notebook = produto("Notebook Gamer", "Notebook com placa de vídeo dedicada",
                "notebook.png", "4500.00", eletronicos, LocalDateTime.of(2026, 10, 1, 10, 30));
        Produto smartphone = produto("Smartphone X", "Smartphone com 128 GB de memória",
                "smartphone.png", "2300.00", eletronicos, LocalDateTime.of(2026, 10, 2, 9, 15));
        produto("Clean Code", "Livro sobre boas práticas de programação",
                "clean-code.png", "89.90", livros, LocalDateTime.of(2026, 10, 3, 14, 0));

        // Cliente com endereço
        Endereco endereco = new Endereco();
        endereco.setRua("Rua das Flores");
        endereco.setNumero(120);
        endereco.setCep("27600000");
        endereco.setComplemento("Apto 301");
        endereco.setTelefone("24999998888");
        endereco.setBairro("Centro");
        endereco.setCidade(valenca);
        endereco.setDataCadastro(LocalDateTime.of(2026, 9, 20, 8, 0));
        endereco.setDataUltimaAtualizacao(LocalDateTime.of(2026, 10, 5, 17, 20));

        Cliente maria = new Cliente();
        maria.setNomeCompleto("Maria Souza Lima");
        maria.setEmail("maria.lima@email.com");
        maria.setSenha("123456");
        maria.setFotoUrl("http://vasscommerce.com.br/img/maria.png");
        maria.setDataNascimento(LocalDate.of(1995, 4, 12));
        maria.setCpf("12345678900");
        maria.setDataCadastro(LocalDateTime.of(2026, 9, 20, 8, 0));
        maria.setDataUltimaAtualizacao(LocalDateTime.of(2026, 10, 5, 17, 20));
        maria.setEndereco(endereco);
        maria = clientes.save(maria);

        // Cartões (o terceiro está excluído e não deve aparecer na listagem)
        Cartao credito = cartao(maria, TipoCartao.CREDITO, false, LocalDateTime.of(2026, 10, 10, 9, 0));
        Cartao debito = cartao(maria, TipoCartao.DEBITO, false, LocalDateTime.of(2026, 10, 12, 9, 0));
        cartao(maria, TipoCartao.CREDITO, true, LocalDateTime.of(2026, 9, 25, 9, 0));

        // Pedido 1: 2 notebooks pagos com dois cartões
        Pedido p1 = pedido(maria, LocalDateTime.of(2026, 10, 23, 14, 0), PedidoStatus.AGUARDANDO_PAGAMENTO);
        item(p1, notebook, 2, "4500.00");
        pagamento(p1, credito, "5000.00");
        pagamento(p1, debito, "4000.00");
        p1.setValorTotal(new BigDecimal("9000.00"));
        pedidos.save(p1);

        // Pedido 2: 1 smartphone pago com um cartão
        Pedido p2 = pedido(maria, LocalDateTime.of(2026, 10, 2, 9, 30), PedidoStatus.ENTREGUE_CLIENTE);
        item(p2, smartphone, 1, "2300.00");
        pagamento(p2, credito, "2300.00");
        p2.setValorTotal(new BigDecimal("2300.00"));
        pedidos.save(p2);
    }

    private Estado estado(String sigla, String nome) {
        Estado e = new Estado();
        e.setSigla(sigla);
        e.setNome(nome);
        return estados.save(e);
    }

    private Cidade cidade(String nome, Estado estado) {
        Cidade c = new Cidade();
        c.setNome(nome);
        c.setEstado(estado);
        return cidades.save(c);
    }

    private Categoria categoria(String imagem, String nome, String descricao) {
        Categoria c = new Categoria();
        c.setImagemSimboloUrl("http://vasscommerce.com.br/img/" + imagem);
        c.setNome(nome);
        c.setDescricao(descricao);
        return categorias.save(c);
    }

    private Produto produto(String nome, String descricao, String foto, String valor,
                            Categoria categoria, LocalDateTime cadastro) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setFotoUrl("http://vasscommerce.com.br/img/" + foto);
        p.setValorUnitario(new BigDecimal(valor));
        p.setCategoria(categoria);
        p.setDataCadastro(cadastro);
        p.setDataUltimaAtualizacao(cadastro);
        return produtos.save(p);
    }

    private Cartao cartao(Cliente titular, TipoCartao tipo, boolean excluido, LocalDateTime criacao) {
        Cartao c = new Cartao();
        c.setTitular(titular);
        c.setTipo(tipo);
        c.setExcluido(excluido);
        c.setDataCriacao(criacao);
        return cartoes.save(c);
    }

    private Pedido pedido(Cliente cliente, LocalDateTime data, PedidoStatus status) {
        Pedido p = new Pedido();
        p.setCliente(cliente);
        p.setDataCadastro(data);
        p.setStatus(status);
        return p;
    }

    private void item(Pedido pedido, Produto produto, int quantidade, String valorUnitario) {
        ItemPedido i = new ItemPedido();
        i.setPedido(pedido);
        i.setProduto(produto);
        i.setQuantidade(quantidade);
        i.setValorUnitario(new BigDecimal(valorUnitario));
        pedido.getItens().add(i);
    }

    private void pagamento(Pedido pedido, Cartao cartao, String valor) {
        Pagamento g = new Pagamento();
        g.setPedido(pedido);
        g.setCartao(cartao);
        g.setValorPago(new BigDecimal(valor));
        pedido.getPagamentos().add(g);
    }
}
