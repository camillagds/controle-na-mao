package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.util.CartoesUtil;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.DespesasUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemSelectedListener;

public class TelaAddDespesas extends Activity {

	private Despesa bdScript;

	// Elementos
	private ImageButton btConfirmar;
	private ImageButton btCancelar;
	
	// Campos
	private static DatePicker dtDebitoDespesas;
	private Spinner dbFormaPgtoDespesas;
	private Spinner dbCategoriaDespesas;
	private Spinner dbPeriodoDespesas;
	private Spinner dbNomeCartaoDespesas;
	private RadioGroup rgFormaPagtoDespesas;
	private RadioGroup rgFormaPagtoCartaoDespesas;
	private TableRow rowParcelamentos;
	private TableRow rowCartoes;
	
	//BD
	private static String nomeCartaoBD;

	private Categoria bdScriptCategorias;

	private Cartao bdScriptCartoes;
	private static EditText txtValorDespesas;
	private static String nomeCategoriaBD;
	private static EditText txtDescricaoDespesas;
	public static String nomeFormaPagtoBD;
	private static String nomeTipoCartaoBD;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanc_despesas);
	    bdScript = new Despesa(new DespesasUtil(this));
	    bdScriptCategorias = new Categoria(new CategoriasUtil(this));
	    bdScriptCartoes = new Cartao(new CartoesUtil(this));

		//Ocultar Teclado
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		btConfirmar = (ImageButton) findViewById(R.id.btConfirmarDespesas);
		btCancelar = (ImageButton) findViewById(R.id.btCancelarDespesas);
		setTxtDescricaoDespesas((EditText) findViewById(R.id.txtDescricaoDespesas));
		setTxtValorDespesas((EditText) findViewById(R.id.txtValorDespesas));
		dbFormaPgtoDespesas = (Spinner) findViewById(R.id.dbFormaPgtoDespesas);
		dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaDespesas);
		dbPeriodoDespesas = (Spinner) findViewById(R.id.dbPeriodoDespesas);
		rgFormaPagtoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoDespesas);
		dbNomeCartaoDespesas = (Spinner) findViewById(R.id.dbCartaoDespesas);
		dtDebitoDespesas = (DatePicker) findViewById(R.id.dtDebitoDespesas);
		rgFormaPagtoCartaoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoCartaoDespesas);
		rowParcelamentos = (TableRow) findViewById(R.id.rowParcelamentos);
		rowParcelamentos.setVisibility(View.INVISIBLE);
		rowCartoes = (TableRow) findViewById(R.id.rowCartao);
		Despesa.setCurrentDateOnView(dtDebitoDespesas);

		// Lista - Forma de Pagamento
		ArrayAdapter<CharSequence> listaFormaPgto = ArrayAdapter
				.createFromResource(this, R.array.forma_pagamento,
						android.R.layout.simple_spinner_item);
		listaFormaPgto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbFormaPgtoDespesas.setAdapter(listaFormaPgto);
		dbFormaPgtoDespesas.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	setNomeFormaPagtoBD(parentView.getItemAtPosition(position).toString());
            	String result = "Cartão";
            	Log.e("cnm", getNomeFormaPagtoBD());
            	if (getNomeFormaPagtoBD().equalsIgnoreCase(result)){
            		rowCartoes.setVisibility(View.VISIBLE);
            	Log.e("cnm", "visivel");
            	}else{
            		rowCartoes.setVisibility(View.INVISIBLE);
            	Log.e("cnm", "invisivel");
            	}
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //Lista - Categorias
        Spinner dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaDespesas);
		
		// Lista - Categorias
		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
			(this, android.R.layout.simple_spinner_item, Categoria.SpinnerCategorias());
	
		listaCategorias
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCategoriaDespesas.setAdapter(listaCategorias);
		dbCategoriaDespesas.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	setNomeCategoriaBD(parentView.getItemAtPosition(position).toString());
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

		// Lista - Periodicidade
		ArrayAdapter<CharSequence> listaPeriodicidade = ArrayAdapter
				.createFromResource(this, R.array.periodicidade,
						android.R.layout.simple_spinner_item);
		listaPeriodicidade
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbPeriodoDespesas.setAdapter(listaPeriodicidade);
		
		// Lista - Cartões
		ArrayAdapter<String> listaCartoes = new ArrayAdapter<String>
				(this, android.R.layout.simple_spinner_item, Cartao.SpinnerCartoes());
		listaCartoes
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbNomeCartaoDespesas.setAdapter(listaCartoes);
		
		dbNomeCartaoDespesas.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	setNomeCartaoBD(parentView.getItemAtPosition(position).toString());
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
		
		// Bt Confirmar
		btConfirmar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
//				Categoria.bdScript.fechar();
				bdScript.salvar();

				// OK
				setResult(RESULT_OK, new Intent());

				// Fecha a tela
				finish();
				
				// Trocar Tela
				Intent trocatela = new Intent(TelaAddDespesas.this,
						ControleNaMaoActivity.class);
				TelaAddDespesas.this.startActivity(trocatela);
				TelaAddDespesas.this.finish();
			}
		});

		// Bt Cancelar
		btCancelar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
				
				Intent trocatela = new Intent(TelaAddDespesas.this,
						ControleNaMaoActivity.class);
				TelaAddDespesas.this.startActivity(trocatela);
