package controle.mao.visualizacao;

import java.util.List;

import controle.mao.R;
import controle.mao.dados.ControleDAO;
import controle.mao.dados.ControleUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;

public class TelaListaConsultas extends Activity {
	
	public static ControleUtil bdScript;
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	private List<ControleDAO> controles;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        setContentView(R.layout.cartoes); 
        bdScript = new ControleUtil(this);
		atualizarLista();
    }


protected void atualizarLista() {
	// Pega a lista de categorias e exibe na tela
	controles = bdScript.listarControle();
	Log.e("cnm", "Nenhum Registro?: " + controles.isEmpty());
	// Se não tiver dados, ele vai pra tela de adicionar categoria.
	if (controles.isEmpty()){
		startActivityForResult(new Intent(this, ControleNaMaoActivity.class), INSERIR_EDITAR);
	} else{
	// Adaptador de lista customizado para cada linha de uma categoria
//		setListAdapter(new ReceitaAdapterListViewBD(this, controles));
	}
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
	menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
	return true;
}

@Override
public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// Clicou no menu
	switch (item.getItemId()) {
	case INSERIR_EDITAR:
		// Abre a tela com o formulário para adicionar
		//TODO dar um jeito dele saber se é despesa ou receita e abrir a tela certa
		startActivityForResult(new Intent(this, TelaAddCartoes.class), INSERIR_EDITAR);
		break;
	case BUSCAR:
		//TODO Implementar Busca
		// Abre a tela para buscar o carro pelo nome
		//startActivity(new Intent(this, BuscarCarro.class));
		break;
	}
	return true;
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
