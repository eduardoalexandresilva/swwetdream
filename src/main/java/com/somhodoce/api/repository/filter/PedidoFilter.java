package com.somhodoce.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.somhodoce.api.model.Cliente;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoFilter {
	
	private Cliente cliente;
	
	@DateTimeFormat(pattern="yyy-MM-dd")
	private LocalDate data;
}
