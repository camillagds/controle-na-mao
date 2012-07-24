package controle.mao.visualizacao;

import java.util.Calendar;

import controle.mao.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

public class TelaAddReceitas extends Activity {

	private DatePicker dtCreditoReceitas;
	private int year;
	private int month;
	private int day;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanc_receitas);
        setCurrentDateOnView();
        Button btConfirmar = (Button) findViewById(R.id.btConfirmarDespesas);
        Button btCancelar = (Button) findViewById(R.id.btCancelarReceitas);
        
        //Bt Confirmar
        btConfirmar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddReceitas.this,ControleNaMaoActivity.class);
        		TelaAddReceitas.this.startActivity(trocatela);
        		TelaAddReceitas.this.finish();
        	}
        });
        
        //Bt Cancelar
        btCancelar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddReceitas.this,ControleNaMaoActivity.class);
        		TelaAddReceitas.this.startActivity(trocatela);
        		TelaAddReceitas.this.finish();
        	}
        });
        
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
    


    
}
