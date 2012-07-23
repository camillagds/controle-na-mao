package controle.mao.visualizacao;

import java.util.Calendar;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

public class TelaAddCartoes extends Activity {

	private DatePicker dtFaturaCartao;
	private int year;
	private int month;
	private int day;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cartoes);
        setCurrentDateOnView();
        Button btConfirmar = (Button) findViewById(R.id.btConfirmarCartao);
        Button btCancelar = (Button) findViewById(R.id.btCancelarCartao);
        
        //Bt Confirmar
        btConfirmar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddCartoes.this,TelaListaCartoes.class);
        		TelaAddCartoes.this.startActivity(trocatela);
        		TelaAddCartoes.this.finish();
        	}
        });
        
        //Bt Cancelar
        btCancelar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddCartoes.this,TelaListaCartoes.class);
        		TelaAddCartoes.this.startActivity(trocatela);
        		TelaAddCartoes.this.finish();
        	}
        });
        
    }

    public void setCurrentDateOnView() {
    	 
    	dtFaturaCartao = (DatePicker) findViewById(R.id.dtFaturaCartao);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		
		// set current date into datepicker
		dtFaturaCartao.init(year, month, day, null);
	}
    


    
}
