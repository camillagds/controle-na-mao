package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.xml.sax.DTDHandler;

import controle.mao.R;
import controle.mao.dados.CartaoDAO;
import controle.mao.dados.CategoriaDAO;
import controle.mao.dados.CategoriasUtil;
import controle.mao.dados.ControleDAO;
import controle.mao.dados.ReceitasDAO;
import controle.mao.dados.ReceitasUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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

public class TelaAddReceitas extends Activity {

	private DatePicker dtCreditoReceitas;
	private int year;
	private int month;
	private int day;
	private CategoriasUtil bdScriptCategorias;
	private ReceitasUtil bdScript;
	private List<CategoriaDAO> categorias;
	private ImageButton btSalvar;
    private ImageButton btCancelar;
	
	// Campo BD
	private String nomeCategoriaBD;
	private EditText txtDescricaoReceitas;
	private Spinner dbCategoriaReceitas;
	private EditText txtValorReceitas;
	private Long id;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanc_receitas);
        setCurrentDateOnView();
      //TODO tem que fechar as duas conexões
      bdScriptCategorias = new CategoriasUtil(this);
      bdScript = new ReceitasUtil(this);

        
        //Elementos
        txtDescricaoReceitas = (EditText) findViewById(R.id.txtDescricaoReceitas);
        dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaReceitas);
        txtValorReceitas = (EditText) findViewById(R.id.txtValorReceitas);
        dtCreditoReceitas = (DatePicker) findViewById(R.id.dtCreditoReceitas);
        btSalvar = (ImageButton) findViewById(R.id.btSalvarReceitas);
        btCancelar = (ImageButton) findViewById(R.id.btCancelarReceitas);
       
        //Lista - Categorias
        Spinner dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaReceitas);
        
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
        
     	// Listener para salvar o controle
    		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarReceitas);
    		btSalvar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				salvar();
    			}
    		});

    		// Listener para cancelar a inclusao/edicao o controle
            ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelarReceitas);
    		btCancelar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				setResult(RESULT_CANCELED);
    				// Fecha a tela
    				finish();
    			}
    		});
    }

    public Date converteData(DatePicker campoData) {  	 
    	campoData = dtCreditoReceitas;
		Date data = new Date(campoData.getYear(), campoData.getMonth(), campoData.getDayOfMonth());
		return data;
	}

    public void setCurrentDateOnView() {
    	dtCreditoReceitas = (DatePicker) findViewById(R.id.dtCreditoReceitas);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		dtCreditoReceitas.init(year, month, day, null);
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
	receita.nome_receitas = txtDescricaoReceitas.getText().toString();
	receita.categoria_receitas = nomeCategoriaBD;
	receita.valor_receitas = valorReceita;
	receita.dataPagamento_receitas = converteData(dtCreditoReceitas);

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
	return bdScript.buscarReceitas(id);
}

// Salvar a receita
protected void salvarReceita(ReceitasDAO receita) {
	bdScript.salvar(receita);
}

// Excluir a receita
protected void excluirReceita(long id) {
	bdScript.deletar(id);
}

@Override
protected void onDestroy() {
	super.onDestroy();

	// Fecha o banco
	bdScriptCategorias.fechar();
	bdScript.fechar();
}
}