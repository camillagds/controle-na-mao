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
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.util.ReceitasUtil;
import controle.mao.visualizacao.TelaAddDespesas;
import controle.mao.visualizacao.TelaAddReceitas;

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
		if (id != null) {
			// É uma atualização
			lancamento.id = id;
		}
		//TODO declarar as variaveis do BD
		lancamento.tipoLancamento_lancamentos = "R";
		lancamento.descricao_lancamentos = TelaAddReceitas.getTxtDescricaoReceitas().getText().toString();
		String temp = TelaAddReceitas.getNomeCategoriaBD();
		Log.e("cnm", TelaAddReceitas.getNomeCategoriaBD());
		lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
		lancamento.dataBaixa_lancamentos = converteData(TelaAddReceitas.getDtCreditoReceitas());
		lancamento.valor_lancamentos = valorLancamento;
		receita.dataCredito_receitas = converteData(TelaAddReceitas.getDtCreditoReceitas());

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
	protected ReceitasDAO buscarReceita(long id) {
		return bdScript.buscarReceitas(id);
	}

	// Salvar a receita
	protected void salvarReceita(LancamentoDAO controle, ReceitasDAO receita) {
		//TODO alterar
		bdScript.salvar(controle,receita);
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
