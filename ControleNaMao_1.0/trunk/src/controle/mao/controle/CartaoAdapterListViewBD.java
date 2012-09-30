package controle.mao.controle;

import java.util.List;

import controle.mao.R;
import controle.mao.dados.CartaoDAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartaoAdapterListViewBD extends BaseAdapter{

		private Context context;
	    private List<CartaoDAO> lista;

	    public CartaoAdapterListViewBD(Context context, List<CartaoDAO> cartoes) {
	    	//Itens que preencheram o listview
	        this.lista = cartoes;
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
	    public CartaoDAO getItem(int position) {
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
	        CartaoDAO item = lista.get(position);
	        
	        //infla o layout para podermos preencher os dados
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.item_lista_cartao, null);

	        //Atualiza o valor do TextView
	        TextView nome = (TextView) view.findViewById(R.id.listItemNomeCartao);
	        nome.setText(item.nome_cartao);
	        
	        TextView bandeira = (TextView) view.findViewById(R.id.listItemBandeiraCartao);
	        bandeira.setText(item.bandeira_Cartao);
	        //TODO Preencher resto dos campos (copiando as 2 linhas acima)
	        
	        return view;
	    }
	

}
