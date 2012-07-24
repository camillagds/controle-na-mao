package controle.mao.visualizacao;

import controle.mao.R;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ControleNaMaoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView btCartao = (ImageView) findViewById(R.id.itemCartao);
        ImageView btCategoria = (ImageView) findViewById(R.id.itemCategoria);
        ImageView btLancamentos = (ImageView) findViewById(R.id.itemLancamentos);
        registerForContextMenu(btLancamentos); 
        
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
        
        //Tela Lista Lançamentos
        btLancamentos.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(ControleNaMaoActivity.this,TelaAddDespesas.class);
        		ControleNaMaoActivity.this.startActivity(trocatela);
        		ControleNaMaoActivity.this.finish();
        	}
        });
    }
    //Tela Lista Lançamentos
    //TODO: implementar pop-up
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Lançamentos");
		menu.add(0, v.getId(), 0, "Despesas");
		menu.add(0, v.getId(), 0, "Receitas");
	}

    @Override
	public boolean onContextItemSelected(MenuItem item) {
       	if(item.getTitle()=="Despesas"){function1(item.getItemId());}
    	else if(item.getTitle()=="Receitas"){function2(item.getItemId());}
    	else {return false;}
	return true;
	}

    public void function1(int id){
    		Intent trocatela = new
    		Intent(ControleNaMaoActivity.this,TelaAddDespesas.class);
    		ControleNaMaoActivity.this.startActivity(trocatela);
    		ControleNaMaoActivity.this.finish();
    }
    public void function2(int id){
		Intent trocatela = new
		Intent(ControleNaMaoActivity.this,TelaAddReceitas.class);
		ControleNaMaoActivity.this.startActivity(trocatela);
		ControleNaMaoActivity.this.finish();    }

}