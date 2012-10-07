package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import controle.mao.R;


public class TelaListaFatura extends ListActivity {
	protected static final int BUSCAR = 1;

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

		// Cria o adaptador da lista para fornecer o conteúdo
		private SimpleAdapter criaAdaptador() {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

			// Cada item na Lista é uma lista
			// Para determinar os valores existe um HashMap para cada linha
			for (int i = 0; i < 10; i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("data", "0"+i+"/10/2012");
				item.put("conta", "Exemplo Conta " + i);
				item.put("valor", "R$ " + (i*100));
				list.add(item);
			}

			// Utiliza o adaptador SimpleAdapter,

			// Array que define as chaves do HashMap
			String[] from = new String[] { "data", "conta", "valor" };

			// nome e fone são definidos no layout_contatos
			int[] to = new int[] { R.id.listItemDataReceita, R.id.listItemNomeReceita, R.id.listItemValorReceita };

			SimpleAdapter adaptador = new SimpleAdapter(this, list, R.layout.item_lista_consulta, from, to);
			return adaptador;
		}

		@Override
		protected void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
        	Intent trocatela = null;
        		trocatela = new
        		        Intent(TelaListaFatura.this,TelaEditarDespesas.class);        	
        	TelaListaFatura.this.startActivity(trocatela);
        	TelaListaFatura.this.finish();;
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
		case BUSCAR:
			startActivity(new Intent(this, TelaBuscarFatura.class));
			break;
		}
		return true;
	}
}