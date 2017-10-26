package controle.mao.dados.dao;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Classe entidade para armazenar os valores de Categoria
 * 
 * @author camilas
 * 
 */
public class CategoriaDAO {

	public static String[] colunas = new String[] { Categorias._ID, Categorias.NOME};

	/**
	 * Pacote do Content Provider. Precisa ser �nico.
	 */
	public static final String AUTHORITY = "controle.mao.provider.dados";

	public long id;
	public String nome_categoria;

	public CategoriaDAO() {
	}

	public CategoriaDAO(String nome) {
		super();
		this.nome_categoria = nome;
	}

	public CategoriaDAO(long id, String nome) {
		super();
		this.id = id;
		this.nome_categoria = nome;
	}

	/**
	 * Classe interna para representar as colunas e ser utilizada por um Content
	 * Provider
	 * 
	 * Filha de BaseColumns que j� define (_id e _count), para seguir o padr�o
	 * Android
	 */
	public static final class Categorias implements BaseColumns {
	
		// N�o pode instanciar esta Classe
		private Categorias() {
		}
	
		// content://br.livro.android.provider.carro/carros
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/categorias");
	
		// Mime Type para todos os carros
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.categorias";
	
		// Mime Type para um �nico carro
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.categorias";
	
		// Ordena��o default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
	
		public static final String NOME = "nome_categoria";
	
		// M�todo que constr�i uma Uri para um Carro espec�fico, com o seu id
		// A Uri � no formato "content://br.livro.android.provider.carro/carros/id"
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /categorias
			Uri uriCategoria = ContentUris.withAppendedId(Categorias.CONTENT_URI, id);
			return uriCategoria;
		}
	}

	@Override
	public String toString() {
		return "Nome: " + nome_categoria;
	}
}
