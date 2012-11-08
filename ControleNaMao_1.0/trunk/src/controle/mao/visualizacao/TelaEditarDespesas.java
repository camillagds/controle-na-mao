package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.dao.CartaoDAO;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.LancamentoDAO.Lancamentos;
import controle.mao.dados.dao.ReceitasDAO.Receitas;
import controle.mao.dados.util.CartoesUtil;
import controle.mao.dados.util.CategoriasUtil;

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

public class TelaEditarDespesas extends Activity {

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
	private ImageButton btExcluir;
	private Long idL;
	private Long idD;
	private EditText txtDescricaoDespesas;
	private EditText txtValorDespesas;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanc_editar_despesas);
		bdScriptCartoes = new CartoesUtil(this);
		//TODO tem que fechar as duas conexões
		bdScriptCategorias = new CategoriasUtil(this);
		//Ocultar Teclado
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		btConfirmar = (ImageButton) findViewById(R.id.btConfirmarEditarDespesas);
		btCancelar = (ImageButton) findViewById(R.id.btCancelarEditarDespesas);
		btExcluir = (ImageButton) findViewById(R.id.btExcluirEditarDespesas);
        txtDescricaoDespesas = (EditText) findViewById(R.id.txtDescricaoEditarDespesas);
        txtValorDespesas = (EditText) findViewById(R.id.txtValorEditarDespesas);
		dbFormaPgtoDespesas = (Spinner) findViewById(R.id.dbFormaPgtoEditarDespesas);
		dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaEditarDespesas);
		rgFormaPagtoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoEditarDespesas);
		dbNomeCartaoDespesas = (Spinner) findViewById(R.id.dbCartaoEditarDespesas);
		rgFormaPagtoCartaoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoCartaoEditarDespesas);
		rowCartoes = (TableRow) findViewById(R.id.rowCartao);

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
		
		idL = null;
 		idD = null;
        Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			idL = extras.getLong(Lancamentos._ID);
			idD = extras.getLong(Receitas._ID);
			if (idL != null) {
				// é uma edição, busca o carro...
				LancamentoDAO l = TelaListaLancamentos.bdScript.buscarLancamento(idL);
				DespesasDAO d = TelaListaLancamentos.bdScriptD.buscarLancamentoDespesas(idL);
				
				txtDescricaoDespesas.setText(l.descricao_lancamentos);
				
				dbCategoriaDespesas.setSelection((int) l.idCategoria_lancamentos);

				txtValorDespesas.setText(String.valueOf(l.valor_lancamentos));
				Calendar data = Receita.ConvertToDateBR(String.valueOf(d.dataVencimento));
				day = data.get(Calendar.DAY_OF_MONTH);
				month = data.get(Calendar.MONTH);
				year = data.get(Calendar.YEAR);
				
				dtDebitoDespesas.updateDate(year, month, day);
				dbFormaPgtoDespesas.setSelection((int) (listaFormaPgto.getPosition(d.formaPagto)));
				//TODO implementar direito
				dbNomeCartaoDespesas.setSelection((int) d.id_cartao);
				String formaPgtCartao = d.tipoCartao.toString().substring(0, 1);
            	String credito = "C";
            	String debito = " D";
            	rdCreditoCartaoDespesas.clearFocus();
            	rdDebitoCartaoDespesas.clearFocus();
            	Log.e("cnm", formaPgtCartao);
            	if (formaPgtCartao.equalsIgnoreCase(credito)){
            	rgFormaPagtoCartaoDespesas.check(0);
            	
            	Log.e("cnm", "credito");
            	}else{
            	rdDebitoCartaoDespesas.clearFocus();
            	rgFormaPagtoCartaoDespesas.check(1);

            	Log.e("cnm", "debito");
            	}
			}
		}
		
		// Bt Excluir
				btExcluir.setOnClickListener(new ImageView.OnClickListener() {
					public void onClick(View v) {
						Intent trocatela = new Intent(TelaEditarDespesas.this,
								ControleNaMaoActivity.class);
						TelaEditarDespesas.this.startActivity(trocatela);
						TelaEditarDespesas.this.finish();
					}
				});
		
		// Bt Confirmar
		btConfirmar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent trocatela = new Intent(TelaEditarDespesas.this,
						ControleNaMaoActivity.class);
				TelaEditarDespesas.this.startActivity(trocatela);
				TelaEditarDespesas.this.finish();
			}
		});

		// Bt Cancelar
		btCancelar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent trocatela = new Intent(TelaEditarDespesas.this,
						ControleNaMaoActivity.class);
				TelaEditarDespesas.this.startActivity(trocatela);
				TelaEditarDespesas.this.finish();
			}
		});

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
