package controle.mao.visualizacao;

import java.util.Calendar;
import java.util.Date;

import controle.mao.R;
import controle.mao.dados.CartaoDAO;
import controle.mao.dados.CartaoDAO.Cartoes;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class TelaAddCartoes extends Activity {

	
	private int year;
	private int month;
	private int day;
	
	private String valorBandeira;
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private EditText campoNome;
	private Spinner campoBandeira;
//	private DatePicker campoVencimento;
	private EditText campoFechamento;
	private Long id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cartoes);
//        setCurrentDateOnView();
        
        ArrayAdapter<CharSequence> listaBandeira = ArrayAdapter
				.createFromResource(this, R.array.bandeira,
						android.R.layout.simple_spinner_item);
        listaBandeira
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        campoNome = (EditText) findViewById(R.id.txtDescricaoCartao);
        campoBandeira = (Spinner) findViewById(R.id.dbBandeiraCartao);
		campoBandeira.setAdapter(listaBandeira);		
		campoBandeira.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	valorBandeira = parentView.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
//        campoVencimento = (DatePicker) findViewById(R.id.dtVencimentoCartao); 
        campoFechamento = (EditText) findViewById(R.id.txtFechamentoCartao); 
		
        id = null;
		
        Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(Cartoes._ID);

			if (id != null) {
				// é uma edição, busca o carro...
				CartaoDAO c = buscarCartao(id);
				campoNome.setText(c.nome_cartao);
//				int pos = listaBandeira.getPosition(c.bandeira_Cartao);
//				campoBandeira.setSelection(pos);
				
				String myString = c.bandeira_Cartao; //the value you want the position for

				@SuppressWarnings("unchecked")
				ArrayAdapter<String> myAdap = (ArrayAdapter<String>) campoBandeira.getAdapter(); //cast to an ArrayAdapter

				int spinnerPosition = myAdap.getPosition(myString);

				//set the default according to value
				campoBandeira.setSelection(spinnerPosition);
				
//				campoVencimento = setDataPersonalizada(c.vencimento_cartao, campoVencimento);
//				Calendar calendar = Calendar.getInstance();    
//				calendar.setTimeInMillis(c.vencimento_cartao);
//				Date data = new Date(c.vencimento_cartao);
//				data.setTime(dataBD);
//				year = data.getYear();
//				month = data.getMonth();
//				day = data.getDay();
//				java.util.Date data = calendar.getTime();
//				campoVencimento.updateDate(year, month, day);
				
				campoFechamento.setText(String.valueOf(c.fechaFatura_cartao));
			}
		}
        
		// Listener para cancelar a inclusao/edicao o cartao
        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelarCartao);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

		// Listener para salvar o cartao
		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarCartao);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});

		// Listener para excluir o cartao
		ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluirCartao);

		if (id == null) {
			// Se id está nulo, não pode excluir
			btExcluir.setVisibility(View.INVISIBLE);
		} else {
			// Listener para excluir o carro
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}  
        
    }

    public void setCurrentDateOnView() {
//    	campoVencimento = (DatePicker) findViewById(R.id.dtVencimentoCartao);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		
		// set current date into datepicker
//		campoVencimento.init(year, month, day, null);
	}
    
    @Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	public void salvar() {
		int fechamento = 0;
//		long dataVencimento = 0;
		try {
			fechamento = Integer.parseInt(campoFechamento.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}
		
		CartaoDAO cartao = new CartaoDAO();
		if (id != null) {
			// É uma atualização
			cartao.id = id;
		}
		cartao.nome_cartao = campoNome.getText().toString();
		cartao.bandeira_Cartao = valorBandeira;
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
		if (id != null) {
			excluirCartao(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a cartao pelo id
	protected CartaoDAO buscarCartao(long id) {
		return TelaListaCartoes.bdScript.buscarCartao(id);
	}

	// Salvar a cartao
	protected void salvarCartao(CartaoDAO cartao) {
		TelaListaCartoes.bdScript.salvar(cartao);
	}

	// Excluir a cartao
	protected void excluirCartao(long id) {
		TelaListaCartoes.bdScript.deletar(id);
	}
    
	public DatePicker setDataPersonalizada(long dataBD, DatePicker campoData){	
		Calendar calendar = Calendar.getInstance();    
		calendar.setTimeInMillis(dataBD);
		Date data = new Date(dataBD);
//		data.setTime(dataBD);
		year = data.getYear();
		month = data.getMonth();
		day = data.getDay();
//		java.util.Date data = calendar.getTime();
		campoData.updateDate(year, month, day);
		return campoData;
	}
	
}
