package controle.mao.controle.lancamentos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.util.ReceitasUtil;
import controle.mao.visualizacao.TelaAddDespesas;
import controle.mao.visualizacao.TelaAddReceitas;
import controle.mao.visualizacao.TelaEditarReceitas;

public class Receita extends Activity{
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private static Long id;
	public static ReceitasUtil bdScript;
	public Receita(ReceitasUtil bd){
		Receita.bdScript = bd;
	}

	public void salvar() {
		float valorLancamento = 0;
		try {
			valorLancamento = Float.parseFloat(TelaAddReceitas.getTxtValorReceitas().getText().toString());
		} catch (NumberFormatException e) {
			//tratar isto 
		}
		LancamentoDAO lancamento = new LancamentoDAO();		
		ReceitasDAO receita = new ReceitasDAO();

		lancamento.tipoLancamento_lancamentos = "R";
		lancamento.descricao_lancamentos = TelaAddReceitas.getTxtDescricaoReceitas().getText().toString();
		String temp = TelaAddReceitas.getNomeCategoriaBD();
		Log.e("cnm", TelaAddReceitas.getNomeCategoriaBD());
		lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
		lancamento.dataBaixa_lancamentos = converteDataString(TelaAddReceitas.getDtCreditoReceitas());
		lancamento.valor_lancamentos = valorLancamento;
		lancamento.pago = 0;
		receita.dataCredito_receitas = converteDataString(TelaAddReceitas.getDtCreditoReceitas());

		// Salvar
		salvarReceita(lancamento, receita);
		Log.i("cnm", lancamento.toString());
		Log.i("cnm", receita.toString());

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
		
		
	}
	
	public void editar() {
		float valorLancamento = 0;
		try {
			valorLancamento = Float.parseFloat(TelaEditarReceitas.getTxtValorReceitas().getText().toString());
		} catch (NumberFormatException e) {
			//tratar isto 
		}
		LancamentoDAO lancamento = new LancamentoDAO();		
		ReceitasDAO receita = new ReceitasDAO();
		lancamento.id = id;
		lancamento.tipoLancamento_lancamentos = "R";
		lancamento.descricao_lancamentos = TelaEditarReceitas.getTxtDescricaoReceitas().getText().toString();
		String temp = TelaEditarReceitas.getNomeCategoriaBD();
		Log.e("cnm", TelaEditarReceitas.getNomeCategoriaBD());
		lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
		lancamento.dataBaixa_lancamentos = converteDataString(TelaEditarReceitas.getDtCreditoReceitas());
		lancamento.valor_lancamentos = valorLancamento;
		receita.dataCredito_receitas = converteDataString(TelaEditarReceitas.getDtCreditoReceitas());

		// Salvar
		salvarReceita(lancamento, receita);
		Log.i("cnm", lancamento.toString());
		Log.i("cnm", receita.toString());

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
		
		
	}

	public void excluir() {
		if (id != null) {
			excluirReceita(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a receita pelo id
	public ReceitasDAO buscarReceita(long id) {
		return bdScript.buscarReceitas(id);
	}

	// Salvar a receita
	protected void salvarReceita(LancamentoDAO controle, ReceitasDAO receita) {
		bdScript.salvar(controle,receita);
	}

	// Excluir a receita
	protected void excluirReceita(long id) {
		bdScript.deletar(id, id);
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
    
	
	public List<ReceitasDAO> listaReceitas() {
		return bdScript.listarReceitas();
	}

	public ReceitasDAO buscarLancamentoReceitas(Long id) {
		return bdScript.buscarLancamentoReceitas(id);
	}

	public void deletar(long id2) {
		// TODO precisa?
		
	}

}
