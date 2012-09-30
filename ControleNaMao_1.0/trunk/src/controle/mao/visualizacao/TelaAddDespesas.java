package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;
import controle.mao.dados.CartaoDAO;
import controle.mao.dados.CartoesUtil;
import controle.mao.dados.CategoriaDAO;
import controle.mao.dados.CategoriasUtil;
import controle.mao.dados.ReceitasDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class TelaAddDespesas extends Activity {

	public static CartoesUtil bdScriptCartoes;
	public static CategoriasUtil bdScriptCategorias;
	private DatePicker dtDebitoDespesas;
	private int year;
	private int month;
	private int day;
	private List<CartaoDAO> cartoes;
	private List<CategoriaDAO> categorias;

	// Elementos
	private ImageButton btConfirmar;
	private ImageButton btCancelar;
	
	// Campos
	private Spinner dbFormaPgtoDespesas;
	private Spinner dbCategoriaDespesas;
	private Spinner dbPeriodoDespesas;
	private Spinner dbNomeCartaoDespesas;
	private RadioGroup rgFormaPagtoDespesas;
	private RadioGroup rgFormaPagtoCartaoDespesas;
	private TableRow rowParcelamentos;
	private TableRow rowCartoes;
	private RadioButton rdCreditoCartaoDespesas;
	private RadioButton rdDebitoCartaoDespesas;
	private RadioButton rdAVistaDespesas;
	private RadioButton rdParcelasDespesas;
	
	//BD
	private String nomeCartaoBD;
	private String nomeCategoriaBD;
	private String nomeFormaPagtoBD;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanc_despesas);
		bdScriptCartoes = new CartoesUtil(this);
		//TODO tem que fechar as duas conexões
		bdScriptCategorias = new CategoriasUtil(this);
		//Ocultar Teclado
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		btConfirmar = (ImageButton) findViewById(R.id.btConfirmarDespesas);
		btCancelar = (ImageButton) findViewById(R.id.btCancelarDespesas);
		dbFormaPgtoDespesas = (Spinner) findViewById(R.id.dbFormaPgtoDespesas);
		dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaDespesas);
		dbPeriodoDespesas = (Spinner) findViewById(R.id.dbPeriodoDespesas);
		rgFormaPagtoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoDespesas);
		dbNomeCartaoDespesas = (Spinner) findViewById(R.id.dbCartaoDespesas);
		rgFormaPagtoCartaoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoCartaoDespesas);
		rowParcelamentos = (TableRow) findViewById(R.id.rowParcelamentos);
		rowParcelamentos.setVisibility(View.INVISIBLE);
		rowCartoes = (TableRow) findViewById(R.id.rowCartao);
//		rowCartoes.setVisibility(View.INVISIBLE);
//		rowCartoes.set
		setCurrentDateOnView();

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
            	String formaPgt = parentView.getItemAtPosition(position).toString().substring(0, 2);
            	String result = "Ca";
            	Log.e("cnm", formaPgt);
            	if (formaPgt.equalsIgnoreCase(result)){
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

		// Lista - Categorias
		categorias = bdScriptCategorias.listarCategorias();
		List<String> nomesCategorias = new ArrayList<String>();
		for (int i = 0; i < categorias.size(); i++) {
			nomesCategorias.add(categorias.get(i).nome_categoria);
		}
		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
				(this, android.R.layout.simple_spinner_item, nomesCategorias);
		
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

		// Lista - Periodicidade
		ArrayAdapter<CharSequence> listaPeriodicidade = ArrayAdapter
				.createFromResource(this, R.array.periodicidade,
						android.R.layout.simple_spinner_item);
		listaPeriodicidade
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbPeriodoDespesas.setAdapter(listaPeriodicidade);
		
		// Lista - Cartões
		cartoes = bdScriptCartoes.listarCartao();
		List<String> nomesCartao = new ArrayList<String>();
		for (int i = 0; i < cartoes.size(); i++) {
			nomesCartao.add(cartoes.get(i).nome_cartao);
		}
		ArrayAdapter<String> listaCartoes = new ArrayAdapter<String>
				(this, android.R.layout.simple_spinner_item, nomesCartao);
		listaCartoes
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbNomeCartaoDespesas.setAdapter(listaCartoes);
		
		dbNomeCartaoDespesas.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	nomeCartaoBD = parentView.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
		
		// Bt Confirmar
		btConfirmar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent trocatela = new Intent(TelaAddDespesas.this,
						ControleNaMaoActivity.class);
				TelaAddDespesas.this.startActivity(trocatela);
				TelaAddDespesas.this.finish();
			}
		});

		// Bt Cancelar
		btCancelar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent trocatela = new Intent(TelaAddDespesas.this,
						ControleNaMaoActivity.class);
				TelaAddDespesas.this.startActivity(trocatela);
				TelaAddDespesas.this.finish();
			}
		});

	}

	/**
	 * Método que retorna a data atual para o campo "Data do Crédito
	 */
	public void setCurrentDateOnView() {
		dtDebitoDespesas = (DatePicker) findViewById(R.id.dtDebitoDespesas);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		dtDebitoDespesas.init(year, month, day, null);
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
			break;
		case R.id.rdDebitoCartaoDespesas:
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
	

@Override
protected void onDestroy() {
	super.onDestroy();
	// Fecha o banco
	bdScriptCategorias.fechar();
	bdScriptCartoes.fechar();
}

}
