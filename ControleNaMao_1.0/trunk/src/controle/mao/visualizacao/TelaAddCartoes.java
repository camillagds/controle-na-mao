package controle.mao.visualizacao;

import java.util.Calendar;

import controle.mao.R;
import controle.mao.dados.CartaoDAO;
import controle.mao.dados.CategoriaDAO;
import controle.mao.dados.CartaoDAO.Cartoes;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TelaAddCartoes extends Activity {

	
	private int year;
	private int month;
	private int day;
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private EditText campoNome;
	private EditText campoBandeira;
	private DatePicker campoVencimento;
	private EditText campoFechamento;
	private DatePicker dtFaturaCartao;
	private Long id;
	private DatePicker dtVencimentoCartao;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cartoes);
        setCurrentDateOnView();
        
        campoNome = (EditText) findViewById(R.id.txtDescricaoCartao);
        campoBandeira = (EditText) findViewById(R.id.dbBandeiraCartao);
     // TODO Trocar pra Vencimento dtFatura
        campoVencimento = (DatePicker) findViewById(R.id.dtVencimentoCartao); 
     // TODO Adicionar campo Fechamento 
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
			}
		}
		
        
		// Listener para cancelar a inclusao/edicao o categoria
        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelarCategoria);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

		// Listener para salvar o categoria
		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarCategoria);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});

		// Listener para excluir o categoria
		ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluirCategoria);

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
    	 //TODO A troca resolve isso
    	dtVencimentoCartao = (DatePicker) findViewById(R.id.dtVencimentoCartao);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		
		// set current date into datepicker
		dtFaturaCartao.init(year, month, day, null);
		campoVencimento = dtVencimentoCartao;
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

		CartaoDAO cartao = new CartaoDAO();
		if (id != null) {
			// É uma atualização
			cartao.id = id;
		}
		cartao.nome_cartao = campoNome.getText().toString();
//TODO Colocar o resto dos campos

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

	// Buscar a categoria pelo id
	protected CartaoDAO buscarCartao(long id) {
		return TelaListaCartoes.bdScript.buscarCartao(id);
	}

	// Salvar a categoria
	protected void salvarCartao(CartaoDAO cartao) {
		TelaListaCartoes.bdScript.salvar(cartao);
	}

	// Excluir a categoria
	protected void excluirCartao(long id) {
		TelaListaCartoes.bdScript.deletar(id);
	}

    
}
