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
public class ControleDAO {

	public static String[] colunas = new String[] { Controles._ID, Controles.ID_DESPESA, Controles.ID_RECEBIMENTO, Controles.VALOR, Controles.DATA_BAIXA};

	/**
	 * Pacote do Content Provider. Precisa ser �nico.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public int idRecebimento_controles;
	public int idDespesa_controles;
	public Date dataBaixa_controles;
	public float valor_controles;

	public ControleDAO() {
	}

	public ControleDAO(int idrecebimento, int iddespesa, float valor, Date dataBaixa) {
		super();
		this.idRecebimento_controles = idrecebimento;
		this.idDespesa_controles = iddespesa;
		this.valor_controles = valor;
		this.dataBaixa_controles = dataBaixa;
	}

	public ControleDAO(long id, int idrecebimento, int iddespesa, float valor, Date dataBaixa) {
		super();
		this.id = id;
		this.idRecebimento_controles = idrecebimento;
		this.idDespesa_controles = iddespesa;
		this.valor_controles = valor;
		this.dataBaixa_controles = dataBaixa;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que j� define (_id e _count), para seguir o padr�o
	 * Android
	 */
	public static final class Controles implements BaseColumns {
	
		// N�o pode instanciar esta Classe
		private Controles() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/controles");
	
		// Mime Type para todos os cartoes
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.controles";
	
		// Mime Type para um �nico cartao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.controles";
	
		// Ordena��o default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		//TODO Colocar valores
		public static final String ID_DESPESA = "";
		public static final String ID_RECEBIMENTO = "";
		public static final String VALOR = "";
		public static final String DATA_BAIXA = "";

	
		// M�todo que constr�i uma Uri para um Carro espec�fico, com o seu id
		// A Uri � no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /cartoes
			Uri uriCartao = ContentUris.withAppendedId(Controles.CONTENT_URI, id);
			return uriCartao;
		}
	}

	@Override
	public String toString() {
		return "Recebimento: " + idRecebimento_controles +"Despesa: " + idDespesa_controles + "Valor: " + valor_controles + "Data Baixa" + dataBaixa_controles;
	}
}
