package controle.mao.visualizacao;

import controle.mao.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TelaListaCategorias extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria);
        
        Button btAddCategoria = (Button) findViewById(R.id.btAddCategoria);
        //Tela Lista Categorias
        btAddCategoria.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaListaCategorias.this,TelaAddCategoria.class);
        		TelaListaCategorias.this.startActivity(trocatela);
        		TelaListaCategorias.this.finish();
        	}
        });
    }

}
