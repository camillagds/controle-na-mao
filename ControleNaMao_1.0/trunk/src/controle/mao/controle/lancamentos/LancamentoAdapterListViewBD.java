package controle.mao.controle.lancamentos;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import controle.mao.R;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.LancamentoDAO;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LancamentoAdapterListViewBD extends BaseAdapter{

		private Context context;
	    private List<LancamentoDAO> lista;

	    public LancamentoAdapterListViewBD(Context context, List<LancamentoDAO> lista) {
	    	//Itens que preencheram o listview
	        this.lista = lista;
	        //responsavel por pegar o Layout do item.
	        this.context = context;
	    }

	    /**
	     * Retorna a quantidade de itens
	     *
	     * @return
	     */
	    public int getCount() {
	        return lista.size();
	    }

	    /**
	     * Retorna o item de acordo com a posicao dele na tela.
	     *
	     * @param position
	     * @return
	     */
	    public LancamentoDAO getItem(int position) {
	        return lista.get(position);
	    }

	    /**
	     * Sem implementação
	     *
	     * @param position
	     * @return
	     */
	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        //Pega o item de acordo com a posção.
	        LancamentoDAO item = lista.get(position);
	        
	        
	        //infla o layout para podermos preencher os dados
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.item_lista_consulta, null);

			//TODO Jogar um if ai pra saber se eh D ou R e assim montar o set certo da data do registro correspondente.
			
			
	        //Atualiza o valor do TextView
	        TextView tipo = (TextView) view.findViewById(R.id.listItemTipoLancamento);
	        tipo.setText(item.tipoLancamento_lancamentos.toString());
	        Log.i("cnm",item.tipoLancamento_lancamentos);

	        
	        TextView data = (TextView) view.findViewById(R.id.listItemDataLancamento);
	        //TODO tratar data
	        data.setText(item.dataBaixa_lancamentos.toString());
//	        data.setText("03/11/2012");
	        TextView nome = (TextView) view.findViewById(R.id.listItemNomeLancamento);
	        nome.setText(item.descricao_lancamentos.toString());
	        
	        TextView valor = (TextView) view.findViewById(R.id.listItemValorLancamento);
	        String valorT = new DecimalFormat("00.00").format(item.valor_lancamentos);
	        valor.setText("R$ " + valorT);
	        
	        return view;
	    }
	    
	    
	

}
