package controle.mao.controle.lancamentos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.ReceitasDAO;
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
		lancamento.pago = 0;
		despesa.tipoCartao = TelaAddDespesas.getNomeTipoCartaoBD();
		despesa.formaPagto = TelaAddDespesas.nomeFormaPagtoBD;
		despesa.dataVencimento = converteDataString(TelaAddDespesas.getDtDebitoDespesas());
		try {
			despesa.id_cartao = Cartao.BuscarIdCartao(TelaAddDespesas.getNomeCartaoBD());
		} catch (Exception e) {
			despesa.id_cartao = (Long) null;
		}
		
		// Salvar
		salvarDespesa(lancamento, despesa);
		Log.i("cnm", lancamento.toString());
		Log.i("cnm", despesa.toString());
		
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
	protected void excluirDespesa(long idL, long idD) {
		bdScript.deletar(idL, idD);
	}
	
    public static String converteDataString(DatePicker campoData) {  	 
    	Calendar data = Calendar.getInstance();
		data.set(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
	    String format = "dd/MM/yyyy";
	    String dataTransf = String.valueOf(DateFormat.format(format, data));
		return dataTransf;
	}
    
    public static Calendar ConvertToDateBR(String dateString){  

    	//de STRING para CALENDAR

    	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

    	String data = dateString;

    	Calendar c = Calendar.getInstance();

    	try {
			c.setTime(formatoData.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return c;
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
		return bdScript.listarDespesas();
	}
	
	public DespesasDAO buscarLancamentoDespesas(Long id) {
		return bdScript.buscarLancamentoDespesas(id);
	}
}
