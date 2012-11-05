package controle.mao.dados.dao;

import java.util.Date;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade de Despesas para armazenar os valores
 * 
 * @author camilas
 * 
 */
public class DespesasDAO {

	public static String[] colunas = new String[] { Despesas._ID, Despesas.LANCAMENTO, Despesas.DATA_VENCIMENTO, Despesas.FORMA_PAGTO, Despesas.TIPO_CARTAO, Despesas.CARTAO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public long idLancamento;
	public String dataVencimento;
	public String formaPagto;
	public String tipoCartao;
	public long id_cartao;

	public DespesasDAO() {
	}

	public DespesasDAO(long id_lancamento, String dataVencimento, String formaPagto, String tipo_cartao, long id_cartao) {
		super();	
		this.idLancamento = id_lancamento;
		this.dataVencimento = dataVencimento;
		this.formaPagto = formaPagto;
		this.tipoCartao = tipo_cartao;
		this.id_cartao = id_cartao;
	}

	public DespesasDAO(long id, long id_lancamento, String dataVencimento, String formaPagto, String tipo_cartao, long id_cartao) {
		super();
		this.id = id;
		this.idLancamento = id_lancamento;
		this.dataVencimento = dataVencimento;
		this.formaPagto = formaPagto;
		this.tipoCartao = tipo_cartao;
		this.id_cartao = id_cartao;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Despesas implements BaseColumns {
	
		// Não pode instanciar esta Classe
		private Despesas() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/despesas");
	
		// Mime Type para todos os cartoes
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.despesas";
	
		// Mime Type para um único cartao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.despesas";
	
		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String LANCAMENTO = "id_lancamento";
		public static final String DATA_VENCIMENTO = "data_vencimento";
		public static final String FORMA_PAGTO = "forma_pagto";
		public static final String TIPO_CARTAO = "tipo_cartao";		
		public static final String CARTAO = "id_cartao";
	
		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /cartoes
			Uri uriCartao = ContentUris.withAppendedId(Despesas.CONTENT_URI, id);
			return uriCartao;
		}
	}

	@Override
	public String toString() {
		return "Lancamento: " + idLancamento +" Data de Vencimento: " + dataVencimento + "Forma de Pagamento: "+ formaPagto +"Tipo do Cartão: " + tipoCartao + "Cartão: " + id_cartao;
	}
}
