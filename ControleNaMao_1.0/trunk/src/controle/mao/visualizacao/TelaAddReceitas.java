package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.ReceitasUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class TelaAddReceitas extends Activity {

	private static DatePicker dtCreditoReceitas;

	public static Receita bdScript;
	private Categoria bdScriptCategorias;
	
	private ImageButton btSalvar;
    private ImageButton btCancelar;
	
	// Campo BD
	private static String nomeCategoriaBD;
	private static EditText txtDescricaoReceitas;
	private Spinner dbCategoriaReceitas;
	private static EditText txtValorReceitas;
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanc_receitas);
        

      //TODO tem que fechar as duas conex�es
      bdScriptCategorias = new Categoria(new CategoriasUtil(this));
      bdScript = new Receita(new ReceitasUtil(this));
        
        //Elementos
        setTxtDescricaoReceitas((EditText) findViewById(R.id.txtDescricaoReceitas));
//        dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaReceitas);
        setTxtValorReceitas((EditText) findViewById(R.id.txtValorReceitas));
        setDtCreditoReceitas((DatePicker) findViewById(R.id.dtCreditoReceitas));
        Receita.setCurrentDateOnView(dtCreditoReceitas);
//        setDtCreditoReceitas(dtCreditoReceitas);
        //Lista - Categorias
        Spinner dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaReceitas);
        
        // Lista - Categorias
     		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
     				(this, android.R.layout.simple_spinner_item, Categoria.SpinnerCategorias());
     		
     		listaCategorias
     				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     		dbCategoriaReceitas.setAdapter(listaCategorias);
     		dbCategoriaReceitas.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 	//Pega Nome Pela Posi��o
                 	setNomeCategoriaBD(parentView.getItemAtPosition(position).toString());
//                 	setNomeCategoriaBD(String.valueOf(parentView.getId()));
                 	System.out.println(getNomeCategoriaBD());
                 	
                 }

                 public void onNothingSelected(AdapterView<?> parentView) {
                     // your code here
//                  	setNomeCategoriaBD(String.valueOf(parentView.getId()));
//                 	System.out.println(getNomeCategoriaBD());
                 }

             });
        
     	// Listener para salvar o controle
    		btSalvar = (ImageButton) findViewById(R.id.btSalvarReceitas);
    		btSalvar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
//    				Categoria.bdScript.fechar();
    				bdScript.salvar();
					// OK
					setResult(RESULT_OK, new Intent());

					// Fecha a tela
					
					finish();
					
					// Trocar Tela
					Intent trocatela = new Intent(TelaAddReceitas.this,
							ControleNaMaoActivity.class);
					TelaAddReceitas.this.startActivity(trocatela);
					TelaAddReceitas.this.finish();
    			}
    		});

    		// Listener para cancelar a inclusao/edicao o controle
            btCancelar = (ImageButton) findViewById(R.id.btCancelarReceitas);
    		btCancelar.setOnClickListener(new OnClickListener() {
    			public void onClick(View view) {
    				setResult(RESULT_CANCELED);
    				// Fecha a tela
    				finish();
    				
    				// Trocar Tela
					Intent trocatela = new Intent(TelaAddReceitas.this,
							ControleNaMaoActivity.class);
					TelaAddReceitas.this.startActivity(trocatela);
					TelaAddReceitas.this.finish();
    			}
    		});
    }


@Override
protected void onPause() {
	super.onPause();
	// Cancela para n�o ficar nada na tela pendente
	setResult(RESULT_CANCELED);

	// Fecha a tela
	finish();
	
	// Fecha o banco
	Receita.bdScript.fechar();
	Categoria.bdScript.fechar();
}

@Override
protected void onDestroy() {
	super.onDestroy();

	// Fecha o banco
	
	Receita.bdScript.fechar();
	Categoria.bdScript.fechar();
}


public static EditText getTxtValorReceitas() {
	return txtValorReceitas;
}


public void setTxtValorReceitas(EditText txtValorReceitas) {
	TelaAddReceitas.txtValorReceitas = txtValorReceitas;
}


public static EditText getTxtDescricaoReceitas() {
	return txtDescricaoReceitas;
}


public void setTxtDescricaoReceitas(EditText txtDescricaoReceitas) {
	TelaAddReceitas.txtDescricaoReceitas = txtDescricaoReceitas;
}


public static String getNomeCategoriaBD() {
	return nomeCategoriaBD;
}


public void setNomeCategoriaBD(String nomeCategoriaBD) {
	Log.e("cnm-R", nomeCategoriaBD);
	TelaAddReceitas.nomeCategoriaBD = nomeCategoriaBD;
}


public static DatePicker getDtCreditoReceitas() {
	return dtCreditoReceitas;
}


public void setDtCreditoReceitas(DatePicker dtCreditoReceitas) {
	TelaAddReceitas.dtCreditoReceitas = dtCreditoReceitas;
}

/**
 * M�todo que n�o deixa o teclado ficar ativo quando sai do foco.
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