package controle.mao.dados.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controle.mao.dados.SQLiteHelper;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.LancamentoDAO.Lancamentos;
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
 * Tabela de Controle de lançamentos
 * 
 * 
 * @author camilas
 * 
 */
public class LancamentosUtil {
	private static final String CATEGORIA = "cnm";

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Nome das tabelas
	public static final String TABELA_LANCAMENTOS = "tb_lancamento";
	public static final String TABELA_RECEITAS = "tb_recebimento";
	public static final String TABELA_DESPESAS = "tb_despesa";

	private SQLiteHelper dbHelper;
	public SQLiteDatabase db;

	public LancamentosUtil(Context ctx) {
		// Abre o banco de dados já existente
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
	}

	protected LancamentosUtil() {
		// Apenas para criar uma subclasse...
	}

	// Busca o controle pelo id
	public LancamentoDAO buscarLancamento(long id) {
		// select * from controle where _id=?
		Cursor c = db.query(true, TABELA_LANCAMENTOS, LancamentoDAO.colunas, Lancamentos._ID + "=" + id, null, null, null, null, null);

		if (c.getCount() > 0) {

			// Posicinoa no primeiro elemento do cursor
			c.moveToFirst();

			LancamentoDAO lancamento = new LancamentoDAO();

			// Lê os dados
			lancamento.id = c.getLong(0);
			lancamento.tipoLancamento_lancamentos = c.getString(1);
			lancamento.descricao_lancamentos = c.getString(2);
			lancamento.idCategoria_lancamentos = c.getLong(3);
			lancamento.dataBaixa_lancamentos = c.getString(4);
			lancamento.valor_lancamentos = c.getFloat(5);
			lancamento.pago = c.getInt(6);

			return lancamento;
		}

		return null;
	}

	// Retorna um cursor com todos os controles
	public Cursor getCursor() {
		try {
			// select * from controles
			return db.query(TABELA_LANCAMENTOS, LancamentoDAO.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os controles: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}

	public Cursor getCursorCartoes() {
		try {
//			return db.query(TABELA_DESPESAS, new String [] {_ID, NAME, DEPT, CITY }, DEPT +"=?" +" AND " + CITY +"=?", new String[] {"Sales", "HCM" }, null, null, null);
		return null;
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar os controles: " + e.toString());
			return null;
		} catch (NullPointerException e){
			Log.e(CATEGORIA, "Banco Vazio: " + e.toString());
			return null;
		}
		
	}
	
	// Retorna uma lista com todos os controles
	public List<LancamentoDAO> listarLancamentos() {
		Cursor c = getCursor();

		List<LancamentoDAO> controles = new ArrayList<LancamentoDAO>();

		if (c.moveToFirst()) {

			// Recupera os índices das colunas
			int idxId = c.getColumnIndex(Lancamentos._ID);
			int idxTipoLancamento = c.getColumnIndex(Lancamentos.TIPO_LANCAMENTO);
			int idxDescricao = c.getColumnIndex(Lancamentos.DESCRICAO);
			int idxCategoria = c.getColumnIndex(Lancamentos.ID_CATEGORIA);
			int idxDataBaixa = c.getColumnIndex(Lancamentos.DATA_BAIXA);
			int idxValor = c.getColumnIndex(Lancamentos.VALOR);
			int idxPago = c.getColumnIndex(Lancamentos.PAGO);


			// Loop até o final
			do {
				LancamentoDAO controle = new LancamentoDAO();
				controles.add(controle);

				// recupera os atributos de controle
				controle.id = c.getLong(idxId);
				controle.tipoLancamento_lancamentos = c.getString(idxTipoLancamento);
				controle.descricao_lancamentos = c.getString(idxDescricao);
				controle.idCategoria_lancamentos = c.getInt(idxCategoria);
				controle.dataBaixa_lancamentos = c.getString(idxDataBaixa);
	            controle.valor_lancamentos = c.getFloat(idxValor);
	            controle.pago = c.getInt(idxPago);

			} while (c.moveToNext());
		}

		return controles;
	}
	
	public List<LancamentoDAO> listarLancamentosCartao(List<LancamentoDAO> list) {

		List<LancamentoDAO> controles = new ArrayList<LancamentoDAO>();

		Character tipoLancamento = 'D';
		Character tipoCartao = 'C';

		// Loop até o final
		for (int i = 0; i < list.size(); i++) {

			LancamentoDAO controle = new LancamentoDAO();
			DespesasDAO despesas = new DespesasDAO();
			char[] temp = null;
			char[] temp2 = null;
			try {
				temp = despesas.tipoCartao.toCharArray();
			} catch (Exception e) {
				temp[0] = 'x';
			}
			try {
				temp2 = controle.tipoLancamento_lancamentos.toCharArray();
			} catch (Exception e) {
				temp2[0] = 'x';
			}
			if (tipoLancamento == temp2[0]) {
				if (despesas.idLancamento == controle.id
						&& tipoCartao.equals(temp[0])) {
					controles.add(list.get(i));

				}
			}
		}

		return controles;
	}
	
	public int totalLancamentos(List<LancamentoDAO> lista, String tipoLancamento){
		int total = 0;
		for (int i = 0; i < lista.size(); i++){
			if (lista.get(i).tipoLancamento_lancamentos == tipoLancamento)
				total+= lista.get(i).valor_lancamentos;
		}
		return total;
	}
	
//TODO dar um jeito nisso ai
	// Busca o controle pelo nome "select * from controle where nome=?"
	public LancamentoDAO buscarControlePorData(String data) {
		LancamentoDAO lancamento = null;

		try {
			// Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
			Cursor c = db.query(TABELA_LANCAMENTOS, LancamentoDAO.colunas, Lancamentos.DATA_BAIXA + "='" + data + "'", null, null, null, null);

			// Se encontrou...
			if (c.moveToNext()) {

				lancamento = new LancamentoDAO();

				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
				lancamento.id = c.getLong(0);
				lancamento.tipoLancamento_lancamentos = c.getString(1);
				lancamento.descricao_lancamentos = c.getString(2);
				lancamento.idCategoria_lancamentos = c.getLong(3);
				lancamento.dataBaixa_lancamentos = c.getString(4);
				lancamento.valor_lancamentos = c.getFloat(5);				
				lancamento.pago = c.getInt(6);

			}
		} catch (SQLException e) {
			Log.e(CATEGORIA, "Erro ao buscar o controle pela data: " + e.toString());
			return null;
		}

		return lancamento;
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
