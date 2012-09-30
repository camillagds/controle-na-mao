package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.dados.BancoDadosScript;
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
	public static BancoDadosScript bdScript;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bdScript = new BancoDadosScript(this);
        setContentView(R.layout.main);
        ImageView btCartao = (ImageView) findViewById(R.id.itemCartao);
        ImageView btCategoria = (ImageView) findViewById(R.id.itemCategoria);
        ImageView btLancamentos = (ImageView) findViewById(R.id.itemLancamentos);
        registerForContextMenu(btLancamentos); 
        ImageView btExportarPlanilha = (ImageView) findViewById(R.id.itemExcel);
        ImageView btFatura = (ImageView) findViewById(R.id.itemFaturas);
        ImageView btConsulta = (ImageView) findViewById(R.id.itemConsultas);


        
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
        
        //Tela Exportar Planilha
        btExportarPlanilha.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(ControleNaMaoActivity.this,TelaExportarPlanilha.class);
        		ControleNaMaoActivity.this.startActivity(trocatela);
        		ControleNaMaoActivity.this.finish();
        	}
        });
    }
    
    //Tela Lista Lançamentos
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Lançamentos");
		menu.add(0, v.getId(), 0, "Despesas");
		menu.add(0, v.getId(), 0, "Receitas");
	}

    @Override
	public boolean onContextItemSelected(MenuItem item) {
       	if(item.getTitle()=="Despesas"){abrirDespesas(item.getItemId());}
    	else if(item.getTitle()=="Receitas"){abrirReceitas(item.getItemId());}
    	else {return false;}
	return true;
	}

    public void abrirDespesas(int id){
    		Intent trocatela = new
    		Intent(ControleNaMaoActivity.this,TelaAddDespesas.class);
    		ControleNaMaoActivity.this.startActivity(trocatela);
    		ControleNaMaoActivity.this.finish();
    }
    public void abrirReceitas(int id){
		Intent trocatela = new
		Intent(ControleNaMaoActivity.this,TelaAddReceitas.class);
		ControleNaMaoActivity.this.startActivity(trocatela);
		ControleNaMaoActivity.this.finish();    }
    
}