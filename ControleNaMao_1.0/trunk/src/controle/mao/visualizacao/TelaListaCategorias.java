package controle.mao.visualizacao;

import java.util.ArrayList;

import controle.mao.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import controle.mao.negocio.categorias.CategoriaAdapterListView;
import controle.mao.negocio.categorias.CategoriaItemListView;


public class TelaListaCategorias extends Activity implements OnItemClickListener{

	private ListView listaCategoriaView;
    private CategoriaAdapterListView adapterListView;
    private ArrayList<CategoriaItemListView> itemListView;
	private Button btAddCategoria;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria);
        
        btAddCategoria = (Button) findViewById(R.id.btAddCategoria);
        
        //Tela Lista Categorias

        //Pega a referencia do ListView
        listaCategoriaView = (ListView) findViewById(R.id.listaCategoria);
        //Define o Listener quando alguem clicar no item.
        listaCategoriaView.setOnItemClickListener(this);

        createListView();
        
        //Botão Adicionar Categoria
        btAddCategoria.setOnClickListener(new ImageView.OnClickListener(){
        	public void onClick(View v){
        		Intent trocatela = new
        		Intent(TelaListaCategorias.this,TelaAddCategoria.class);
        		TelaListaCategorias.this.startActivity(trocatela);
        		TelaListaCategorias.this.finish();
        	}
        });
    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itemListView = new ArrayList<CategoriaItemListView>();
//        ArrayAdapter<String> listaCategoriaXML = new ArrayAdapter<String>(this, R.array.categorias);
        String[] listaString = getResources().getStringArray(R.array.categorias);
        
        for(int i = 0;i < listaString.length;i++){
//        String x = listaCategoriaXML.getItem(i);
//        String[] x = getResources().getStringArray(R.array.categorias);
        itemListView.add(new CategoriaItemListView(listaString[i]));
        }


        //Cria o adapter
        adapterListView = new CategoriaAdapterListView(this, itemListView);

        //Define o Adapter
        listaCategoriaView.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listaCategoriaView.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //Pega o item que foi selecionado.
        CategoriaItemListView item = adapterListView.getItem(arg2);
        //Demostração
        Toast.makeText(this, "Você Clicou em: " + item.getTexto(), Toast.LENGTH_LONG).show();
    }

}
