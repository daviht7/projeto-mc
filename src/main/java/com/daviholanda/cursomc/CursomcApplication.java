package com.daviholanda.cursomc;

import java.util.Arrays;

import com.daviholanda.cursomc.domain.*;
import com.daviholanda.cursomc.domain.enums.TipoCliente;
import com.daviholanda.cursomc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto produto1 = new Produto(null, "Computador",2000.00);

		Produto produto2 = new Produto(null, "Impressora", 800.00);

		Produto produto3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(produto1,produto2,produto3));
		cat2.getProdutos().addAll(Arrays.asList(produto2));

		produto1.getCategorias().addAll(Arrays.asList(cat1));
		produto2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		produto3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");

		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"Uberlândia",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(produto1,produto2,produto3));

		estadoRepository.saveAll(Arrays.asList(est1,est2));

		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

		Cliente cli1 = new Cliente(null,"Davi Holanda","daviht7@gmail.com","05880478300", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("85994185335","85991835144"));

		Endereco e1 = new Endereco(null,"rua santiago","279","B","Serrinha","60510476",cli1,c2);
		Endereco e2 = new Endereco(null,"rua xx","350","B","joaõ 23","58585878",cli1,c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

		clienteRepository.save(cli1);

		enderecoRepository.saveAll(Arrays.asList(e1,e2));

	}
	
	
}
