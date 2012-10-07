package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.ControleDAO;
import controle.mao.dados.dao.ControleDAO.Controles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * Tabela de Controle de lançamentos
 * 
 * 
 * @author camilas
 * 
 */
public class ControleUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_CONTROLE = "tb_controle";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public ControleUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected ControleUtil() {
		// Apenas para criar uma subclasse...
	}

	// Salva o controle, insere um novo ou atualiza
	public long salvar(ControleDAO controle) {
		long id = controle.id;

		if (id != 0) {
			atualizar(controle);
		} else {
			// Insere novo
			id = inserir(controle);
		}

		return id;
	}

	// Insere um novo controle
	public long inserir(ControleDAO controle) {
		ContentValues values = new ContentValues();
		values.put(Controles.ID_RECEBIMENTO, controle.idRecebimento_controles);
		values.put(Controles.ID_DESPESA, controle.idDespesa_controles);
		values.put(Controles.DATA_BAIXA, controle.dataBaixa_controles.toString());
		values.put(Controles.VALOR, controle.valor_controles);
		long id = inserir(values);
		return id;
	}

	// Insere um novo controle
	public long inserir(ContentValues valores) {
		long id = db.insert(TABELA_CONTROLE, "", valores);
		return id;
	}

	// Atualiza o controle no banco. O id do controle é utilizado.
	public int atualizar(ControleDAO controle) {
		ContentValues values = new ContentValues();
		values.put(Controles.ID_RECEBIMENTO, controle.idRecebimento_controles);
		values.put(Controles.ID_DESPESA, controle.idDespesa_controles);
		values.put(Controles.VALOR, controle.valor_controles);
		values.put(Controles.DATA_BAIXA, controle.dataBaixa_controles.toString());
		

		String _id = String.valueOf(controle.id);

		String where = Controles._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza o controle com os valores abaixo
	// A cláusula where é utilizada para identificar o controle a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(TABELA_CONTROLE, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o controle com o id fornecido
	public int deletar(long id) {
		String where = Controles._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o controle com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(TABELA_CONTROLE, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o controle pelo id
	public ControleDAO buscarControle(long id) {
		// select * from controle where _id=?
		Cursor c = db.query(true, TABELA_CONTROLE, ControleDAO.colunas, Controles._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			ControleDAO controle = new ControleDAO();

			// Lê os dados
			controle.id = c.getLong(0);
			controle.idRecebimento_controles = c.getInt(1);
			controle.idDespesa_controles = c.getInt(2);
			controle.valor_controles = c.getFloat(3);
            controle.dataBaixa_controles = converteData(c, 4);			
			return controle;
		}

		return null;
	}

	// Retorna um cursor com todos os controles
	public Cursor getCursor() {
		try {
			// select * from controles
			return db.query(TABELA_CONTROLE, ControleDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os controles: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	// Retorna uma lista com todos os controles
	public List<ControleDAO> listarControle() {
		Cursor c = getCursor();

		List<ControleDAO> controles = new ArrayList<ControleDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Controles._ID);
			int idxIdRecebimentos = c.getColumnIndex(Controles.ID_RECEBIMENTO);
			int idxIdDespesas = c.getColumnIndex(Controles.ID_DESPESA);
			int idxValor = c.getColumnIndex(Controles.VALOR);
			int idxDataBaixa = c.getColumnIndex(Controles.DATA_BAIXA);

			// Loop até o final
			do {
				ControleDAO controle = new ControleDAO();
				controles.add(controle);

				// recupera os atributos de controle
				controle.id = c.getLong(idxId);
				controle.idRecebimento_controles = c.getInt(idxIdRecebimentos);
				controle.idDespesa_controles = c.getInt(idxIdDespesas);
				controle.valor_controles = c.getInt(idxValor);
	            controle.dataBaixa_controles = converteData(c, idxDataBaixa);

			} while (c.moveToNext());
		}

		return controles;
	}
//TODO dar um jeito nisso ai
	// Busca o controle pelo nome "select * from controle where nome=?"
	public ControleDAO buscarControlePorData(String data) {
		ControleDAO controle = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_CONTROLE, ControleDAO.colunas, Controles.DATA_BAIXA + "='" + data + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				controle = new ControleDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				controle.id = c.getLong(0);
				controle.idRecebimento_controles = c.getInt(1);
				controle.idDespesa_controles = c.getInt(2);
				controle.valor_controles = c.getFloat(3);
	            controle.dataBaixa_controles = converteData(c, 4);		
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o controle pela data: " + e.toString());
			return null;
		}

		return controle;
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

	// Busca um controle utilizando as configurações definidas no
	// SQLiteQueryBuilder
	// Utilizado pelo Content Provider de controle
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
