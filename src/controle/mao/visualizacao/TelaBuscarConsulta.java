package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.dados.util.CategoriasUtil;
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
 * Tela de Consulta de Lançamentos.
 * 
 * @author camilas
 * 
 */
public class TelaBuscarConsulta extends Activity implements OnClickListener {
	private Categoria bdScriptCategorias;
	private String nomeCategoriaBD;
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
	    bdScriptCategorias = new Categoria(new CategoriasUtil(this));

		setContentView(R.layout.busca_consulta);

		//Lista - Categorias
        Spinner dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaBuscaConsulta);
		
		// Lista - Categorias
		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
			(this, android.R.layout.simple_spinner_item, Categoria.SpinnerCategorias());
	
		listaCategorias
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCategoriaDespesas.setAdapter(listaCategorias);
		dbCategoriaDespesas.setOnItemSelectedListener(new OnItemSelectedListener() {
			

			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	nomeCategoriaBD = parentView.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
		
		ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscaConsulta);
		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada pendente na tela
		setResult(RESULT_CANCELED);
		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
			(this, android.R.layout.simple_spinner_item, R.array.categorias);
	Spinner dbCategoria = (Spinner) findViewById(R.id.dbCategoriaBuscaConsulta);
	listaCategorias
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	dbCategoria.setAdapter(listaCategorias);
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
		
		Intent trocatela = new Intent(TelaBuscarConsulta.this,
				TelaListaLancamentos.class);
		TelaBuscarConsulta.this.startActivity(trocatela);
		TelaBuscarConsulta.this.finish();
	}	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Fecha o banco
		Categoria.bdScript.fechar();
	}

}
