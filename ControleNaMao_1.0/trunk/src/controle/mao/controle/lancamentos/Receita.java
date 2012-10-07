package controle.mao.controle.lancamentos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.widget.DatePicker;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.ReceitasUtil;
import controle.mao.visualizacao.TelaAddReceitas;

public class Receita extends Activity{
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private static Long id;
	public static ReceitasUtil bdScript;
//	private static CategoriasUtil bdScriptCategorias;
	
	public Receita(ReceitasUtil bd){
		Receita.bdScript = bd;
	}

	public void salvar() {
		float valorReceita = 0;
		try {
			valorReceita = Float.parseFloat(TelaAddReceitas.getTxtValorReceitas().getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}
		
		ReceitasDAO receita = new ReceitasDAO();
		if (id != null) {
			// É uma atualização
			receita.id = id;
		}
		receita.nome_receitas = TelaAddReceitas.getTxtDescricaoReceitas().getText().toString();
		receita.categoria_receitas = TelaAddReceitas.getNomeCategoriaBD();
		receita.valor_receitas = valorReceita;
		receita.dataPagamento_receitas = converteData(TelaAddReceitas.getDtCreditoReceitas());

		// Salvar
		salvarReceita(receita);

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
	protected ReceitasDAO buscarReceita(long id) {
		return bdScript.buscarReceitas(id);
	}

	// Salvar a receita
	protected void salvarReceita(ReceitasDAO receita) {
		bdScript.salvar(receita);
	}

	// Excluir a receita
	protected void excluirReceita(long id) {
		bdScript.deletar(id);
	}
	
    public Date converteData(DatePicker campoData) {  	 
    	campoData = TelaAddReceitas.getDtCreditoReceitas();
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
    
	
	public List<ReceitasDAO> listaReceitas() {
		// TODO Auto-generated method stub
		return bdScript.listarReceitas();
	}
}
