package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.DespesasDAO.Despesas;

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
public class DespesasUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_DESPESAS = "tb_despesa";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public DespesasUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected DespesasUtil() {
		// Apenas para criar uma subclasse...
	}

	// Salva a despesa, insere uma nova ou atualiza
	public long salvar(DespesasDAO receita) {
		long id = receita.id;

		if (id != 0) {
			atualizar(receita);
		} else {
			// Insere novo
			id = inserir(receita);
		}

		return id;
	}

	// Insere uma nova despesa
	public long inserir(DespesasDAO despesa) {
		ContentValues values = new ContentValues();
		values.put(Despesas.DESCRICAO, despesa.descricao);
		values.put(Despesas.CATEGORIA, despesa.id_categorias);
		values.put(Despesas.TIPO_CARTAO, despesa.tipo_cartao);
		values.put(Despesas.MODALIDADE, despesa.modalidade);
		values.put(Despesas.DATA_PAGAMENTO, despesa.data_pagamento.toString());
		values.put(Despesas.VALOR, despesa.valor);
		values.put(Despesas.CARTAO, despesa.id_cartao);
		long id = inserir(values);
		return id;
	}

	// Insere uma nova despesa
	public long inserir(ContentValues valores) {
		long id = db.insert(TABELA_DESPESAS, "", valores);
		return id;
	}

	// Atualiza a despesa no banco. O id do receita é utilizado.
	public int atualizar(DespesasDAO despesa) {
		ContentValues values = new ContentValues();
		values.put(Despesas.DESCRICAO, despesa.descricao);
		values.put(Despesas.CATEGORIA, despesa.id_categorias);
		values.put(Despesas.TIPO_CARTAO, despesa.tipo_cartao);
		values.put(Despesas.MODALIDADE, despesa.modalidade);
		values.put(Despesas.DATA_PAGAMENTO, despesa.data_pagamento.toString());
		values.put(Despesas.VALOR, despesa.valor);
		values.put(Despesas.CARTAO, despesa.id_cartao);
		

		String _id = String.valueOf(despesa.id);

		String where = Despesas._ID + "=?";
		String[] whereArgs = new String[] { _id };

		int count = atualizar(values, where, whereArgs);

		return count;
	}

	// Atualiza a despesa com os valores abaixo
	// A cláusula where é utilizada para identificar o receita a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs) {
		int count = db.update(TABELA_DESPESAS, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta a despesa com o id fornecido
	public int deletar(long id) {
		String where = Despesas._ID + "=?";

		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };

		int count = deletar(where, whereArgs);

		return count;
	}

	// Deleta o receita com os argumentos fornecidos
	public int deletar(String where, String[] whereArgs) {
		int count = db.delete(TABELA_DESPESAS, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros");
		return count;
	}

	// Busca o receita pelo id
	public DespesasDAO buscarDespesas(long id) {
		// select * from receita where _id=?
		Cursor c = db.query(true, TABELA_DESPESAS, DespesasDAO.colunas, Despesas._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			DespesasDAO despesa = new DespesasDAO();

			// Lê os dados
			despesa.id = c.getLong(0);
			despesa.descricao = c.getString(1);
			despesa.id_categorias = c.getInt(2);
			despesa.tipo_cartao = c.getString(3);
			despesa.modalidade = c.getString(4);
			despesa.data_pagamento = converteData(c, 5);
			despesa.valor = c.getFloat(6);		
			despesa.id_cartao = c.getInt(7);
			
			return despesa;
		}

		return null;
	}

	// Retorna um cursor com todos as receitas
	public Cursor getCursor() {
		try {
			// select * from receitas
			return db.query(TABELA_DESPESAS, DespesasDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar as despesas: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	// Retorna uma lista com todos as receitas
	public List<DespesasDAO> listarDespesas() {
		Cursor c = getCursor();

		List<DespesasDAO> despesas = new ArrayList<DespesasDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Despesas._ID);
			int idxDescricao = c.getColumnIndex(Despesas.DESCRICAO);
			int idxCategoria = c.getColumnIndex(Despesas.CATEGORIA);
			int idxTipoCartao = c.getColumnIndex(Despesas.TIPO_CARTAO);
			int idxModalidade = c.getColumnIndex(Despesas.MODALIDADE);
			int idxDataPagamento = c.getColumnIndex(Despesas.DATA_PAGAMENTO);
			int idxValor = c.getColumnIndex(Despesas.VALOR);
			int idxCartao = c.getColumnIndex(Despesas.CARTAO);
			// Loop até o final
			do {
				DespesasDAO receita = new DespesasDAO();
				despesas.add(receita);

				// recupera os atributos de receita
				receita.id = c.getLong(idxId);
				receita.descricao = c.getString(idxDescricao);
				receita.id_categorias = c.getInt(idxCategoria);
				receita.tipo_cartao = c.getString(idxTipoCartao);
				receita.modalidade = c.getString(idxModalidade);
	            receita.data_pagamento = converteData(c, idxDataPagamento);
	            receita.valor = c.getFloat(idxValor);
	            receita.id_cartao = c.getInt(idxCartao);
	            
			} while (c.moveToNext());
		}

		return despesas;
	}

	// Busca a despesa pelo nome "select * from tb_despesas where descricao=?"
	public DespesasDAO buscarDespesaPorNome(String nome) {
		DespesasDAO despesa = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_DESPESAS, DespesasDAO.colunas, Despesas.DESCRICAO + "='" + nome + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				despesa = new DespesasDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				despesa.id = c.getLong(0);
				despesa.descricao = c.getString(1);
				despesa.id_categorias = c.getInt(2);
				despesa.tipo_cartao = c.getString(3);
				despesa.modalidade = c.getString(4);
				despesa.data_pagamento = converteData(c, 5);
				despesa.valor = c.getFloat(6);		
				despesa.id_cartao = c.getInt(7);
				
			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar a despesa pelo nome: " + e.toString());
			return null;
		}

		return despesa;
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
