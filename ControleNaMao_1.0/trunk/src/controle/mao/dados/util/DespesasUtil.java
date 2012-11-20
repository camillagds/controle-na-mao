package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.DespesasDAO.Despesas;
import controle.mao.dados.dao.LancamentoDAO;
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
public class DespesasUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_DESPESAS = "tb_despesa";
	public static final String TABELA_LANCAMENTOS = "tb_lancamento";
	
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
	public long salvar(LancamentoDAO lancamento, DespesasDAO receita) {
		long id = receita.id;

		if (id != 0) {
			atualizar(lancamento, receita);
		} else {
			// Insere novo
			id = inserir(lancamento, receita);
		}

		return id;
	}

	// Insere uma nova despesa
	public long inserir(LancamentoDAO lancamento, DespesasDAO despesa) {
		
		ContentValues valuesL = new ContentValues();
		valuesL.put(Lancamentos.TIPO_LANCAMENTO, lancamento.tipoLancamento_lancamentos);
		valuesL.put(Lancamentos.DESCRICAO, lancamento.descricao_lancamentos);
		valuesL.put(Lancamentos.ID_CATEGORIA, lancamento.idCategoria_lancamentos);
		valuesL.put(Lancamentos.DATA_BAIXA, despesa.dataVencimento.toString());
		valuesL.put(Lancamentos.VALOR, lancamento.valor_lancamentos);
		long idL = inserir(TABELA_LANCAMENTOS,valuesL);
		
		ContentValues valuesD = new ContentValues();
		despesa.idLancamento = idL;
		valuesD.put(Despesas.ID_LANCAMENTO, idL);
		valuesD.put(Despesas.DATA_VENCIMENTO, despesa.dataVencimento.toString());
		valuesD.put(Despesas.FORMA_PAGTO, despesa.formaPagto);
		valuesD.put(Despesas.TIPO_CARTAO, despesa.tipoCartao);
		valuesD.put(Despesas.CARTAO, despesa.id_cartao);
		long idD = inserir(TABELA_DESPESAS,valuesD);
		return idD;
	}

	// Insere uma nova despesa
	public long inserir(String tabela, ContentValues valores) {
		long id = db.insert(tabela, "", valores);
		return id;
	}

	// Atualiza a despesa no banco. O id do receita é utilizado.
	public int atualizar(LancamentoDAO lancamento, DespesasDAO despesa) {
		ContentValues valuesL = new ContentValues();
		valuesL.put(Lancamentos.TIPO_LANCAMENTO, lancamento.tipoLancamento_lancamentos);
		valuesL.put(Lancamentos.DESCRICAO, lancamento.descricao_lancamentos);
		valuesL.put(Lancamentos.ID_CATEGORIA, lancamento.idCategoria_lancamentos);
		valuesL.put(Lancamentos.DATA_BAIXA, lancamento.dataBaixa_lancamentos.toString());
		valuesL.put(Lancamentos.VALOR, lancamento.valor_lancamentos);
		valuesL.put(Lancamentos.PAGO, lancamento.pago);
		
		ContentValues valuesD = new ContentValues();
		valuesD.put(Despesas.ID_LANCAMENTO, despesa.idLancamento);
		valuesD.put(Despesas.DATA_VENCIMENTO, despesa.dataVencimento.toString());
		valuesD.put(Despesas.FORMA_PAGTO, despesa.formaPagto);
		valuesD.put(Despesas.TIPO_CARTAO, despesa.tipoCartao);
		valuesD.put(Despesas.CARTAO, despesa.id_cartao);
		
		String _idL = String.valueOf(lancamento.id);
		String _idD = String.valueOf(despesa.id);

		// Atualliza Lancamentos
		String whereL = Lancamentos._ID + "=?";
		String[] whereArgsL = new String[] { _idL };
		int countL = atualizar(valuesL, whereL, whereArgsL,TABELA_LANCAMENTOS);

		// Atualliza Recebimentos
		String whereD = Despesas._ID + "=?";
		String[] whereArgsD = new String[] { _idD };
		int countD = atualizar(valuesD, whereD, whereArgsD,TABELA_DESPESAS);

		return countD;
	}

	// Atualiza a despesa com os valores abaixo
	// A cláusula where é utilizada para identificar o receita a ser atualizado
	public int atualizar(ContentValues valores, String where, String[] whereArgs,String tabela) {
		int count = db.update(tabela, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
	}

	// Deleta a despesa com o id fornecido
	public int deletar(long idL, long idD) {
		String whereL = Lancamentos._ID + "=?";
		String whereD = Despesas._ID + "=?";

		String _idL = String.valueOf(idL);
		String _idD = String.valueOf(idD);

		String[] whereArgsL = new String[] { _idL };
		String[] whereArgsD = new String[] { _idD };
		
		int count2 = deletar(TABELA_DESPESAS,whereD, whereArgsD);
		int count = deletar(TABELA_LANCAMENTOS,whereL, whereArgsL);
		return count;
	}

	// Deleta o receita com os argumentos fornecidos
	public int deletar(String tabela, String where, String[] whereArgs) {
		int count = db.delete(tabela, where, whereArgs);
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
			despesa.idLancamento = c.getLong(1);
			despesa.dataVencimento = c.getString(2);
			despesa.formaPagto = c.getString(3);
			despesa.tipoCartao = c.getString(4);
			despesa.id_cartao = c.getLong(5);
			
			return despesa;
		}

		return null;
	}
	
	
	
	public DespesasDAO buscarLancamentoDespesas(Long id) {
		// select * from receita where _id=?
				Cursor c = db.query(true, TABELA_DESPESAS, DespesasDAO.colunas, Despesas.ID_LANCAMENTO + "=" + id, null, null, null, null, null);

				if (c.getCount() > 0) {

					// Posicinoa no primeiro elemento do cursor
					c.moveToFirst();

					DespesasDAO despesa = new DespesasDAO();

					// Lê os dados
					despesa.id = c.getLong(0);
					despesa.idLancamento = c.getLong(1);
					despesa.dataVencimento = c.getString(2);
					despesa.formaPagto = c.getString(3);
					despesa.tipoCartao = c.getString(4);
					despesa.id_cartao = c.getLong(5);
					
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
			int idxLancamento = c.getColumnIndex(Despesas.ID_LANCAMENTO);
			int idxDataVencimento = c.getColumnIndex(Despesas.DATA_VENCIMENTO);
			int idxFormaPagto = c.getColumnIndex(Despesas.FORMA_PAGTO);
			int idxTipoCartao = c.getColumnIndex(Despesas.TIPO_CARTAO);
			int idxCartao = c.getColumnIndex(Despesas.CARTAO);
			// Loop até o final
			do {
				DespesasDAO despesa = new DespesasDAO();
				despesas.add(despesa);

				// recupera os atributos de receita
				despesa.id = c.getLong(idxId);
				despesa.idLancamento = c.getLong(idxLancamento);
				despesa.dataVencimento = c.getString(idxDataVencimento);
				despesa.formaPagto = c.getString(idxFormaPagto);
				despesa.tipoCartao = c.getString(idxTipoCartao);
	            despesa.id_cartao = c.getLong(idxCartao);
	            
			} while (c.moveToNext());
		}

		return despesas;
	}

	// Busca a despesa pelo nome "select * from tb_despesas where descricao=?"
	public DespesasDAO buscarDespesaPorNome(String nome) {
		DespesasDAO despesa = null;
//
//		try {
//			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
//			Cursor c = db.query(TABELA_DESPESAS, DespesasDAO.colunas, Despesas.DESCRICAO + "='" + nome + "'", null, null, null, null);
//
//			// Se encontrou...
//			if (c.moveToNext()) {
//
//				despesa = new DespesasDAO();
//
//				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
//				despesa.id = c.getLong(0);
//				despesa.idLancamento = c.getLong(1);
//				despesa.dataVencimento = converteData(c,2);
//				despesa.formaPagto = c.getString(3);
//				despesa.tipoCartao = c.getString(4);
//				despesa.id_cartao = c.getInt(5);
//				
//			}
//		} catch (SQLException e) {
//			Log.e(CATEGORIA, "Erro ao buscar a despesa pelo nome: " + e.toString());
//			return null;
//		}
//
		return despesa;
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
