package controle.mao.dados;

import java.util.Date;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores
 * 
 * @author ricardo
 * 
 */
public class ReceitasDAO {

	public static String[] colunas = new String[] { Receitas._ID, Receitas.NOME, Receitas.CATEGORIA, Receitas.VALOR, Receitas.DATA_PAGAMENTO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public String nome_receitas;
	public String categoria_receitas;
	public float valor_receitas;
	public Date dataPagamento_receitas;

	public ReceitasDAO() {
	}

	public ReceitasDAO(String nome, String categoria, float valor, Date dataPagamento) {
		super();
		this.nome_receitas = nome;
		this.categoria_receitas = categoria;
		this.valor_receitas = valor;
		this.dataPagamento_receitas = dataPagamento;
	}

	public ReceitasDAO(long id, String nome, String categoria, float valor, Date dataPagamento) {
		super();
		this.id = id;
		this.nome_receitas = nome;
		this.categoria_receitas = categoria;
		this.valor_receitas = valor;
		this.dataPagamento_receitas = dataPagamento;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Receitas implements BaseColumns {
	
		// Não pode instanciar esta Classe
		private Receitas() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/receitas");
	
		// Mime Type para todos os cartoes
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.receitas";
	
		// Mime Type para um único cartao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.receitas";
	
		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "descricao";
		public static final String CATEGORIA = "id_categoria";
		public static final String VALOR = "valor";
		public static final String DATA_PAGAMENTO = "data_pagamento";

	
		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /cartoes
			Uri uriCartao = ContentUris.withAppendedId(Receitas.CONTENT_URI, id);
			return uriCartao;
		}
	}

	@Override
	public String toString() {
		return "Nome: " + nome_receitas +"Categoria: " + categoria_receitas + "Valor: " + valor_receitas + "Data Pagamento" + dataPagamento_receitas;
	}
}
