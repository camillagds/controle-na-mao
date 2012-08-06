package controle.mao.visualizacao;

import java.util.Calendar;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class TelaAddDespesas extends Activity {

	private DatePicker dtDebitoDespesas;
	private int year;
	private int month;
	private int day;

	// Elementos
	private Button btConfirmar;
	private Button btCancelar;
	private Spinner dbFormaPgtoDespesas;
	private Spinner dbCategoriaDespesas;
	private Spinner dbPeriodoDespesas;
	private RadioGroup rgFormaPagtoDespesas;
	private RadioButton rdAVistaDespesas;
	private RadioButton rdParcelasDespesas;
	private TableRow rowParcelamentos;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanc_despesas);
		
		//Ocultar Teclado
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		btConfirmar = (Button) findViewById(R.id.btConfirmarDespesas);
		btCancelar = (Button) findViewById(R.id.btCancelarDespesas);
		dbFormaPgtoDespesas = (Spinner) findViewById(R.id.dbFormaPgtoDespesas);
		dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaDespesas);
		dbPeriodoDespesas = (Spinner) findViewById(R.id.dbPeriodoDespesas);
		rgFormaPagtoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoDespesas);
		rdAVistaDespesas = (RadioButton) findViewById(R.id.rdAVistaDespesas);
		rdParcelasDespesas = (RadioButton) findViewById(R.id.rdParcelasDespesas);
		rowParcelamentos = (TableRow) findViewById(R.id.rowParcelamentos);
		rowParcelamentos.setVisibility(View.INVISIBLE);
		setCurrentDateOnView();

		// Lista - Forma de Pagamento
		ArrayAdapter<CharSequence> listaFormaPgto = ArrayAdapter
				.createFromResource(this, R.array.forma_pagamento,
						android.R.layout.simple_spinner_item);
		listaFormaPgto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbFormaPgtoDespesas.setAdapter(listaFormaPgto);

		// Lista - Categorias
		ArrayAdapter<CharSequence> listaCategorias = ArrayAdapter
				.createFromResource(this, R.array.categorias,
						android.R.layout.simple_spinner_item);
		listaCategorias
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCategoriaDespesas.setAdapter(listaCategorias);

		// Lista - Categorias
		ArrayAdapter<CharSequence> listaPeriodicidade = ArrayAdapter
				.createFromResource(this, R.array.periodicidade,
						android.R.layout.simple_spinner_item);
		listaPeriodicidade
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbPeriodoDespesas.setAdapter(listaCategorias);

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

}
