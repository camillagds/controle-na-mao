package controle.mao.controle.cartoes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import controle.mao.dados.dao.CartaoDAO;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.util.CartoesUtil;
import controle.mao.visualizacao.TelaAddCartoes;
import controle.mao.visualizacao.TelaListaCartoes;

public class Cartao  extends Activity{
	
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	public static CartoesUtil bdScript;
	private Long id;

	public Cartao(CartoesUtil bdScript){
		Cartao.bdScript = bdScript;
	}
	
	public void salvar() {
		int fechamento = 0;
//		long dataVencimento = 0;
		try {
			fechamento = Integer.parseInt(TelaAddCartoes.getCampoFechamento().getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}
		
		CartaoDAO cartao = new CartaoDAO();
		id = TelaAddCartoes.getID();
		Log.e("cnm", "ID: " + id);
		
		if (id != null) {
			// É uma atualização
			cartao.id = id;
		}
		cartao.nome_cartao = TelaAddCartoes.getCampoNome().getText().toString();
		cartao.bandeira_Cartao = TelaAddCartoes.getValorBandeira();
		cartao.fechaFatura_cartao = fechamento;
//		cartao.vencimento_cartao = dataVencimento;

		// Salvar
		salvarCartao(cartao);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		id = TelaAddCartoes.getID();
		Log.e("cnm", "ID: " + id);
		
		if (id != null) {
			excluirCartao(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a cartao pelo id
	public CartaoDAO buscarCartao(long id) {
		return bdScript.buscarCartao(id);
	}

	// Salvar a cartao
	public void salvarCartao(CartaoDAO cartao) {
		bdScript.salvar(cartao);
	}

	// Excluir a cartao
	public void excluirCartao(long id) {
		bdScript.deletar(id);
	}
    
	public static List<CartaoDAO> listaCartoes() {
		// TODO Auto-generated method stub
		return bdScript.listarCartao();
	}
	
	public DatePicker setDataPersonalizada(long dataBD, DatePicker campoData){	
		Calendar calendar = Calendar.getInstance();    
		calendar.setTimeInMillis(dataBD);
		Date data = new Date(dataBD);
//		data.setTime(dataBD);
		int year = data.getYear();
		int month = data.getMonth();
		int day = data.getDay();
//		java.util.Date data = calendar.getTime();
		campoData.updateDate(year, month, day);
		return campoData;
	}
	
	public static List<String> SpinnerCartoes(){
		// Lista - Categorias
		List<CartaoDAO> cartoes = listaCartoes();
 		List<String> nomesCartoes = new ArrayList<String>();
 		for (int i = 0; i < cartoes.size(); i++) {
 			nomesCartoes.add(cartoes.get(i).nome_cartao);
 		}
 		
 		return nomesCartoes;
	}
}
