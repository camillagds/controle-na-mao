package controle.mao.visualizacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xml.sax.DTDHandler;

import controle.mao.R;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.dao.CartaoDAO;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.LancamentoDAO.Lancamentos;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.CartaoDAO.Cartoes;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.LancamentosUtil;
import controle.mao.dados.util.ReceitasUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class TelaEditarReceitas extends Activity {

	private DatePicker dtCreditoReceitas;
	private int year;
	private int month;
	private int day;
	private Categoria bdScriptCategorias;
//	private ReceitasUtil bdScriptR;
	private List<CategoriaDAO> categorias;
	private ImageButton btSalvar;
    private ImageButton btCancelar;
	
	// Campo BD
	private String nomeCategoriaBD;
	private EditText txtDescricaoReceitas;
	private Spinner dbCategoriaReceitas;
	private EditText txtValorReceitas;
	private Long id;
	private ImageButton btExcluir;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanc_editar_receitas);
      //TODO tem que fechar as duas conexões
      bdScriptCategorias = new Categoria(new CategoriasUtil(this));
//      bdScriptR = new ReceitasUtil(this);

        
        //Elementos
        txtDescricaoReceitas = (EditText) findViewById(R.id.txtDescricaoEditarReceitas);
        dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaEditarReceitas);
        txtValorReceitas = (EditText) findViewById(R.id.txtValorReceitas);
        dtCreditoReceitas = (DatePicker) findViewById(R.id.dtCreditoEditarReceitas);
        btSalvar = (ImageButton) findViewById(R.id.btSalvarReceitas);
        btCancelar = (ImageButton) findViewById(R.id.btCancelarReceitas);
        btExcluir = (ImageButton) findViewById(R.id.btExcluirEditarReceitas);
       
        //Lista - Categorias
        Spinner dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaEditarReceitas);
        
     // Lista - Categorias
     		categorias = bdScriptCategorias.bdScript.listarCategorias();
     		List<String> nomesCategorias = new ArrayList<String>();
     		for (int i = 0; i < categorias.size(); i++) {
     			nomesCategorias.add(categorias.get(i).nome_categoria);
     		}
     		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
     				(this, android.R.layout.simple_spinner_item, nomesCategorias);
     		
     		listaCategorias
     				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     		dbCategoriaReceitas.setAdapter(listaCategorias);
     		dbCategoriaReceitas.setOnItemSelectedListener(new OnItemSelectedListener() {


				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 	//Pega Nome Pela Posição
                 	nomeCategoriaBD = parentView.getItemAtPosition(position).toString();
                 }

                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
                 }

             });
     		
     		id = null;
    		
            Bundle extras = getIntent().getExtras();
    		// Se for para Editar, recuperar os valores ...
    		if (extras != null) {
    			id = extras.getLong(Lancamentos._ID);

    			if (id != null) {
    				// é uma edição, busca o carro...
    				LancamentoDAO l = TelaListaLancamentos.bdScript.buscarLancamento(id);
    				ReceitasDAO r = TelaListaLancamentos.bdScriptR.buscarLancamentoReceitas(id);
    				
    				txtDescricaoReceitas.setText(l.descricao_lancamentos);
    				
    				Integer pos = (int) l.idCategoria_lancamentos;
    				dbCategoriaReceitas.setSelection((int) l.idCategoria_lancamentos);
    				
//    				String myString = l.idCategoria_lancamentos; //the value you want the position for
//
//    				@SuppressWarnings("unchecked")
//    				ArrayAdapter<String> myAdap = (ArrayAdapter<String>) dbCategoriaReceitas.getAdapter(); //cast to an ArrayAdapter
//
//    				int spinnerPosition = myAdap.getPosition(myString);
//
//    				//set the default according to value
//    				dbCategoriaReceitas.setSelection(spinnerPosition);
    				txtValorReceitas.setText(String.valueOf(l.valor_lancamentos));
    				Date data = new Date(r.dataCredito_receitas);
    				dtCreditoReceitas.updateDate(data.getYear(), data.getMonth(), data.getDay());
    			}
    		}
        
     	// Listener para salvar o controle
    		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarEditarReceitas);
    		btSalvar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				salvar();
    			}
    		});

    		// Listener para cancelar a inclusao/edicao o controle
            ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelarEditarReceitas);
    		btCancelar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				setResult(RESULT_CANCELED);
    				// Fecha a tela
    				finish();
    			}
    		});
    		
    		// Bt Excluir
			btExcluir.setOnClickListener(new ImageView.OnClickListener() {
				public void onClick(View v) {
					Intent trocatela = new Intent(TelaEditarReceitas.this,
							ControleNaMaoActivity.class);
					TelaEditarReceitas.this.startActivity(trocatela);
					TelaEditarReceitas.this.finish();
				}
			});
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
	float valorReceita = 0;
	try {
		valorReceita = Float.parseFloat(txtValorReceitas.getText().toString());
	} catch (NumberFormatException e) {
		// ok neste exemplo, tratar isto em aplicações reais
	}
	
	ReceitasDAO receita = new ReceitasDAO();
	if (id != null) {
		// É uma atualização
		receita.id = id;
	}
	receita.dataCredito_receitas = converteDataString(dtCreditoReceitas);


	// Salvar
	salvarReceita(receita);

	// OK
	setResult(RESULT_OK, new Intent());

	// Fecha a tela
	finish();
}

public void excluir() {
	if (id != null) {
		excluirReceita(id);
	}

	// OK
	setResult(RESULT_OK, new Intent());

	// Fecha a tela
	finish();
}

// Buscar a receita pelo id
protected ReceitasDAO buscarReceita(long id) {
	return TelaListaLancamentos.bdScriptR.buscarReceita(id);
}

// Salvar a receita
protected void salvarReceita(ReceitasDAO receita) {
//TODO alterar
	TelaListaLancamentos.bdScriptR.salvar();
}

// Excluir a receita
protected void excluirReceita(long id) {
	TelaListaLancamentos.bdScriptR.excluir();
}

public String converteDataString(DatePicker campoData) {  	 
//	campoData = TelaAddDespesas.getDtDebitoDespesas();
	Date data = new Date(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
        String dataTransf = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
//        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.applyPattern("yyyy-MM-dd");
        
        dataTransf  = formato.format(data);
	
	return dataTransf;
}

public Date converteDataDate(DatePicker campoData) {  	 
//	campoData = TelaAddReceitas.getDtCreditoReceitas();
	Date data = new Date(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
	return data;
}


@Override
protected void onDestroy() {
	super.onDestroy();

	// Fecha o banco
	bdScriptCategorias.bdScript.fechar();
}
}