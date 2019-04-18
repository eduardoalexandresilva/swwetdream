package com.somhodoce.api.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.somhodoce.api.model.PagamentoComBoleto;

@Service
public class boletoService {

	public void preecherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
