package controle.mao.visualizacao;

import java.util.Calendar;

import controle.mao.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

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
        
        //Elementos
        Button btConfirmar = (Button) findViewById(R.id.btConfirmarReceitas);
        Button btCancelar = (Button) findViewById(R.id.btCancelarReceitas);
       
        //Lista - Categorias
        Spinner dbCategoriaReceitas = (Spinner) findViewById(R.id.dbCategoriaReceitas);
        ArrayAdapter<CharSequence> listaCategorias = ArrayAdapter.createFromResource(this,
        R.array.categorias, android.R.layout.simple_spinner_item);
        listaCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dbCategoriaReceitas.setAdapter(listaCategorias);
        
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
