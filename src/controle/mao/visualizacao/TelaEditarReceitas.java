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
import controle.mao.dados.dao.ReceitasDAO.Receitas;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.LancamentosUtil;
import controle.mao.dados.util.ReceitasUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;
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

public class TelaEditarReceitas extends Activity implements OnItemSelectedListener{

	private static DatePicker dtCreditoReceitas;
	private int year;
	private int month;
	private int day;
	private Categoria bdScriptCategorias;
	private List<CategoriaDAO> categorias;
	private ImageButton btSalvar;
    private ImageButton btCancelar;
	
	// Campo BD
	private static String nomeCategoriaBD;
	private static EditText txtDescricaoReceitas;
	private Spinner dbCategoriaReceitas;
	private static EditText txtValorReceitas;
	private Long idL;
	private ImageButton btExcluir;
	private Long idR;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanc_editar_receitas);
        bdScriptCategorias = new Categoria(new CategoriasUtil(this));
        
        //Elementos
        setTxtDescricaoReceitas((EditText) findViewById(R.id.txtDescricaoEditarReceitas));
        dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaEditarReceitas);
        setTxtValorReceitas((EditText) findViewById(R.id.txtValorEditarReceitas));
        setDtCreditoReceitas((DatePicker) findViewById(R.id.dtCreditoEditarReceitas));
        btSalvar = (ImageButton) findViewById(R.id.btSalvarReceitas);
        btCancelar = (ImageButton) findViewById(R.id.btCancelarReceitas);
        btExcluir = (ImageButton) findViewById(R.id.btExcluirEditarReceitas);
       
        //Lista - Categorias
//        Spinner dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaEditarReceitas);
        
     // Lista - Categorias
     		categorias = Categoria.bdScript.listarCategorias();
     		List<String> nomesCategorias = new ArrayList<String>();
     		for (CategoriaDAO categoria : categorias) {
     			nomesCategorias.add(categoria.nome_categoria);
     		}
     		Log.i("cnm","tamanho lista categoria: "+nomesCategorias.size());
     		
     		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
     				(this, android.R.layout.simple_spinner_item, nomesCategorias);
     		
     		listaCategorias
     				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     		
     		dbCategoriaReceitas.setAdapter(listaCategorias);
     		dbCategoriaReceitas.setOnItemSelectedListener(this);
     		
     		idL = null;
     		idR = null;
            Bundle extras = getIntent().getExtras();
    		// Se for para Editar, recuperar os valores ...
    		if (extras != null) {
    			idL = extras.getLong(Lancamentos._ID);
    			idR = extras.getLong(Receitas._ID);
    			if (idL != null) {
    				// é uma edição, busca o carro...
    				LancamentoDAO l = TelaListaLancamentos.bdScript.buscarLancamento(idL);
    				ReceitasDAO r = TelaListaLancamentos.bdScriptR.buscarLancamentoReceitas(idL);
    				
    				getTxtDescricaoReceitas().setText(l.descricao_lancamentos);
    				//TODO arrumar
    				Integer pos = (int) l.idCategoria_lancamentos-1;
    				Log.i("cnm", "Posicao categoria: "+pos);
    				dbCategoriaReceitas.setSelection(pos);
    				getTxtValorReceitas().setText(String.valueOf(l.valor_lancamentos));
    				Calendar data = Receita.ConvertToDateBR(String.valueOf(r.dataCredito_receitas));
    				day = data.get(Calendar.DAY_OF_MONTH);
    				month = data.get(Calendar.MONTH);
    				year = data.get(Calendar.YEAR);
    				
    				getDtCreditoReceitas().updateDate(year, month, day);
    			}
    		}
        
     	// Listener para salvar o controle
    		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarEditarReceitas);
    		btSalvar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				editar();
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
//					Intent trocatela = new Intent(TelaEditarReceitas.this,
//							ControleNaMaoActivity.class);
//					TelaEditarReceitas.this.startActivity(trocatela);
//					TelaEditarReceitas.this.finish();
					excluir();
					// OK
					setResult(RESULT_OK, new Intent());

					// Fecha a tela
					finish();
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

