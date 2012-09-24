package controle.mao.visualizacao;

import controle.mao.R;
import controle.mao.dados.CategoriaDAO;
import controle.mao.dados.CategoriaDAO.Categorias;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class TelaAddCategoria extends Activity {

	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	
	// Campos texto
	private EditText campoNome;
	private Long id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.add_categorias);
        
		campoNome = (EditText) findViewById(R.id.txtNomeCategoria);
		id = null;
		
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(Categorias._ID);

			if (id != null) {
				// é uma edição, busca o carro...
				CategoriaDAO c = buscarCategoria(id);
				campoNome.setText(c.nome_categoria);
			}
		}
		
        
		// Listener para cancelar a inclusao/edicao o categoria
        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelarCategoria);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				// Fecha a tela
				finish();
			}
		});

		// Listener para salvar o categoria
		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvarCategoria);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});

		// Listener para excluir o categoria
		ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluirCategoria);

		if (id == null) {
			// Se id está nulo, não pode excluir
			btExcluir.setVisibility(View.INVISIBLE);
		} else {
			// Listener para excluir o carro
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}  
    }

    @Override
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente
		setResult(RESULT_CANCELED);

		// Fecha a tela
		finish();
	}

	public void salvar() {

		CategoriaDAO categoria = new CategoriaDAO();
		if (id != null) {
			// É uma atualização
			categoria.id = id;
		}
		categoria.nome_categoria = campoNome.getText().toString();


		// Salvar
		salvarCategoria(categoria);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirCategoria(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar a categoria pelo id
	protected CategoriaDAO buscarCategoria(long id) {
		return TelaListaCategorias.bdScript.buscarCategoria(id);
	}

	// Salvar a categoria
	protected void salvarCategoria(CategoriaDAO carro) {
		TelaListaCategorias.bdScript.salvar(carro);
	}

	// Excluir a categoria
	protected void excluirCategoria(long id) {
		TelaListaCategorias.bdScript.deletar(id);
	}
}

