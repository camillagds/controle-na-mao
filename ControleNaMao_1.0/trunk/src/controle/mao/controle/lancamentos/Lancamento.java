package controle.mao.controle.lancamentos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.widget.DatePicker;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.util.LancamentosUtil;

public class Lancamento extends Activity{
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private static Long id;
	public static LancamentosUtil bdScript;
	public Lancamento(LancamentosUtil lancamentosUtil){
		Lancamento.bdScript = lancamentosUtil;
	}
    
	
	public List<LancamentoDAO> listaLancamentos() {
		return bdScript.listarLancamentos();
	}
	
	public List<LancamentoDAO> listaLancamentosCartao() {
		return bdScript.listarLancamentosCartao();
	}


	public LancamentoDAO buscarLancamento(long id){
		return bdScript.buscarLancamento(id);
	}

	public LancamentoDAO buscarLancamentoCartao(long cartao){
		return bdScript.buscarLancamentoCartao(cartao);
	}
}
