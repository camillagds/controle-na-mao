package controle.mao.visualizacao;

import java.util.List;

import controle.mao.R;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.controle.lancamentos.Lancamento;
import controle.mao.controle.lancamentos.LancamentoAdapterListViewBD;
import controle.mao.controle.lancamentos.Receita;
import controle.mao.dados.dao.DespesasDAO.Despesas;
import controle.mao.dados.dao.LancamentoDAO;
import controle.mao.dados.dao.LancamentoDAO.Lancamentos;
import controle.mao.dados.dao.ReceitasDAO;
import controle.mao.dados.dao.ReceitasDAO.Receitas;
import controle.mao.dados.util.DespesasUtil;
import controle.mao.dados.util.LancamentosUtil;
import controle.mao.dados.util.ReceitasUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;

public class TelaListaLancamentos extends ListActivity {
	
	public static Lancamento bdScript;
	public static Receita bdScriptR;
	public static Despesa bdScriptD;

	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	private List<LancamentoDAO> lancamentos;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
//        setContentView(R.layout.cartoes); 
        bdScript = new Lancamento(new LancamentosUtil(this));
        bdScriptR = new Receita(new ReceitasUtil(this));
        bdScriptD = new Despesa(new DespesasUtil(this));

        atualizarLista();
		getListView().setBackgroundResource(R.drawable.fundo);
    }


protected void atualizarLista() {
	// Pega a lista de categorias e exibe na tela
	lancamentos = bdScript.listaLancamentos();
	Log.e("cnm", "Nenhum Registro?: " + lancamentos.isEmpty());
	// Se não tiver dados, ele vai pra tela de adicionar categoria.
	if (lancamentos.isEmpty()){
//		startActivityForResult(new Intent(this, TelaAddReceitas.class), INSERIR_EDITAR);
	} else{
	// Adaptador de lista customizado para cada linha de uma categoria
		setListAdapter(new LancamentoAdapterListViewBD(this, lancamentos));
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
//		startActivityForResult(new Intent(this, TelaAddCartoes.class), INSERIR_EDITAR);
		break;
	case BUSCAR:
		//TODO Implementar Busca
		// Abre a tela para buscar o carro pelo nome
		startActivity(new Intent(this, TelaBuscarFatura.class));
		break;
	}
	return true;
}

@Override
protected void onListItemClick(ListView l, View v, int posicao, long id) {
	super.onListItemClick(l, v, posicao, id);
	editarLancamento(posicao);
}

// Recupera o id do carro, e abre a tela de edição
protected void editarLancamento(int posicao) {
	// Usuário clicou em algum carro da lista
	Intent it;
	// Recupera a categoria selecionada
	LancamentoDAO lancamento = lancamentos.get(posicao);
//	ReceitasUtil lancamentos = new ReceitasUtil(getApplicationContext());
	char[] temp = lancamento.tipoLancamento_lancamentos.toCharArray();
	Character R = 'R';
	if(R.equals(temp[0])){
	// Cria a intent para abrir a tela de editar
	it = new Intent(this, TelaEditarReceitas.class);
	}
	else {
		// Cria a intent para abrir a tela de editar
		it = new Intent(this, TelaEditarDespesas.class);
	}
		// Passa o id do carro como parâmetro
		it.putExtra(Lancamentos._ID, lancamento.id);	
	
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
	Lancamento.bdScript.fechar();
	Receita.bdScript.fechar();
	Despesa.bdScript.fechar();

}

}
