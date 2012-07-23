package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.R.id;
import controle.mao.R.layout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class TelaListaCartoes extends Activity {
	

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.cartoes);
    Button btAddCartao = (Button) findViewById(R.id.btAddCartao);
    //Tela Lista Cartões
        btAddCartao.setOnClickListener(new ImageView.OnClickListener(){
    	public void onClick(View v){
    		Intent trocatela = new
    		Intent(TelaListaCartoes.this,TelaAddCartoes.class);
    		TelaListaCartoes.this.startActivity(trocatela);
    		TelaListaCartoes.this.finish();
    	}
    });
    }
}
