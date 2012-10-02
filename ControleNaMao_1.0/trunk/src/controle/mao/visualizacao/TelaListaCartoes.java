package controle.mao.visualizacao;

import java.util.List;

import controle.mao.R;
import controle.mao.controle.CartaoAdapterListViewBD;
import controle.mao.dados.CartaoDAO;
import controle.mao.dados.CartaoDAO.Cartoes;
import controle.mao.dados.CartoesUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class TelaListaCartoes extends ListActivity {
	
	public static CartoesUtil bdScript;
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	private List<CartaoDAO> cartoes;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        setContentView(R.layout.cartoes); 
        bdScript = new CartoesUtil(this);
		atualizarLista();
		getListView().setBackgroundResource(R.drawable.fundo);
    }


protected void atualizarLista() {
	// Pega a lista de categorias e exibe na tela
	cartoes = bdScript.listarCartao();
	Log.e("cnm", "Nenhum Registro?: " + cartoes.isEmpty());
	// Se não tiver dados, ele vai pra tela de adicionar categoria.
	if (cartoes.isEmpty()){
		startActivityForResult(new Intent(this, TelaAddCartoes.class), INSERIR_EDITAR);
	} else{
	// Adaptador de lista customizado para cada linha de uma categoria
		setListAdapter(new CartaoAdapterListViewBD(this, cartoes));
	}
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
//	menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
	return true;
}

@Override
public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// Clicou no menu
	switch (item.getItemId()) {
	case INSERIR_EDITAR:
		// Abre a tela com o formulário para adicionar
		startActivityForResult(new Intent(this, TelaAddCartoes.class), INSERIR_EDITAR);
		break;
	case BUSCAR:
		//TODO Implementar Busca
		// Abre a tela para buscar o carro pelo nome
		//startActivity(new Intent(this, TelaBuscarFatura.class));
		break;
	}
	return true;
}

@Override
protected void onListItemClick(ListView l, View v, int posicao, long id) {
	super.onListItemClick(l, v, posicao, id);
	editarCartao(posicao);
}

// Recupera o id do carro, e abre a tela de edição
protected void editarCartao(int posicao) {
	// Usuário clicou em algum carro da lista
	// Recupera a categoria selecionada
	CartaoDAO cartao = cartoes.get(posicao);
	// Cria a intent para abrir a tela de editar
	Intent it = new Intent(this, TelaAddCartoes.class);
	// Passa o id do carro como parâmetro
	it.putExtra(Cartoes._ID, cartao.id);
	// Abre a tela de edição
	startActivityForResult(it, INSERIR_EDITAR);
}

@Override
protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
	super.onActivityResult(codigo, codigoRetorno, it);

	// Quando a activity TelaAddCategoria retornar, seja se foi para adicionar vamos atualizar a lista
	if (codigoRetorno == RESULT_OK) {
		// atualiza a lista na tela
		atualizarLista();
	}
}

@Override
protected void onDestroy() {
	super.onDestroy();

	// Fecha o banco
	bdScript.fechar();
}

}
