package controle.mao.visualizacao;

import controle.mao.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Buscar o Carro.
 * 
 * @author rlecheta
 * 
 */
public class TelaBuscarConsulta extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.busca_consulta);

//		ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscarFatura);
//		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada pendente na tela
		setResult(RESULT_CANCELED);
		ArrayAdapter<String> listaCategorias = new ArrayAdapter<String>
			(this, android.R.layout.simple_spinner_item, R.array.categorias);
	Spinner dbCategoria = (Spinner) findViewById(R.id.dbCategoriaBuscaConsulta);
	listaCategorias
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	dbCategoria.setAdapter(listaCategorias);
		// Fecha a tela
		finish();
	}

	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view) {

//		DatePicker dataFatura = (DatePicker) findViewById(R.id.dtDataBuscarFatura);
	}

}
