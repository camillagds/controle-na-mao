package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.util.CartoesUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Tela de Fatura do Cartão de Credito.
 * 
 * @author camilas
 * 
 */
public class TelaBuscarFatura extends Activity implements OnClickListener {
	
	private LancamentoDAO listaCartao;
	private long idCartao;
	private Cartao bdScriptCartoes;

	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
	    bdScriptCartoes = new Cartao(new CartoesUtil(this));

		setContentView(R.layout.busca_fatura);
		Spinner dbCartaoBuscarFatura = (Spinner) findViewById(R.id.dbCartaoBuscarFatura);
		
		// Lista - Cartões
		ArrayAdapter<String> listaCartoes = new ArrayAdapter<String>
				(this, android.R.layout.simple_spinner_item, Cartao.SpinnerCartoes());
		listaCartoes
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCartaoBuscarFatura.setAdapter(listaCartoes);
		
		dbCartaoBuscarFatura.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
//            	listaCartao = parentView.getItemAtPosition(position).toString();
            	idCartao = position;

            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
		
		ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscarFatura);
		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada pendente na tela
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view) {
		setResult(RESULT_CANCELED);
		// Fecha a tela
		finish();
		
		Intent trocatela = new Intent(TelaBuscarFatura.this,
				TelaListaFatura.class);
		TelaBuscarFatura.this.startActivity(trocatela);
		TelaBuscarFatura.this.finish();
		
	}

	// Busca um carro pelo nome
	protected LancamentoDAO buscarLancamento(long cartao) {
		long id = 0;
		LancamentoDAO listaCartao = TelaListaFatura.bdScript.buscarLancamentoCartao(id);
		return listaCartao;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Fecha o banco
		Cartao.bdScript.fechar();
	}

}
