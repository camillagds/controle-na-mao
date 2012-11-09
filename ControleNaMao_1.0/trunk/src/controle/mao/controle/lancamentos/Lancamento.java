package controle.mao.controle.lancamentos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.widget.DatePicker;
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
		// TODO Auto-generated method stub
		return bdScript.listarLancamentos();
	}
	
	public List<LancamentoDAO> listaLancamentosCartao(List<LancamentoDAO> list) {
		// TODO Auto-generated method stub
		return bdScript.listarLancamentosCartao(list);
	}


	public LancamentoDAO buscarLancamento(long id){
		return bdScript.buscarLancamento(id);
	}


}
