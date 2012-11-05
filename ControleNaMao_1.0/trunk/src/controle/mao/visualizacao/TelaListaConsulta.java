package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controle.mao.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class TelaListaConsulta extends ListActivity {
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;

	@Override
	public void onCreate(Bundle icicle) {
			super.onCreate(icicle);

			// Apenas para brincar... sobrescreve o layout do ListView default do Android
//			setContentView(R.layout.consultas);

			SimpleAdapter adaptador = criaAdaptador();
			setListAdapter(adaptador);
	        getListView().setBackgroundResource(R.drawable.fundo);
	        getListView().setPadding(17, 10, 0, 0);
		}

		// Cria o adaptador da lista para fornecer o conte�do
		private SimpleAdapter criaAdaptador() {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			// Cada item na Lista � uma lista
			// Para determinar os valores existe um HashMap para cada linha
			for (int i = 0; i < 5; i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("data", "0"+i+"/10/2012");
				item.put("conta", "Credito Conta " + i);
				item.put("valor", "R$ " + (i*100));
				list.add(item);
			}
			for (int i = 0; i < 5; i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("data", "0"+i+"/10/2012");
				item.put("conta", "D�bito Conta " + i);
				item.put("valor", "R$ " + (i*100));
				list.add(item);
			}

			// Utiliza o adaptador SimpleAdapter,

			// Array que define as chaves do HashMap
			String[] from = new String[] { "data", "conta", "valor" };

			// nome e fone s�o definidos no layout_contatos
			
			int[] to = new int[] { R.id.listItemDataLancamento, R.id.listItemNomeLancamento, R.id.listItemValorLancamento };

			SimpleAdapter adaptador = new SimpleAdapter(this, list, R.layout.item_lista_consulta, from, to);
			return adaptador;
		}

		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			
			String formaPgt = l.getItemAtPosition(position).toString();
        	String result = "Credito";
        	Log.e("cnm", formaPgt);
        	Intent trocatela = null;
        	if (formaPgt.contains(result)){
        		trocatela = new
        		        Intent(TelaListaConsulta.this,TelaEditarReceitas.class);        	
        	}else{
        		trocatela = new
        		        Intent(TelaListaConsulta.this,TelaEditarDespesas.class);        	
        	}
			TelaListaConsulta.this.startActivity(trocatela);
			TelaListaConsulta.this.finish();;
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
//		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
		menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
		case INSERIR_EDITAR:
			// Abre a tela com o formul�rio para adicionar
//			startActivityForResult(new Intent(this, EditarCarro.class), INSERIR_EDITAR);
			break;
		case BUSCAR:
			// Abre a tela para buscar o carro pelo nome
			startActivity(new Intent(this, TelaBuscarConsulta.class));
			break;
		}
		return true;
	}
}