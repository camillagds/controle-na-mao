package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.controle.util.DialogoData;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import android.content.Context;
import android.content.Intent;

public class TelaExportarPlanilha extends FragmentActivity {

	// Elementos
	private Button btDataInicialExportar;
	private Button btDataFinalExportar;
	private DatePicker dtDataInicialExportar;
	private DatePicker dtDataFinalExportar;
	private Spinner dbCategoriaExportar;
	private RadioGroup rgExportarLocal;
	private RadioButton rdEnviarporEmailExportar;
	private RadioButton rdSalvarCartaoMemoriaExportar;
	private Button btConfirmarExportar;
	private Button btCancelarExportar;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.exportar);

		// Ocultar Teclado
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Elementos
		rgExportarLocal = (RadioGroup) findViewById(R.id.rgExportarLocalExportar);
		rdEnviarporEmailExportar = (RadioButton) findViewById(R.id.rdEnviarporEmailExportar);
		rdSalvarCartaoMemoriaExportar = (RadioButton) findViewById(R.id.rdSalvarCartaoMemoriaExportar);
		btDataInicialExportar = (Button) findViewById(R.id.btDataInicialExportar);
		btDataFinalExportar = (Button) findViewById(R.id.btDataFinalExportar);
		dbCategoriaExportar = (Spinner) findViewById(R.id.dbCategoriaExportar);
		btConfirmarExportar = (Button) findViewById(R.id.btConfirmarExportar);
		btCancelarExportar = (Button) findViewById(R.id.btCancelarExportar);
		

		//DropBox Categoria
		ArrayAdapter<CharSequence> listaCategorias = ArrayAdapter
				.createFromResource(this, R.array.categorias,
						android.R.layout.simple_spinner_item);
		listaCategorias
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dbCategoriaExportar.setAdapter(listaCategorias);
		
		// Bot�o Data Inicial
		btDataInicialExportar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				DialogoData dialogo = new DialogoData();
			    dialogo.show(getSupportFragmentManager(), "Data Inicial");
			    dtDataInicialExportar = dialogo.getDataUsuario();
			}
		});

		
		//Bot�o Data Final
		btDataFinalExportar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				DialogoData dialogo = new DialogoData();
			    dialogo.show(getSupportFragmentManager(), "Data Final");
			    dtDataFinalExportar = dialogo.getDataUsuario();
			}
		});		
		
		
		// Bot�o Gerar Planilha
		btConfirmarExportar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				// Chama o POI
			}
		});

		// Bot�o Cancelar
		btCancelarExportar.setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				Intent trocatela = new Intent(TelaExportarPlanilha.this,
						ControleNaMaoActivity.class);
				TelaExportarPlanilha.this.startActivity(trocatela);
				TelaExportarPlanilha.this.finish();
			}
		});
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
