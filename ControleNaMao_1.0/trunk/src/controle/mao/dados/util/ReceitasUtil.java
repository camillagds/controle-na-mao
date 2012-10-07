package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.ReceitasDAO.Receitas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Tabela de Receitas
 * 
 * 
 * 
 * @author camilas
 * 
 */
public class ReceitasUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_RECEITAS = "tb_recebimento";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public ReceitasUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected ReceitasUtil() {
		// Apenas para criar uma subclasse...
	}

	// Salva o receita, insere um novo ou atualiza
	public long salvar(ReceitasDAO receita) {
		long id = receita.id;

		if (id != 0) {
			atualizar(receita);
		} else {
			// Insere novo
			id = inserir(receita);
		}

		return id;
	}

	// Insere um novo receita
	public long inserir(ReceitasDAO receita) {
		ContentValues values = new ContentValues();
		values.put(Receitas.NOME, receita.nome_receitas);
		values.put(Receitas.CATEGORIA, receita.categoria_receitas);
		values.put(Receitas.VALOR, receita.valor_receitas);
		values.put(Receitas.DATA_PAGAMENTO, receita.dataPagamento_receitas.toString());
		long id = inserir(values);
		return id;
	}

	// Insere um novo receita
	public long inserir(ContentValues valores) {
		long id = db.insert(TABELA_RECEITAS, "", valores);
		return id;
	}

	// Atualiza o receita no banco. O id do receita é utilizado.
	public int atualizar(ReceitasDAO receita) {
		ContentValues values = new ContentValues();
		values.put(Receitas.NOME, receita.nome_receitas);
		values.put(Receitas.CATEGORIA, receita.categoria_receitas);
		values.put(Receitas.VALOR, receita.valor_receitas);
		values.put(Receitas.DATA_PAGAMENTO, receita.dataPagamento_receitas.toString());
		

		String _id = String.valueOf(receita.id);

		String where = Receitas._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o receita com os valores abaixo
	// A cláusula where é utilizada para identificar o receita a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(TABELA_RECEITAS, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o receita com o id fornecido
	public int deletar(long id) {
		String where = Receitas._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o receita com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(TABELA_RECEITAS, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o receita pelo id
	public ReceitasDAO buscarReceitas(long id) {
		// select * from receita where _id=?
		Cursor c = db.query(true, TABELA_RECEITAS, ReceitasDAO.colunas, Receitas._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			ReceitasDAO receita = new ReceitasDAO();

			// Lê os dados
			receita.id = c.getLong(0);
			receita.nome_receitas = c.getString(1);
			receita.categoria_receitas = c.getString(2);
			receita.valor_receitas = c.getFloat(3);
			receita.dataPagamento_receitas = converteData(c, 4);
						
			return receita;
		}

		return null;
	}

	// Retorna um cursor com todos as receitas
	public Cursor getCursor() {
		try {
			// select * from receitas
			return db.query(TABELA_RECEITAS, ReceitasDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os categorias: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	// Retorna uma lista com todos as receitas
	public List<ReceitasDAO> listarReceitas() {
		Cursor c = getCursor();

		List<ReceitasDAO> receitas = new ArrayList<ReceitasDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Receitas._ID);
			int idxNome = c.getColumnIndex(Receitas.NOME);
			int idxCategoria = c.getColumnIndex(Receitas.CATEGORIA);
			int idxValor = c.getColumnIndex(Receitas.VALOR);
			int idxDataPagamento = c.getColumnIndex(Receitas.DATA_PAGAMENTO);

			// Loop até o final
			do {
				ReceitasDAO receita = new ReceitasDAO();
				receitas.add(receita);

				// recupera os atributos de receita
				receita.id = c.getLong(idxId);
				receita.nome_receitas = c.getString(idxNome);
				receita.categoria_receitas = c.getString(idxCategoria);
				receita.valor_receitas = c.getInt(idxValor);
	            receita.dataPagamento_receitas = converteData(c, idxDataPagamento);

			} while (c.moveToNext());
		}

		return receitas;
	}

	// Busca o receita pelo nome "select * from receita where nome=?"
	public ReceitasDAO buscarReceitaPorNome(String nome) {
		ReceitasDAO receita = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_RECEITAS, ReceitasDAO.colunas, Receitas.NOME + "='" + nome + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				receita = new ReceitasDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				receita.id = c.getLong(0);
				receita.nome_receitas = c.getString(1);
				receita.categoria_receitas = c.getString(2);
				receita.valor_receitas = c.getFloat(3);
	            receita.dataPagamento_receitas = converteData(c, 4);
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o receita pelo nome: " + e.toString());
			return null;
		}

		return receita;
	}
	
	public Date converteData(Cursor c,int posicao){
		//Pegando o valor do index da coluna dos tipos DATETIME
        String extr = c.getString(posicao);
        Date data = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        try {
        	data  = formato.parse(extr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
	}

	// Busca um receita utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de receita
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
