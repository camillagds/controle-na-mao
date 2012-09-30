package controle.mao.dados;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import controle.mao.dados.CategoriaDAO.Categorias;

/**
 * Tabela de Categoria
 * 
 * 
 * @author camilas
 * 
 */
public class CategoriasUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_CATEGORIAS = "tb_categorias";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public CategoriasUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
//		db = dbHelper.getWritableDatabase();

	}

	protected CategoriasUtil() {
		// Apenas para criar uma subclasse...
	}

	// Salva o categoria, insere um novo ou atualiza
	public long salvar(CategoriaDAO categoria) {
		long id = categoria.id;

		if (id != 0) {
			atualizar(categoria);
		} else {
			// Insere novo
			id = inserir(categoria);
		}

		return id;
	}

	// Insere um novo categoria
	public long inserir(CategoriaDAO categoria) {
		ContentValues values = new ContentValues();
		values.put(Categorias.NOME, categoria.nome_categoria);

		long id = inserir(values);
		return id;
	}

	// Insere um novo categoria
	public long inserir(ContentValues valores) {
		long id = db.insert(TABELA_CATEGORIAS, "", valores);
		return id;
	}

	// Atualiza o categoria no banco. O id do categoria é utilizado.
	public int atualizar(CategoriaDAO categoria) {
		ContentValues values = new ContentValues();
		values.put(Categorias.NOME, categoria.nome_categoria);

		String _id = String.valueOf(categoria.id);

		String where = Categorias._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o categoria com os valores abaixo
	// A cláusula where é utilizada para identificar o categoria a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(TABELA_CATEGORIAS, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o categoria com o id fornecido
	public int deletar(long id) {
		String where = Categorias._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o categoria com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(TABELA_CATEGORIAS, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o categoria pelo id
	public CategoriaDAO buscarCategoria(long id) {
		// select * from categoria where _id=?
		Cursor c = db.query(true, TABELA_CATEGORIAS, CategoriaDAO.colunas, Categorias._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			CategoriaDAO categoria = new CategoriaDAO();

			// Lê os dados
			categoria.id = c.getLong(0);
			categoria.nome_categoria = c.getString(1);

			return categoria;
		}

		return null;
	}

	// Retorna um cursor com todos os categorias
	public Cursor getCursor() {
		try {
			// select * from categorias
			return db.query(TABELA_CATEGORIAS, CategoriaDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os categorias: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	// Retorna uma lista com todos os categorias
	public List<CategoriaDAO> listarCategorias() {
		Cursor c = getCursor();

		List<CategoriaDAO> categorias = new ArrayList<CategoriaDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Categorias._ID);
			int idxNome = c.getColumnIndex(Categorias.NOME);

			// Loop até o final
			do {
				CategoriaDAO categoria = new CategoriaDAO();
				categorias.add(categoria);

				// recupera os atributos de categoria
				categoria.id = c.getLong(idxId);
				categoria.nome_categoria = c.getString(idxNome);

			} while (c.moveToNext());
		}

		return categorias;
	}

	// Busca o categoria pelo nome "select * from categoria where nome=?"
	public CategoriaDAO buscarCategoriaPorNome(String nome) {
		CategoriaDAO categoria = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_CATEGORIAS, CategoriaDAO.colunas, Categorias.NOME + "='" + nome + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				categoria = new CategoriaDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				categoria.id = c.getLong(0);
				categoria.nome_categoria = c.getString(1);
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o categoria pelo nome: " + e.toString());
			return null;
		}

		return categoria;
	}

	// Busca um categoria utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de categoria
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) {
		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}

	// Fecha o banco
	public void fechar() {
		// fecha o banco de dados
		if (db != null) {
			db.close();
		}
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
