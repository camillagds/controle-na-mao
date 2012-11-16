package controle.mao.dados.dao;

import java.util.Date;

import controle.mao.dados.util.LancamentosUtil;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores
 * 
 * @author camilas
 * 
 */
public class LancamentoDAO {

	public static String[] colunas = new String[] { Lancamentos._ID, Lancamentos.TIPO_LANCAMENTO, Lancamentos.DESCRICAO, Lancamentos.ID_CATEGORIA, Lancamentos.DATA_BAIXA, Lancamentos.VALOR, Lancamentos.PAGO};

	/**
	 * Pacote do Content Provider. Precisa ser único.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public String tipoLancamento_lancamentos;
	public String descricao_lancamentos;
	public long idCategoria_lancamentos;
	public String dataBaixa_lancamentos;
	public float valor_lancamentos;
	public int pago;

	public LancamentoDAO() {
	}

	public LancamentoDAO(String tipoLancamento, String descricao, int idCategoria, String dataBaixa, float valor, int pago) {
		super();
		this.tipoLancamento_lancamentos = tipoLancamento;
		this.descricao_lancamentos = descricao;
		this.idCategoria_lancamentos = idCategoria;
		this.dataBaixa_lancamentos = dataBaixa;
		this.valor_lancamentos = valor;
		this.pago = pago;
	}

	public LancamentoDAO(long id, String tipoLancamento, String descricao, int idCategoria, String dataBaixa, float valor, int pago) {
		super();
		this.id = id;
		this.tipoLancamento_lancamentos = tipoLancamento;
		this.descricao_lancamentos = descricao;
		this.idCategoria_lancamentos = idCategoria;
		this.dataBaixa_lancamentos = dataBaixa;
		this.valor_lancamentos = valor;
		this.pago = pago;
	}
	
	public LancamentoDAO(long id, int pago) {
		super();
		this.id = id;
		this.pago = pago;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que já define (_id e _count), para seguir o padrão
	 * Android
	 */
	public static final class Lancamentos implements BaseColumns {
	
		// Não pode instanciar esta Classe
		private Lancamentos() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/controles");
	
		// Mime Type para todos os cartoes
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.controles";
	
		// Mime Type para um único cartao
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.controles";
	
		// Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		public static final String TIPO_LANCAMENTO = "tipo_Lancamento";
		public static final String DESCRICAO = "descricao";
		public static final String ID_CATEGORIA = "id_categoria";
		public static final String DATA_BAIXA = "data_baixa";
		public static final String VALOR = "valor";
		public static final String PAGO = "pago";

		// Método que constrói uma Uri para um Carro específico, com o seu id
		// A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /cartoes
			Uri uriCartao = ContentUris.withAppendedId(Lancamentos.CONTENT_URI, id);
			return uriCartao;
		}
	}

	@Override
	public String toString() {
		return "Tipo de Lançamento: " + tipoLancamento_lancamentos + " Descricao: " + descricao_lancamentos +" Categoria: " + idCategoria_lancamentos + " Data Baixa: " + dataBaixa_lancamentos + "Valor: " + valor_lancamentos + " Pago: " + pago;
	}
}
