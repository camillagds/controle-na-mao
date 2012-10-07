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

	public static String[] colunas = new String[] { Despesas._ID, Despesas.DESCRICAO, Despesas.CATEGORIA, Despesas.TIPO_CARTAO, Despesas.MODALIDADE, Despesas.DATA_PAGAMENTO, Despesas.VALOR, Despesas.CARTAO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public String descricao;
	public long id_categorias;
	public String tipo_cartao;
	public String modalidade;
	public Date data_pagamento;
	public Float valor;
	public long id_cartao;
	

	public DespesasDAO() {
	}

	public DespesasDAO(String descricao, long id_categorias, String tipo_cartao, String modalidade, Date data_pagamento, Float valor, long parcelas, String periodo, long id_cartao) {
		super();
		
		this.descricao = descricao;
		this.id_categorias = id_categorias;
		this.tipo_cartao = tipo_cartao;
		this.modalidade = modalidade;
		this.data_pagamento = data_pagamento;
		this.valor = valor;
		this.id_cartao = id_cartao;
	}

	public DespesasDAO(long id, String descricao, long id_categorias, String tipo_cartao, String modalidade, Date data_pagamento, Float valor, long parcelas, String periodo, long id_cartao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.id_categorias = id_categorias;
		this.tipo_cartao = tipo_cartao;
		this.modalidade = modalidade;
		this.data_pagamento = data_pagamento;
		this.valor = valor;
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
		
		public static final String DESCRICAO = "descricao";
		public static final String CATEGORIA = "id_categoria";
		public static final String TIPO_CARTAO = "tipo_cartao";
		public static final String MODALIDADE = "modalidade";		
		public static final String DATA_PAGAMENTO = "data_pagamento";
		public static final String VALOR = "valor";
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
		return "Nome: " + descricao +"Categoria: " + id_categorias + "Tipo do Cartão: " + tipo_cartao + "Modalidade: " + modalidade + "Data para Pagamento: " + data_pagamento + "Valor: "+valor + "Cartão: " + id_cartao;
	}
}