public void editar() {
	float valorReceita = 0;
	try {
		valorReceita = Float.parseFloat(getTxtValorReceitas().getText().toString());
	} catch (NumberFormatException e) {
		// ok neste exemplo, tratar isto em aplicações reais
	}
	
	ReceitasDAO receita = new ReceitasDAO();
	LancamentoDAO lancamento = new LancamentoDAO();		
		// É uma atualização
		lancamento.id = idL;
		receita.id = idR;
	
	lancamento.tipoLancamento_lancamentos = "R";
	lancamento.descricao_lancamentos = getTxtDescricaoReceitas().getText().toString();
	String temp = getNomeCategoriaBD();
	Log.e("cnm", getNomeCategoriaBD());
	lancamento.idCategoria_lancamentos = Categoria.BuscarIdCategoria(temp);
	lancamento.dataBaixa_lancamentos = Receita.converteDataString(getDtCreditoReceitas());
	lancamento.valor_lancamentos = valorReceita;
	receita.idLancamento_receitas = idL;
	receita.dataCredito_receitas = Receita.converteDataString(getDtCreditoReceitas());


	// Salvar
	atualizarReceita(lancamento,receita);

	// OK
	setResult(RESULT_OK, new Intent());

	// Fecha a tela
	finish();
}

public void excluir() {
	if (idL != null) {
		excluirReceita(idL, idR);
	}

	// OK
	setResult(RESULT_OK, new Intent());

	// Fecha a tela
	finish();
}

// Buscar a receita pelo idL
protected ReceitasDAO buscarReceita(long id) {
	return TelaListaLancamentos.bdScriptR.buscarReceita(id);
}

// Salvar a receita
protected void atualizarReceita(LancamentoDAO lancamento, ReceitasDAO receita) {
	Receita.bdScript.atualizar(lancamento, receita);
}

// Excluir a receita
protected void excluirReceita(long idL, long idR) {
	Receita.bdScript.deletar(idL, idR);
}




public static EditText getTxtValorReceitas() {
	return txtValorReceitas;
}




public void setTxtValorReceitas(EditText txtValorReceitas) {
	TelaEditarReceitas.txtValorReceitas = txtValorReceitas;
}




public static DatePicker getDtCreditoReceitas() {
	return dtCreditoReceitas;
}




public void setDtCreditoReceitas(DatePicker dtCreditoReceitas) {
	TelaEditarReceitas.dtCreditoReceitas = dtCreditoReceitas;
}





public static EditText getTxtDescricaoReceitas() {
	return txtDescricaoReceitas;
}




public void setTxtDescricaoReceitas(EditText txtDescricaoReceitas) {
	TelaEditarReceitas.txtDescricaoReceitas = txtDescricaoReceitas;
}


public static String getNomeCategoriaBD() {
	return nomeCategoriaBD;
}




public void setNomeCategoriaBD(String nomeCategoriaBD) {
	TelaEditarReceitas.nomeCategoriaBD = nomeCategoriaBD;
}




@Override
protected void onDestroy() {
	super.onDestroy();

	// Fecha o banco
	Categoria.bdScript.fechar();
}



public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
 	//Pega Nome Pela Posição
 	Log.i("cnm","Spinner Categoria: Antes");
 	String selected = parentView.getItemAtPosition(position).toString();
	String itemCategoria = dbCategoriaReceitas.getSelectedItem().toString();
 	Log.i("cnm","Spinner Categoria 1: "+selected);
 	Log.i("cnm","Spinner Categoria 2: "+itemCategoria);

	setNomeCategoriaBD(selected);	
 }

 public void onNothingSelected(AdapterView<?> parentView) {
     // your code here
 }

}