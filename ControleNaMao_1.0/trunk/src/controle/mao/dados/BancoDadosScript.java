package controle.mao.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * <pre>
 * Repositório para carros que utiliza o SQLite internamente
 * 
 * Para visualizar o banco pelo adb shell:
 * 
 * &gt;&gt; sqlite3 /data/data/br.livro.android.exemplos.banco/databases/BancoCarro
 * 
 * &gt;&gt; Mais info dos comandos em: http://www.sqlite.org/sqlite.html
 * 
 * &gt;&gt; .exit para sair
 * 
 * </pre>
 * 
 * @author rlecheta
 * 
 */
public class BancoDadosScript{
	
	private static final String[] SCRIPT_DATABASE_DELETE = new String[]{ 
			"DROP TABLE IF EXISTS tb_cartoes;",
			"DROP TABLE IF EXISTS tb_categorias;",
			"DROP TABLE IF EXISTS tb_contaPagar;",
			"DROP TABLE IF EXISTS tb_contas;",
			"DROP VIEW IF EXISTS view_fluxoCaixa;"};


	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
			"CREATE TABLE tb_cartoes ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome_cartao TEXT NOT NULL, bandeira_Cartao TEXT NOT NULL, fechaFatura_cartao INTEGER NOT NULL);",
			"CREATE TABLE tb_categorias ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome_categoria TEXT NOT NULL);",
			"CREATE TABLE tb_despesa ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, [descricao] CHAR(45) NOT NULL, [id_categoria] INT(8) NOT NULL CONSTRAINT [id_categoria] REFERENCES [tb_categorias]([id_categoria]) ON DELETE NO ACTION ON UPDATE CASCADE, [tipo_pagamento] CHAR(45) NOT NULL, [data_pagamento] DATETIME NOT NULL, [valor] FLOAT(8) NOT NULL, [parcelas] INT(2), [periodo] CHAR(45), [id_cartao] INT(8) CONSTRAINT [id_cartao] REFERENCES [tb_cartoes]([id_cartao]) ON DELETE NO ACTION ON UPDATE CASCADE);",
			"CREATE TABLE tb_recebimento ( _id INTEGER PRIMARY KEY AUTOINCREMENT, [descricao] CHAR(45) NOT NULL, [id_categoria] INT(8) NOT NULL CONSTRAINT [id_categoria] REFERENCES [tb_categorias]([id_categoria]) ON DELETE NO ACTION ON UPDATE CASCADE, [valor] FLOAT(8) NOT NULL, [data_pagamento] DATETIME NOT NULL);",
			"CREATE TABLE tb_controle ( _id INTEGER PRIMARY KEY AUTOINCREMENT, [id_recebimento] INT(8) CONSTRAINT [id_recebimento] REFERENCES [tb_recebimento]([id_rcebimento]) ON DELETE CASCADE ON UPDATE CASCADE, [id_despesa] INT(8) CONSTRAINT [id_despesa] REFERENCES [tb_despesa]([id_despesa]) ON DELETE CASCADE ON UPDATE CASCADE, [data_baixa] DATETIME, [valor] FLOAT(8));",
			//"CREATE VIEW view_fluxoCaixa AS SELECT tb_contas._id, tb_contas.tipo_conta, tb_contas.descr_conta, tb_categorias.nome_categoria, tb_contas.dtVenc_conta, tb_contaPagar.formaPgto_contaPagar, tb_cartoes.nome_cartao, tb_contaPagar.modalidade_contaPagar, tb_contaPagar.numParcela_contaPagar, tb_contas.valorPgto_conta, tb_contas.pago_conta, tb_contas.dtPgto_conta FROM tb_cartoes INNER JOIN tb_contaPagar ON (tb_cartoes._id = tb_contaPagar.id_cartao) INNER JOIN tb_categorias ON (tb_categorias._id = tb_contaPagar.categoria_contaPagar) INNER JOIN tb_contas ON (tb_contaPagar._id = tb_contas.id_contaPagar);",
//			"INSERT INTO tb_categorias(nome_categoria) values ('Geral');"
	};

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Controle de versão
	private static final int VERSAO_BANCO = 1;

	// Nome das tabelas
	public static final String TABELA_CARTOES = "tb_cartoes";
	public static final String TABELA_CATEGORIAS = "tb_categorias";
	public static final String TABELA_CONTAPAGAR = "tb_contaPagar";
	public static final String TABELA_CONTAS = "tb_contas";
	
	//Nome das Views
	public static final String VIEW_FLUXOCAIXA = "view_fluxoCaixa";

	// Classe utilitária para abrir, criar, e atualizar o banco de dados
	private SQLiteHelper dbHelper;

	protected SQLiteDatabase db;

	// Cria o banco de dados com um script SQL
	public BancoDadosScript(Context ctx) {
		// Criar utilizando um script SQL
		dbHelper = new SQLiteHelper(ctx, BancoDadosScript.NOME_BANCO, BancoDadosScript.VERSAO_BANCO,
				BancoDadosScript.SCRIPT_DATABASE_CREATE, BancoDadosScript.SCRIPT_DATABASE_DELETE);

		// abre o banco no modo escrita para poder alterar também
		db = dbHelper.getWritableDatabase();
		dbHelper.close();
	}	

	// Fecha o banco
	public void fechar() {
		if (db != null) {
			db.close();
		}
		if (dbHelper != null) {
			dbHelper.close();
		}
	}
}
