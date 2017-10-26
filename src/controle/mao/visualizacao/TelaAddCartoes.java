package controle.mao.visualizacao;

import java.util.Calendar;
import java.util.Date;

import controle.mao.R;
import controle.mao.controle.cartoes.Cartao;
import controle.mao.dados.dao.CartaoDAO;
import controle.mao.dados.dao.CartaoDAO.Cartoes;


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
	
	private static String valorBandeira;
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private static EditText campoNome;
	private Spinner campoBandeira;
//	private DatePicker campoVencimento;
	private static EditText campoFechamento;
	private static Long id;
	
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
        
        setCampoNome((EditText) findViewById(R.id.txtDescricaoCartao));
        campoBandeira = (Spinner) findViewById(R.id.dbBandeiraCartao);
		campoBandeira.setAdapter(listaBandeira);		
		campoBandeira.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            	//Pega Nome Pela Posição
            	setValorBandeira(parentView.getItemAtPosition(position).toString());
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
//        campoVencimento = (DatePicker) findViewById(R.id.dtVencimentoCartao); 
        setCampoFechamento((EditText) findViewById(R.id.txtFechamentoCartao)); 
		
        id = null;
		
        Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(Cartoes._ID);

			if (id != null) {
				// é uma edição, busca o carro...
				CartaoDAO c = TelaListaCartoes.bdScript.buscarCartao(id);
				getCampoNome().setText(c.nome_cartao);
//				int pos = listaBandeira.getPosition(c.bandeira_Cartao);
//				campoBandeira.setSelection(pos);
				
				String myString = c.bandeira_Cartao; //the value you want the position for

				@SuppressWarnings("unchecked")
				ArrayAdapter<String> myAdap = (ArrayAdapter<String>) campoBandeira.getAdapter(); //cast to an ArrayAdapter

				int spinnerPosition = myAdap.getPosition(myString);

				//set the default according to value
				campoBandeira.setSelection(spinnerPosition);
				
				getCampoFechamento().setText(String.valueOf(c.fechaFatura_cartao));
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
				TelaListaCartoes.bdScript.salvar();
				// OK
				setResult(RESULT_OK, new Intent());

				// Fecha a tela
				finish();
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
					TelaListaCartoes.bdScript.excluir();
					// OK
					setResult(RESULT_OK, new Intent());

					// Fecha a tela
					finish();
				}
			});
		}  
        
    }
    
	public static Long getID(){
		return id;
	}
    
    @Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	public static String getValorBandeira() {
		return valorBandeira;
	}

	public void setValorBandeira(String valorBandeira) {
		this.valorBandeira = valorBandeira;
	}

	public static EditText getCampoFechamento() {
		return campoFechamento;
	}

	public void setCampoFechamento(EditText campoFechamento) {
		this.campoFechamento = campoFechamento;
	}

	public static EditText getCampoNome() {
		return campoNome;
	}

	public void setCampoNome(EditText campoNome) {
		this.campoNome = campoNome;
	}

	
	
}
