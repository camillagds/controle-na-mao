package controle.mao.controle.lancamentos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.util.CartoesUtil;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.DespesasUtil;
import controle.mao.visualizacao.TelaAddDespesas;

public class Despesa extends Activity{
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private static Long id;
	public static DespesasUtil bdScript;
	
//	private static CategoriasUtil bdScriptCategorias;
	
	public Despesa(DespesasUtil bd){
		Despesa.bdScript = bd;
	}

	public void salvar() {
		float valorDespesa = 0;
		try {
			valorDespesa = Float.parseFloat(TelaAddDespesas.getTxtValorDespesas().getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}
		
		DespesasDAO despesa = new DespesasDAO();
		if (id != null) {
			// É uma atualização
			despesa.id = id;
		}
		despesa.descricao = TelaAddDespesas.getTxtDescricaoCartao().getText().toString();
		String temp = TelaAddDespesas.getNomeCategoriaBD();
		Log.e("cnm", TelaAddDespesas.getNomeCategoriaBD());
		despesa.id_categorias = Categoria.bdScript.buscarCategoriaPorNome(temp).id;
		despesa.tipo_cartao = TelaAddDespesas.getNomeTipoCartaoBD();
		despesa.modalidade = TelaAddDespesas.nomeFormaPagtoBD;
		despesa.data_pagamento = converteData(TelaAddDespesas.getDtDebitoDespesas());
		despesa.valor = valorDespesa;
		despesa.id_cartao = Cartao.bdScript.buscarCartaoPorNome(TelaAddDespesas.nomeFormaPagtoBD).id;

		// Salvar
		salvarDespesa(despesa);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
		
		
	}

	public void excluir() {
		if (id != null) {
			excluirDespesa(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a receita pelo id
	protected DespesasDAO buscarDespesa(long id) {
		return bdScript.buscarDespesas(id);
	}

	// Salvar a receita
	protected void salvarDespesa(DespesasDAO despesa) {
		bdScript.salvar(despesa);
	}

	// Excluir a receita
	protected void excluirDespesa(long id) {
		bdScript.deletar(id);
	}
	
    public Date converteData(DatePicker campoData) {  	 
    	campoData = TelaAddDespesas.getDtDebitoDespesas();
		Date data = new Date(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
		return data;
	}

    public static void setCurrentDateOnView(DatePicker campoData) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		campoData.updateDate(year, month, day);
	}
    
	
	public List<DespesasDAO> listaReceitas() {
		// TODO Auto-generated method stub
		return bdScript.listarDespesas();
	}
}
