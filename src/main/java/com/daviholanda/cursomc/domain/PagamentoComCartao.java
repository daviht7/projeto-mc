package com.daviholanda.cursomc.domain;

import com.daviholanda.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Long numeroDeParcelas;

    public PagamentoComCartao() {

    }

    public PagamentoComCartao(Long numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Long numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Long getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Long numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
