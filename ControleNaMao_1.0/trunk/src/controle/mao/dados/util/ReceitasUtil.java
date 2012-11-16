package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.DespesasDAO.Despesas;
import controle.mao.dados.dao.LancamentoDAO.Lancamentos;
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

	private static final String TABELA_LANCAMENTO = "tb_lancamento";

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
	public long salvar(LancamentoDAO lancamento, ReceitasDAO receita) {
		long id = receita.id;

		if (id != 0) {
			atualizar(lancamento, receita);
		} else {
			// Insere novo
			id = inserir(lancamento, receita);
		}

		return id;
	}

	// Insere um novo receita
	public long inserir(LancamentoDAO lancamento, ReceitasDAO receita) {
		ContentValues valuesL = new ContentValues();
		valuesL.put(Lancamentos.TIPO_LANCAMENTO, lancamento.tipoLancamento_lancamentos);
		valuesL.put(Lancamentos.DESCRICAO, lancamento.descricao_lancamentos);
		valuesL.put(Lancamentos.ID_CATEGORIA, lancamento.idCategoria_lancamentos);
		valuesL.put(Lancamentos.DATA_BAIXA, receita.dataCredito_receitas.toString());
		valuesL.put(Lancamentos.VALOR, lancamento.valor_lancamentos);
		long idL = inserir(valuesL,TABELA_LANCAMENTO);
		
		ContentValues valuesR = new ContentValues();
		receita.idLancamento_receitas = idL;
		valuesR.put(Receitas.ID_LANCAMENTO, idL);
		valuesR.put(Receitas.DATA_CREDITO, receita.dataCredito_receitas.toString());
		long idR = inserir(valuesR,TABELA_RECEITAS);
		return idR;
	}

	// Insere um novo receita
	public long inserir(ContentValues valores, String tabela) {
		long id = db.insert(tabela, "", valores);
		return id;
	}

	// Atualiza o receita no banco. O id do receita é utilizado.
	public int atualizar(LancamentoDAO lancamento, ReceitasDAO receita) {
		ContentValues valuesL = new ContentValues();
		Log.i("cnm",receita.toString());
		valuesL.put(Lancamentos.TIPO_LANCAMENTO, lancamento.tipoLancamento_lancamentos);
		valuesL.put(Lancamentos.DESCRICAO, lancamento.descricao_lancamentos);
		valuesL.put(Lancamentos.ID_CATEGORIA, lancamento.idCategoria_lancamentos);
		valuesL.put(Lancamentos.DATA_BAIXA, lancamento.dataBaixa_lancamentos.toString());
		valuesL.put(Lancamentos.VALOR, lancamento.valor_lancamentos);
		valuesL.put(Lancamentos.PAGO, lancamento.pago);
		
		ContentValues valuesR = new ContentValues();
		valuesR.put(Receitas.ID_LANCAMENTO, receita.idLancamento_receitas);
		valuesR.put(Receitas.DATA_CREDITO, receita.dataCredito_receitas.toString());

		String _idL = String.valueOf(lancamento.id);
		String _idR = String.valueOf(receita.id);

		// Atualliza Lancamentos
		String whereL = Lancamentos._ID + "=?";
		String[] whereArgsL = new String[] { _idL };
		int countL = atualizar(TABELA_LANCAMENTO, valuesL, whereL, whereArgsL);

		// Atualliza Recebimentos
		String whereR = Receitas._ID + "=?";
		String[] whereArgsR = new String[] { _idR };
		int countR = atualizar(TABELA_RECEITAS, valuesR, whereR, whereArgsR);
		
		return countR;
	}

	// Atualiza o receita com os valores abaixo
	// A cláusula where é utilizada para identificar o receita a ser atualizado
	public int atualizar(String tabela, ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(tabela, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta o receita com o id fornecido
	public int deletar(long idL, long idR) {
		String whereL = Lancamentos._ID + "=?";
		String whereR = Receitas._ID + "=?";

		String _idL = String.valueOf(idL);
		String _idR = String.valueOf(idR);

		String[] whereArgsL = new String[] { _idL };
		String[] whereArgsR = new String[] { _idR };
		
		int count2 = deletar(TABELA_RECEITAS,whereR, whereArgsR);
		int count = deletar(TABELA_LANCAMENTO,whereL, whereArgsL);
		return count;
	}

	// Deleta o receita com os argumentos fornecidos
	public int deletar(String tabela, String where, String[] whereArgs) {
		int count = db.delete(tabela, where, whereArgs);
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
			receita.idLancamento_receitas = c.getLong(1);
			receita.dataCredito_receitas = c.getString(2);

			return receita;
		}

		return null;
	}
	
	public ReceitasDAO buscarLancamentoReceitas(long id) {
		// select * from receita where _id=?
		Cursor c = db.query(true, TABELA_RECEITAS, ReceitasDAO.colunas, Receitas.ID_LANCAMENTO + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			ReceitasDAO receita = new ReceitasDAO();

			// Lê os dados		
			receita.id = c.getLong(0);
			receita.idLancamento_receitas = c.getLong(1);
			receita.dataCredito_receitas = c.getString(2);

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
			int idxLancamento = c.getColumnIndex(Receitas.ID_LANCAMENTO);
			int idxDataCredito = c.getColumnIndex(Receitas.DATA_CREDITO);

			// Loop até o final
			do {
				ReceitasDAO receita = new ReceitasDAO();
				receitas.add(receita);

				// recupera os atributos de receita
				receita.id = c.getLong(idxId);
				receita.idLancamento_receitas = c.getLong(idxLancamento);
	            receita.dataCredito_receitas = c.getString(idxDataCredito);

			} while (c.moveToNext());
		}

		return receitas;
	}

	// Busca o receita pelo nome "select * from receita where nome=?"
	public ReceitasDAO buscarReceitaPorNome(String nome) {
		ReceitasDAO receita = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_RECEITAS, ReceitasDAO.colunas, Receitas.ID_LANCAMENTO + "='" + nome + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				receita = new ReceitasDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				receita.id = c.getLong(0);
				receita.idLancamento_receitas = c.getLong(1);
				receita.dataCredito_receitas = c.getString(2);
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
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
        	data  = formato.parse(extr);
        } catch (ParseException e) {
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
