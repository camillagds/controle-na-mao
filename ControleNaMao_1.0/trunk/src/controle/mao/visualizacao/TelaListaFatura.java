package controle.mao.visualizacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import controle.mao.R;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.controle.lancamentos.FaturaAdapterListViewBD;
import controle.mao.controle.lancamentos.Lancamento;
import controle.mao.controle.lancamentos.LancamentoAdapterListViewBD;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.dao.DespesasDAO;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.util.DespesasUtil;
import controle.mao.dados.util.LancamentosUtil;
import controle.mao.dados.util.ReceitasUtil;



public class TelaListaFatura extends  Activity implements OnItemClickListener  {
	protected static final int BUSCAR = 1;

	public static Lancamento bdScript;

	private List<LancamentoDAO> lancamentos;
	private List<DespesasDAO> despesas;
	private ArrayList<HashMap<String, String>> list;

	private ListView listView;  
	private FaturaAdapterListViewBD faturaAdapterList;
	
	private TextView valorFatura;

	private ArrayList<Float> listValor;
	
	@Override
	public void onCreate(Bundle icicle) {
			super.onCreate(icicle);
		       setContentView(R.layout.fatura); 
		        bdScript = new Lancamento(new LancamentosUtil(this));
		        
		        //Lista
		        listView = (ListView)findViewById(R.id.listaLancamentosCartoes);
		        listView.setOnItemClickListener(this);
		        
		        valorFatura = (TextView)findViewById(R.id.txtTotalFatura);

		        atualizarLista();
		        
		        // Calcular Fluxo de Caixa
		    	valorFatura.setText(String.valueOf("R$ "+totalLancamentos(lancamentos)));
//		    	valorFatura.setText(String.valueOf("R$ "+totalLancamentosFake()));

	}

		private void atualizarLista() {
			//Carrega Lista
			lancamentos = bdScript.listaLancamentosCartao();
			Log.e("cnm", "Nenhum Registro?: " + lancamentos.isEmpty());
			
			if (lancamentos.isEmpty()){
				// NADA XD
				} else{

			// Cria o adapter
			 faturaAdapterList = new FaturaAdapterListViewBD(this,
			 lancamentos);
//			SimpleAdapter adaptador = criaAdaptador();

			// Define o Adapter
			 listView.setAdapter(faturaAdapterList);
//			listView.setAdapter(adaptador);

			// Cor quando a lista é selecionada para ralagem.

			listView.setCacheColorHint(Color.TRANSPARENT);

		}
		
	}
		
		public float totalLancamentosFake(){
			int total = 0;
	    	Character tipoLancamento = 'D';
	    	Character tipoCartao = 'C';
			for (int i = 0; i < list.size(); i++){
					total+= listValor.get(i);
			}
			//TODO boa sorte na logica rs
			return total;
		}
		
		public float totalLancamentos(List<LancamentoDAO> listaL){
			int total = 0;
			for (int i = 0; i < listaL.size(); i++){
					total+= listaL.get(i).valor_lancamentos;
			}
			//TODO boa sorte na logica rs
			return total;
		}

		// Cria o adaptador da lista para fornecer o conteúdo
		private SimpleAdapter criaAdaptador() {
			list = new ArrayList<HashMap<String, String>>();
			listValor = new ArrayList<Float>();
			// Cada item na Lista é uma lista
			// Para determinar os valores existe um HashMap para cada linha
			for (int i = 0; i < 10; i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("data", "0"+i+"/10/2012");
				item.put("conta", "Exemplo Conta " + i);
				item.put("valor", "R$ " + (i*120));
				listValor.add((float) (i*120));
				list.add(item);
			}

			// Utiliza o adaptador SimpleAdapter,

			// Array que define as chaves do HashMap
			String[] from = new String[] { "data", "conta", "valor" };

			// nome e fone são definidos no layout_contatos
			int[] to = new int[] { R.id.listItemDataFatura, R.id.listItemNomeFatura, R.id.listItemValorFatura };

			SimpleAdapter adaptador = new SimpleAdapter(this, list, R.layout.item_lista_fatura, from, to);
			return adaptador;
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
			startActivityForResult(new Intent(this, TelaBuscarFatura.class), BUSCAR);
//			startActivity(new Intent(this, TelaBuscarFatura.class));
			break;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
	// Toast.makeText(this, "Back key pressed =)", Toast.LENGTH_SHORT).show();
		Intent trocatela = new
		Intent(TelaListaFatura.this,ControleNaMaoActivity.class);
		TelaListaFatura.this.startActivity(trocatela);
		TelaListaFatura.this.finish();
	// super.onBackPressed();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		//TODO precisa?		
	}
	
	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);

		// Quando a activity EditarCarro retornar, seja se foi para adicionar vamos atualizar a lista
		if (codigoRetorno == RESULT_OK) {
			// atualiza a lista na tela
			atualizarLista();
		}
	}
	
}