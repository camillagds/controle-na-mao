package controle.mao.controle.lancamentos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.DespesasUtil;
import controle.mao.visualizacao.TelaAddDespesas;
import controle.mao.visualizacao.TelaAddReceitas;

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
		LancamentoDAO lancamento = new LancamentoDAO();

		if (id != null) {
			// É uma atualização
			despesa.id = id;
		}
		//todo arrumar tudo isso ai
		lancamento.tipoLancamento_lancamentos = "D";
		lancamento.descricao_lancamentos = TelaAddDespesas.getTxtDescricaoDespesas().getText().toString();
		String temp = TelaAddDespesas.getNomeCategoriaBD();
		Log.e("cnm", TelaAddDespesas.getNomeCategoriaBD());
		lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
		lancamento.dataBaixa_lancamentos = null;
		lancamento.valor_lancamentos = valorDespesa;

		despesa.tipoCartao = TelaAddDespesas.getNomeTipoCartaoBD();
		despesa.formaPagto = TelaAddDespesas.nomeFormaPagtoBD;
		despesa.dataVencimento = converteDataString(TelaAddDespesas.getDtDebitoDespesas());
		despesa.id_cartao = Cartao.BuscarIdCartao(TelaAddDespesas.getNomeCartaoBD());
		
		// Salvar
		salvarDespesa(lancamento, despesa);
		Log.i("cnm", lancamento.toString());
		Log.i("cnm", despesa.toString());
		
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
	protected void salvarDespesa(LancamentoDAO lancamento, DespesasDAO despesa) {
		bdScript.salvar(lancamento, despesa);
	}

	// Excluir a receita
	protected void excluirDespesa(long id) {
		bdScript.deletar(id);
	}
	
    public String converteDataString(DatePicker campoData) {  	 
//    	campoData = TelaAddDespesas.getDtDebitoDespesas();
		Date data = new Date(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
	        String dataTransf = null;
	        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	        
//	        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	        formato.applyPattern("yyyy-MM-dd");
	        
	        dataTransf  = formato.format(data);
		
		return dataTransf;
	}
    
    public Date converteDataDate(DatePicker campoData) {  	 
//    	campoData = TelaAddReceitas.getDtCreditoReceitas();
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
    
	
	public List<DespesasDAO> listaDespesas() {
		// TODO Auto-generated method stub
		return bdScript.listarDespesas();
	}
}
