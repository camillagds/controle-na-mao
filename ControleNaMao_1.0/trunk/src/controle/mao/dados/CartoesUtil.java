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
import controle.mao.dados.CartaoDAO.Cartoes;

/**
 * Tabela de Cartões
 * 
 * 
 * @author camilas
 * 
 */
public class CartoesUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_CARTOES = "tb_cartoes";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public CartoesUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected CartoesUtil() {
		// Apenas para criar uma subclasse...
	}

	// Salva o cartao, insere um novo ou atualiza
	public long salvar(CartaoDAO cartao) {
		long id = cartao.id;

		if (id != 0) {
			atualizar(cartao);
		} else {
			// Insere novo
			id = inserir(cartao);
		}

		return id;
	}

	// Insere um novo cartao
	public long inserir(CartaoDAO cartao) {
		ContentValues values = new ContentValues();
		values.put(Cartoes.NOME, cartao.nome_cartao);
		values.put(Cartoes.BANDEIRA, cartao.bandeira_Cartao);
		values.put(Cartoes.FECHAMENTO, cartao.fechaFatura_cartao);
		long id = inserir(values);
		return id;
	}

	// Insere um novo cartao
	public long inserir(ContentValues valores) {
		long id = db.insert(TABELA_CARTOES, "", valores);
		return id;
	}

	// Atualiza o cartao no banco. O id do cartao é utilizado.
	public int atualizar(CartaoDAO cartao) {
		ContentValues values = new ContentValues();
		values.put(Cartoes.NOME, cartao.nome_cartao);
		values.put(Cartoes.BANDEIRA, cartao.bandeira_Cartao);
		values.put(Cartoes.FECHAMENTO, cartao.fechaFatura_cartao);

		String _id = String.valueOf(cartao.id);

		String where = Cartoes._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o cartao com os valores abaixo
	// A cláusula where é utilizada para identificar o cartao a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(TABELA_CARTOES, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o cartao com o id fornecido
	public int deletar(long id) {
		String where = Cartoes._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o cartao com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(TABELA_CARTOES, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o cartao pelo id
	public CartaoDAO buscarCartao(long id) {
		// select * from cartao where _id=?
		Cursor c = db.query(true, TABELA_CARTOES, CartaoDAO.colunas, Cartoes._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			CartaoDAO cartao = new CartaoDAO();

			// Lê os dados
			cartao.id = c.getLong(0);
			cartao.nome_cartao = c.getString(1);
			cartao.bandeira_Cartao = c.getString(2);
			cartao.fechaFatura_cartao = c.getInt(3);
			
			
			return cartao;
		}

		return null;
	}

	// Retorna um cursor com todos os categorias
	public Cursor getCursor() {
		try {
			// select * from categorias
			return db.query(TABELA_CARTOES, CartaoDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os categorias: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	// Retorna uma lista com todos os categorias
	public List<CartaoDAO> listarCartao() {
		Cursor c = getCursor();

		List<CartaoDAO> categorias = new ArrayList<CartaoDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Cartoes._ID);
			int idxNome = c.getColumnIndex(Cartoes.NOME);
			int idxBandeira = c.getColumnIndex(Cartoes.BANDEIRA);
			int idxFechamento = c.getColumnIndex(Cartoes.FECHAMENTO);

			// Loop até o final
			do {
				CartaoDAO cartao = new CartaoDAO();
				categorias.add(cartao);

				// recupera os atributos de cartao
				cartao.id = c.getLong(idxId);
				cartao.nome_cartao = c.getString(idxNome);
				cartao.bandeira_Cartao = c.getString(idxBandeira);
				cartao.fechaFatura_cartao = c.getInt(idxFechamento);

			} while (c.moveToNext());
		}

		return categorias;
	}

	// Busca o cartao pelo nome "select * from cartao where nome=?"
	public CartaoDAO buscarCartaoPorNome(String nome) {
		CartaoDAO cartao = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_CARTOES, CartaoDAO.colunas, Cartoes.NOME + "='" + nome + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				cartao = new CartaoDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				cartao.id = c.getLong(0);
				cartao.nome_cartao = c.getString(1);
				cartao.bandeira_Cartao = c.getString(2);
				cartao.fechaFatura_cartao = c.getInt(3);
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o cartao pelo nome: " + e.toString());
			return null;
		}

		return cartao;
	}

	// Busca um cartao utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de cartao
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
