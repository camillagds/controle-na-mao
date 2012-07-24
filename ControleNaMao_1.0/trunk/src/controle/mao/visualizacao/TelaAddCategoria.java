package controle.mao.visualizacao;

import controle.mao.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TelaAddCategoria extends Activity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_categorias);
        Button btConfirmar = (Button) findViewById(R.id.btConfirmarCategoria);
        Button btCancelar = (Button) findViewById(R.id.btCancelarCategoria);
        
        //Bt Confirmar
        btConfirmar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddCategoria.this,TelaListaCategorias.class);
        		TelaAddCategoria.this.startActivity(trocatela);
        		TelaAddCategoria.this.finish();
        	}
        });
        
        //Bt Cancelar
        btCancelar.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaAddCategoria.this,TelaListaCategorias.class);
        		TelaAddCategoria.this.startActivity(trocatela);
        		TelaAddCategoria.this.finish();
        	}
        });
        
    }

   
}
