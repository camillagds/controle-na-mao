package controle.mao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ControleNaMaoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView btCartao = (ImageView) findViewById(R.id.itemCartao);
        ImageView btCategoria = (ImageView) findViewById(R.id.itemCategoria);
        
        //Tela Lista Cartões
        btCartao.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(ControleNaMaoActivity.this,TelaListaCartoes.class);
        		ControleNaMaoActivity.this.startActivity(trocatela);
        		ControleNaMaoActivity.this.finish();
        	}
        });
        
        //Tela Lista Categorias
        btCategoria.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(ControleNaMaoActivity.this,TelaListaCategorias.class);
        		ControleNaMaoActivity.this.startActivity(trocatela);
        		ControleNaMaoActivity.this.finish();
        	}
        });
    }
}