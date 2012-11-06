package controle.mao.visualizacao;

import java.util.List;

import controle.mao.R;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import controle.mao.controle.categoria.Categoria;
import controle.mao.controle.categoria.CategoriaAdapterListViewBD;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.dao.CategoriaDAO.Categorias;
import controle.mao.dados.util.CategoriasUtil;


public class TelaListaCategorias extends ListActivity{
	
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	private List<CategoriaDAO> categorias;
	static Categoria bdScript;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bdScript = new Categoria(new CategoriasUtil(this));
        bdScript = new Categoria(this);
        atualizarLista();
        getListView().setBackgroundResource(R.drawable.fundo);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
//		menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
		case INSERIR_EDITAR:
			// Abre a tela com o formulário para adicionar
			startActivityForResult(new Intent(this, TelaAddCategoria.class), INSERIR_EDITAR);
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
		editarCategoria(posicao);
	}
	
	public void atualizarLista() {
		// Pega a lista de categorias e exibe na tela
		categorias = bdScript.listaCategorias();
		Log.e("cnm", "Nenhum Registro?: " + categorias.isEmpty());
		// Se não tiver dados, ele vai pra tela de adicionar categoria.
		if (categorias.isEmpty()){
			startActivityForResult(new Intent(this, TelaAddCategoria.class), INSERIR_EDITAR);
//			setListAdapter(new CategoriaAdapterListViewBD(this, categorias));
		} else{
		// Adaptador de lista customizado para cada linha de uma categoria
			setListAdapter(new CategoriaAdapterListViewBD(this, categorias));
		}
	}

	// Recupera o id do carro, e abre a tela de edição
	protected void editarCategoria(int posicao) {
		// Usuário clicou em algum carro da lista
		// Recupera a categoria selecionada
		CategoriaDAO categoria = categorias.get(posicao);
		// Cria a intent para abrir a tela de editar
		Intent it = new Intent(this, TelaAddCategoria.class);
		// Passa o id do carro como parâmetro
		it.putExtra(Categorias._ID, categoria.id);
		// Abre a tela de edição
		startActivityForResult(it, INSERIR_EDITAR);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Fecha o banco
		Categoria.bdScript.fechar();
	}
	
	private static final int NONE = -1;

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//	    if (keyCode == KeyEvent.KEYCODE_BACK) {
//	        finish();
//	    }
//	    return super.onKeyDown(keyCode, event);
//	}

	@Override
	public void onBackPressed() {
//	 Toast.makeText(this, "Back key pressed =)", Toast.LENGTH_SHORT).show();
		Intent trocatela = new
		Intent(TelaListaCategorias.this,ControleNaMaoActivity.class);
		TelaListaCategorias.this.startActivity(trocatela);
		TelaListaCategorias.this.finish();
//	 super.onBackPressed();
	}

}
