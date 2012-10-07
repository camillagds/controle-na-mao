package controle.mao.dados.dao;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores de Carro
 * 
 * @author camilas
 * 
 */
public class CartaoDAO {

	public static String[] colunas = new String[] { Cartoes._ID, Cartoes.NOME, Cartoes.BANDEIRA, Cartoes.FECHAMENTO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public String nome_cartao;
	public String bandeira_Cartao;
	public int fechaFatura_cartao;

	public CartaoDAO() {
	}

	public CartaoDAO(String nome, String bandeira, long vencimento, int fechamento) {
		super();
		this.nome_cartao = nome;
		this.bandeira_Cartao = bandeira;
		this.fechaFatura_cartao = fechamento;
	}

	public CartaoDAO(long id, String nome, String bandeira, int fechamento) {
		super();
		this.id = id;
		this.nome_cartao = nome;
		this.bandeira_Cartao = bandeira;
		this.fechaFatura_cartao = fechamento;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Cartoes implements BaseColumns {
	
		// Não pode instanciar esta Classe
		private Cartoes() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cartoes");
	
		// Mime Type para todos os cartoes
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.cartoes";
	
		// Mime Type para um único cartao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.cartoes";
	
		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome_cartao";
		public static final String BANDEIRA = "bandeira_Cartao";
		public static final String FECHAMENTO = "fechaFatura_cartao";

	
		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /cartoes
			Uri uriCartao = ContentUris.withAppendedId(Cartoes.CONTENT_URI, id);
			return uriCartao;
		}
	}

	@Override
	public String toString() {
		return "Nome: " + nome_cartao +"Bandeira: " + bandeira_Cartao + "Fechamento Fatura: " + fechaFatura_cartao;
	}
}
