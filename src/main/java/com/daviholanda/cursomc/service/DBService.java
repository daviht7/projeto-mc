package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.*;
import com.daviholanda.cursomc.domain.enums.EstadoPagamento;
import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instantiateTestDatabase() throws ParseException {


        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama,mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinaria");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto produto1 = new Produto(null, "Computador", 2000.00);
        Produto produto2 = new Produto(null, "Impressora", 800.00);
        Produto produto3 = new Produto(null, "Mouse", 80.00);
        Produto produto4 = new Produto(null, "Mesa de escritório", 300.00);
        Produto produto5 = new Produto(null, "Toalha", 50.00);
        Produto produto6 = new Produto(null, "Colcha", 200.00);
        Produto produto7 = new Produto(null, "TV true color", 1200.00);
        Produto produto8 = new Produto(null, "Roçadeira", 800.00);
        Produto produto9 = new Produto(null, "Abajour", 100.00);
        Produto produto10 = new Produto(null, "Pendente", 180.00);
        Produto produto11 = new Produto(null, "Shampoo", 90.00);

        cat1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
        cat2.getProdutos().addAll(Arrays.asList(produto2,produto4));
        cat3.getProdutos().addAll(Arrays.asList(produto5,produto6));
        cat4.getProdutos().addAll(Arrays.asList(produto1,produto2,produto3,produto7));
        cat5.getProdutos().addAll(Arrays.asList(produto8));
        cat6.getProdutos().addAll(Arrays.asList(produto9,produto10));
        cat7.getProdutos().addAll(Arrays.asList(produto11));

        produto1.getCategorias().addAll(Arrays.asList(cat1));
        produto2.getCategorias().addAll(Arrays.asList(cat1, cat2,cat4));
        produto3.getCategorias().addAll(Arrays.asList(cat1,cat4));
        produto4.getCategorias().addAll(Arrays.asList(cat2));
        produto5.getCategorias().addAll(Arrays.asList(cat3));
        produto6.getCategorias().addAll(Arrays.asList(cat3));
        produto7.getCategorias().addAll(Arrays.asList(cat5));
        produto8.getCategorias().addAll(Arrays.asList(cat5));
        produto9.getCategorias().addAll(Arrays.asList(cat6));
        produto10.getCategorias().addAll(Arrays.asList(cat6));
        produto11.getCategorias().addAll(Arrays.asList(cat7));

        Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Uberlândia", est1);
        Cidade c2 = new Cidade(null, "Uberlândia", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3,produto4,produto5,produto6,produto7,produto8,produto9,produto10,produto11));

        estadoRepository.saveAll(Arrays.asList(est1, est2));

        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Davi Holanda", "daviht7@gmail.com", "05880478300", TipoCliente.PESSOAFISICA,bCryptPasswordEncoder.encode("davi8080"));
        cli1.getTelefones().addAll(Arrays.asList("85994185335", "85991835144"));

        Endereco e1 = new Endereco(null, "rua santiago", "279", "B", "Serrinha", "60510476", cli1, c2);
        Endereco e2 = new Endereco(null, "rua xx", "350", "B", "joaõ 23", "58585878", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.save(cli1);

        enderecoRepository.saveAll(Arrays.asList(e1, e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);

        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6L);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));

        pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));

        ItemPedido ip1 = new ItemPedido(ped1,produto1,0.00,1,2000.00);
        ItemPedido ip2 = new ItemPedido(ped1,produto3,0.00,2,80.00);
        ItemPedido ip3 = new ItemPedido(ped2,produto2,100.00,1,800.00);

        ped1.getItems().addAll(Arrays.asList(ip1,ip2));
        ped2.getItems().addAll(Arrays.asList(ip3));

        produto1.getItems().addAll(Arrays.asList(ip1));
        produto2.getItems().addAll(Arrays.asList(ip3));
        produto3.getItems().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

    }
}
