package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.dao.CartaoDAO;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.DespesasDAO.Despesas;
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

	public static Cartao bdScriptCartoes;
	public static Categoria bdScriptCategorias;
	private DatePicker dtDebitoDespesas;
	private int year;
	private int month;
	private int day;
	private List<CartaoDAO> cartoes;
	private List<CategoriaDAO> categorias;
	private ArrayAdapter<String> listaCategoriasAdapter;

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

	// BD
	private String nomeCartaoBD;
	private String nomeCategoriaBD;
	private String nomeFormaPagtoBD;
	private ImageButton btExcluir;
	private Long idL;
	private Long idD;
	private EditText txtDescricaoDespesas;
	private EditText txtValorDespesas;
	private String formaPgtCartao;
	private String campoFormaPgtCartao;
	private String campoFormaPgt;
	private String campoDataVencimento;
	private int campoPago;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lanc_editar_despesas);
		bdScriptCartoes = new Cartao(new CartoesUtil(this));
		bdScriptCategorias = new Categoria(new CategoriasUtil(this));
		// Ocultar Teclado
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		btConfirmar = (ImageButton) findViewById(R.id.btConfirmarEditarDespesas);
		btCancelar = (ImageButton) findViewById(R.id.btCancelarEditarDespesas);
		btExcluir = (ImageButton) findViewById(R.id.btExcluirEditarDespesas);
		txtDescricaoDespesas = (EditText) findViewById(R.id.txtDescricaoEditarDespesas);
		txtValorDespesas = (EditText) findViewById(R.id.txtValorEditarDespesas);
		dbFormaPgtoDespesas = (Spinner) findViewById(R.id.dbFormaPgtoEditarDespesas);
		dbCategoriaDespesas = (Spinner) findViewById(R.id.dbCategoriaEditarDespesas);
		dtDebitoDespesas = (DatePicker) findViewById(R.id.dtDebitoEditarDespesas);
		rgFormaPagtoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoEditarDespesas);
		dbNomeCartaoDespesas = (Spinner) findViewById(R.id.dbCartaoEditarDespesas);
		rgFormaPagtoCartaoDespesas = (RadioGroup) findViewById(R.id.rgFormaPagtoCartaoEditarDespesas);
		rdCreditoCartaoDespesas = (RadioButton) findViewById(R.id.rdCreditoCartaoEditarDespesas);
		rdDebitoCartaoDespesas = (RadioButton) findViewById(R.id.rdDebitoCartaoEditarDespesas);

		rowCartoes = (TableRow) findViewById(R.id.rowCartao);
		rgFormaPagtoCartaoDespesas.clearCheck();

		// Lista - Forma de Pagamento
		ArrayAdapter<CharSequence> listaFormaPgto = ArrayAdapter
				.createFromResource(this, R.array.forma_pagamento,
						android.R.layout.simple_spinner_item);
		listaFormaPgto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbFormaPgtoDespesas.setAdapter(listaFormaPgto);
		dbFormaPgtoDespesas
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						// Pega Nome Pela Posição
						campoFormaPgt = parentView
								.getItemAtPosition(position).toString()
								.substring(0, 2);
					}

					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});

		// Lista - Categorias
		categorias = Categoria.bdScript.listarCategorias();
		List<String> nomesCategorias = new ArrayList<String>();
		for (CategoriaDAO i : categorias) {
			nomesCategorias.add(i.nome_categoria);
		}
		listaCategoriasAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, nomesCategorias);

		listaCategoriasAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCategoriaDespesas.setAdapter(listaCategoriasAdapter);
		dbCategoriaDespesas
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						// Pega Nome Pela Posição
						nomeCategoriaBD = parentView
								.getItemAtPosition(position).toString();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
					}

				});

		// Lista - Cartões
		cartoes = Cartao.bdScript.listarCartao();
		List<String> nomesCartao = new ArrayList<String>();
		for (CartaoDAO cartao : cartoes) {
			nomesCartao.add(cartao.nome_cartao);
		}
		ArrayAdapter<String> listaCartoes = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, nomesCartao);
		listaCartoes
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbNomeCartaoDespesas.setAdapter(listaCartoes);

		dbNomeCartaoDespesas
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						// Pega Nome Pela Posição
						nomeCartaoBD = parentView.getItemAtPosition(position)
								.toString();
					}

					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
					}

				});
		Log.i("cnm", "Lista Cartões");

		idL = null;
		idD = null;
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			idL = extras.getLong(Lancamentos._ID);
			idD = extras.getLong(Despesas._ID);
			if (idL != null) {
				// é uma edição, busca o carro...
				LancamentoDAO l = TelaListaLancamentos.bdScript
						.buscarLancamento(idL);
				DespesasDAO d = TelaListaLancamentos.bdScriptD
						.buscarLancamentoDespesas(idL);

				txtDescricaoDespesas.setText(l.descricao_lancamentos);

				// TODO arrumar
				Integer pos = (int) l.idCategoria_lancamentos - 1;
				Log.i("cnm", "Posicao categoria: " + pos);
				dbCategoriaDespesas.setSelection(pos);

				txtValorDespesas.setText(String.valueOf(l.valor_lancamentos));
				campoPago = l.pago;

				campoDataVencimento = String.valueOf(d.dataVencimento);
				Calendar data = Receita.ConvertToDateBR(campoDataVencimento);
				Log.i("cnm", data.toString());
				day = data.get(Calendar.DAY_OF_MONTH);
				month = data.get(Calendar.MONTH);
				year = data.get(Calendar.YEAR);

				dtDebitoDespesas.updateDate(year, month, day);
				int i = 0;

				campoFormaPgt = d.formaPagto.toString();
				
				String cartao = "Ca";
				String cheque = "Ch";
				String dinheiro = "Di";
				String formaP = campoFormaPgt.toString().substring(0, 3);
				Log.i("cnm", formaP);
				Log.i("cnm", String.valueOf(listaFormaPgto.getCount()));
				for (i = 0; i < listaFormaPgto.getCount(); i++) {
					String tempP = listaFormaPgto.getItem(i).toString().substring(0, 3);
					Log.i("cnm", tempP);
					if (tempP.equals(formaP))
						dbFormaPgtoDespesas.setSelection(i);
				}

				Log.i("cnm", "Lista Extras - Cartões");

				// TODO implementar direito
				dbNomeCartaoDespesas.setSelection((int) (d.id_cartao - 1));
				try {
					campoFormaPgtCartao = d.tipoCartao.toString();
				} catch (NullPointerException e) {
					campoFormaPgtCartao = "";
				}
				
				try {
					formaPgtCartao = campoFormaPgtCartao.substring(0, 1);
				} catch (Exception e) {
					formaPgtCartao = "";
				} 
				
				String credito = "C";
				String debito = "D";
				rgFormaPagtoCartaoDespesas.clearCheck();
				Log.e("cnm", formaPgtCartao);
				if (formaPgtCartao.equalsIgnoreCase(credito)) {
					rgFormaPagtoCartaoDespesas
							.check(R.id.rdCreditoCartaoEditarDespesas);
					Log.e("cnm", "credito");
				} else if (formaPgtCartao.equalsIgnoreCase(debito)){
					rgFormaPagtoCartaoDespesas
							.check(R.id.rdDebitoCartaoEditarDespesas);
					Log.e("cnm", "debito");
				}
				
			}
		}

		// Bt Excluir
		btExcluir.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				excluir();
				// OK
				setResult(RESULT_OK, new Intent());

				// Fecha a tela
				finish();
			}
		});

		// Bt Confirmar
		btConfirmar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				editar();
			}
		});

		// Bt Cancelar
		btCancelar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

	}

	protected void excluir() {
		Despesa.bdScript.deletar(idL,idD);
	}

	protected void editar() {
		float valorDespesa = 0;
		try {
			valorDespesa = Float.parseFloat(txtValorDespesas.getText()
					.toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		DespesasDAO despesa = new DespesasDAO();
		LancamentoDAO lancamento = new LancamentoDAO();
		// É uma atualização
		lancamento.id = idL;
		despesa.id = idD;

		lancamento.tipoLancamento_lancamentos = "D";
		lancamento.descricao_lancamentos = txtDescricaoDespesas.getText()
				.toString();
		String temp = nomeCategoriaBD.toString();
		Log.e("cnm", nomeCategoriaBD);
		lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
		Log.e("cnm", String.valueOf(lancamento.idCategoria_lancamentos));

		// TODO temporario
		lancamento.dataBaixa_lancamentos = Despesa
				.converteDataString(dtDebitoDespesas);

		lancamento.valor_lancamentos = valorDespesa;
		lancamento.pago = campoPago;
		despesa.idLancamento = idL;
		despesa.formaPagto = campoFormaPgt;
		despesa.id_cartao = Cartao.BuscarIdCartao(nomeCartaoBD);
		despesa.tipoCartao = campoFormaPgtCartao;
		despesa.dataVencimento = Despesa.converteDataString(dtDebitoDespesas);

		// Salvar
		atualizarDespesa(lancamento, despesa);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	private void atualizarDespesa(LancamentoDAO lancamento, DespesasDAO despesa) {
		Despesa.bdScript.atualizar(lancamento, despesa);
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

			Log.d("Activity",
					"Touch event " + event.getRawX() + "," + event.getRawY()
							+ " " + x + "," + y + " rect " + w.getLeft() + ","
							+ w.getTop() + "," + w.getRight() + ","
							+ w.getBottom() + " coords " + scrcoords[0] + ","
							+ scrcoords[1]);
			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Fecha o banco
		Categoria.bdScript.fechar();
		Cartao.bdScript.fechar();
	}

}