//				TelaAddDespesas.this.finish();
			}
		});
	}

	/**
	 * Método acionado na hora do click, que verifica qual foi o Radio Button
	 * selecionado e executa a ação correspondente.
	 * 
	 * @param v
	 */
	public void onClickFormaPagamento(View v) {
		// RadioGroup button = (RadioGroup) v;
		switch (rgFormaPagtoDespesas.getCheckedRadioButtonId()) {
		case R.id.rdAVistaDespesas:
			rowParcelamentos.setVisibility(View.INVISIBLE);
			break;
		case R.id.rdParcelasDespesas:
			rowParcelamentos.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	/**
	 * Método acionado na hora do click, que verifica qual foi o Radio Button
	 * selecionado e executa a ação correspondente.
	 * 
	 * @param v
	 */
	public void onClickFormaPagamentoCartao(View v) {
		// RadioGroup button = (RadioGroup) v;
		switch (rgFormaPagtoCartaoDespesas.getCheckedRadioButtonId()) {
		case R.id.rdCreditoCartaoDespesas:
			setNomeTipoCartaoBD("Credito");
			break;
		case R.id.rdDebitoCartaoDespesas:
			setNomeTipoCartaoBD("Debito");
			break; 
		}
	}
	
	/**
	 * Método que não deixa o teclado ficar ativo quando sai do foco.
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {

	    View v = getCurrentFocus();
	    boolean ret = super.dispatchTouchEvent(event);

	    if (v instanceof EditText) {
	        View w = getCurrentFocus();
	        int scrcoords[] = new int[2];
	        w.getLocationOnScreen(scrcoords);
	        float x = event.getRawX() + w.getLeft() - scrcoords[0];
	        float y = event.getRawY() + w.getTop() - scrcoords[1];

	        Log.d("Activity", "Touch event "+event.getRawX()+","+event.getRawY()+" "+x+","+y+" rect "+w.getLeft()+","+w.getTop()+","+w.getRight()+","+w.getBottom()+" coords "+scrcoords[0]+","+scrcoords[1]);
	        if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) { 

	            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
	        }
	    }
	return ret;
	}
	
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
		
		// Fecha o banco
		Despesa.bdScript.fechar();
		Categoria.bdScript.fechar();
	}
	

@Override
protected void onDestroy() {
	super.onDestroy();
	// Fecha o banco
	Despesa.bdScript.fechar();
	Categoria.bdScript.fechar();
	Cartao.bdScript.fechar();
}

public static String getNomeTipoCartaoBD() {
	return nomeTipoCartaoBD;
}

public void setNomeTipoCartaoBD(String nomeTipoCartaoBD) {
	TelaAddDespesas.nomeTipoCartaoBD = nomeTipoCartaoBD;
}

public String getNomeFormaPagtoBD() {
	return nomeFormaPagtoBD;
}

public void setNomeFormaPagtoBD(String nomeFormaPagtoBD) {
	TelaAddDespesas.nomeFormaPagtoBD = nomeFormaPagtoBD;
}

public static EditText getTxtDescricaoDespesas() {
	return txtDescricaoDespesas;
}

public void setTxtDescricaoDespesas(EditText txtDescricaoCartao) {
	TelaAddDespesas.txtDescricaoDespesas = txtDescricaoCartao;
}

public static String getNomeCategoriaBD() {
	Log.e("cnm-D", nomeCategoriaBD);
	return nomeCategoriaBD;
}

public void setNomeCategoriaBD(String nomeCategoriaBD) {
	TelaAddDespesas.nomeCategoriaBD = nomeCategoriaBD;
	Log.e("cnm", nomeCategoriaBD);
}

public static DatePicker getDtDebitoDespesas() {
	return dtDebitoDespesas;
}

public void setDtDebitoDespesas(DatePicker dtDebitoDespesas) {
	TelaAddDespesas.dtDebitoDespesas = dtDebitoDespesas;
}

public static EditText getTxtValorDespesas() {
	return txtValorDespesas;
}

public void setTxtValorDespesas(EditText txtValorDespesas) {
	TelaAddDespesas.txtValorDespesas = txtValorDespesas;
}

public static String getNomeCartaoBD() {
	return nomeCartaoBD;
}

public void setNomeCartaoBD(String nomeCartaoBD) {
	TelaAddDespesas.nomeCartaoBD = nomeCartaoBD;
}

@Override
public void onBackPressed() {
// Toast.makeText(this, "Back key pressed =)", Toast.LENGTH_SHORT).show();
	Intent trocatela = new
	Intent(TelaAddDespesas.this,ControleNaMaoActivity.class);
	TelaAddDespesas.this.startActivity(trocatela);
	TelaAddDespesas.this.finish();
// super.onBackPressed();
}

}
