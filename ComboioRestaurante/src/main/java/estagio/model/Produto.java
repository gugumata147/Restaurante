/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estagio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Pereira
 */
@Entity
public class Produto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "pro_id")
	private Long id;
	@Column(name = "pro_nome", nullable = false, length = 100)
	private String nome;
	@Column(name = "pro_preco", nullable = false, precision = 2)
	private double preco;
	@Column(name = "pro_preco_compra", nullable = false, precision = 2)
	private double preco_compra;
	@JoinColumn(nullable = false)
	@ManyToOne
	private Fornecedor fornecedor;
	@JoinColumn(nullable = false)
	@ManyToOne
	private Categoria categoria;
	@Column(name = "pro_estoque", nullable = false)
	private int estoque;

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getPreco_compra() {
		return preco_compra;
	}

	public void setPreco_compra(double preco_compra) {
		this.preco_compra = preco_compra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Produto() {
	}

	public Produto(Long id, String nome, double preco, double preco_compra, Fornecedor fornecedor,
			Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.preco_compra = preco_compra;
		this.fornecedor = fornecedor;
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Produto) {
			Produto prod = (Produto) obj;
			if (prod.getNome().equals(this.nome) && prod.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}

}
