package controle.mao.dados.dao;

import java.util.Date;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores
 * 
 * @author camilas
 * 
 */
public class ReceitasDAO {

	public static String[] colunas = new String[] { Receitas._ID, Receitas.ID_LANCAMENTO, Receitas.DATA_CREDITO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public long idLancamento_receitas;
	public Date dataCredito_receitas;

	public ReceitasDAO() {
	}

	public ReceitasDAO(long idLancamento, Date dataCredito) {
		super();
		this.idLancamento_receitas = idLancamento;
		this.dataCredito_receitas = dataCredito;
	}

	public ReceitasDAO(long id, long idLancamento, Date dataCredito) {
		super();
		this.id = id;
		this.idLancamento_receitas = idLancamento;
		this.dataCredito_receitas = dataCredito;
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
		
		public static final String ID_LANCAMENTO = "id_lancamento";
		public static final String DATA_CREDITO = "data_credito";

	
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
		return "Lancamento: " + idLancamento_receitas +" Data de Credito: " + dataCredito_receitas;
	}
}
