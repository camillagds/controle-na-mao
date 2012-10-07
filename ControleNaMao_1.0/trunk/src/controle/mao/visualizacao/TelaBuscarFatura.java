package controle.mao.visualizacao;

import controle.mao.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Tela de Fatura do Cartão de Credito.
 * 
 * @author camilas
 * 
 */
public class TelaBuscarFatura extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.busca_fatura);

		ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscarFatura);
		btBuscar.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada pendente na tela
		setResult(RESULT_CANCELED);

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
