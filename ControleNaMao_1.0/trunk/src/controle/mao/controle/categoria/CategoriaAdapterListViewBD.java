package controle.mao.controle.categoria;

import java.util.List;

import controle.mao.R;
import controle.mao.dados.dao.CategoriaDAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriaAdapterListViewBD extends BaseAdapter{

		private Context context;
	    private List<CategoriaDAO> lista;

	    public CategoriaAdapterListViewBD(Context context, List<CategoriaDAO> lista) {
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
	    public CategoriaDAO getItem(int position) {
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
	        CategoriaDAO item = lista.get(position);
	        
	        //infla o layout para podermos preencher os dados
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.item_lista_categoria, null);

	        //Atualiza o valor do TextView
	        TextView nome = (TextView) view.findViewById(R.id.listItemCategoria);
	        nome.setText(item.nome_categoria);
	        
	        return view;
	    }
	

}
