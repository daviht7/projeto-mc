package com.daviholanda.cursomc.service;

import com.daviholanda.cursomc.domain.*;
import com.daviholanda.cursomc.domain.enums.EstadoPagamento;
import com.daviholanda.cursomc.dto.CategoriaDTO;
import com.daviholanda.cursomc.repository.ItemPedidoRepository;
import com.daviholanda.cursomc.repository.PagamentoRepository;
import com.daviholanda.cursomc.repository.PedidoRepository;
import com.daviholanda.cursomc.security.UserSS;
import com.daviholanda.cursomc.service.exceptions.AuthorizationException;
import com.daviholanda.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(() -> new ObjectNotFoundException(String.format("Objeto não encontrado! Id : %s, do tipo: %s", id, Pedido.class.getName())));
    }

    @Transactional
    public Pedido insert(Pedido pedido) {

        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItems()) {
            ip.setDesconto(0.0);
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
            ip.setPedido(pedido);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
        }

        itemPedidoRepository.saveAll(pedido.getItems());

        //emailService.sendOrderConfirmationEmail(pedido);
        emailService.sendOrderConfirmationHtmlEmail(pedido);

        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();

        if(user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.find(user.getId().longValue());

        return pedidoRepository.findByCliente(cliente,pageRequest);

    }

}
