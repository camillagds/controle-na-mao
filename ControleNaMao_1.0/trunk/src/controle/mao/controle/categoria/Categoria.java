package controle.mao.controle.categoria;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import controle.mao.controle.cartoes.Cartao;
import controle.mao.controle.lancamentos.Despesa;
import controle.mao.dados.dao.CategoriaDAO;
import controle.mao.dados.util.CategoriasUtil;
import controle.mao.dados.util.DespesasUtil;
import controle.mao.visualizacao.TelaAddCategoria;

public class Categoria extends Activity{
	
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	public static CategoriasUtil bdScript;
	private Long id;

	public Categoria(CategoriasUtil bdScript){
		Categoria.bdScript = bdScript;
	}
	
	public Categoria(Context ctx){
		Categoria.bdScript = new CategoriasUtil(ctx);
	}
	
	public void salvar() {

		CategoriaDAO categoria = new CategoriaDAO();
		id = TelaAddCategoria.getID();
		Log.e("cnm", "ID: " + id);
		
		if (id != null) {
			// É uma atualização
			categoria.id = id;
		}
		categoria.nome_categoria = TelaAddCategoria.campoNome.getText().toString();


		// Salvar
		salvarCategoria(categoria);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		id = TelaAddCategoria.getID();
		Log.e("cnm", "ID: " + id);

		if (id != null) {
			excluirCategoria(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a categoria pelo id
	public CategoriaDAO buscarCategoria(long id) {
		return bdScript.buscarCategoria(id);
	}

	// Salvar a categoria
	protected void salvarCategoria(CategoriaDAO categoria) {
		bdScript.salvar(categoria);
	}

	// Excluir a categoria
	protected void excluirCategoria(long id) {
		bdScript.deletar(id);
	}

	public static List<CategoriaDAO> listaCategorias() {
		// TODO Auto-generated method stub
		return bdScript.listarCategorias();
	}
	
	public static List<String> SpinnerCategorias(){
		// Lista - Categorias
		List<CategoriaDAO> categorias = listaCategorias();
 		List<String> nomesCategorias = new ArrayList<String>();
 		for (int i = 0; i < categorias.size(); i++) {
 			nomesCategorias.add(categorias.get(i).nome_categoria);
 		}
 		return nomesCategorias;
	}
	
	public static long BuscarIdCategoria(String nome){
		long id = 0;
		id = bdScript.buscarCategoriaPorNome(nome).id;
		return id;
	}
		
}
