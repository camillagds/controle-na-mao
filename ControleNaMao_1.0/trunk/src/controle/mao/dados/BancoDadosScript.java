package controle.mao.dados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Repositório para dados que utiliza o SQLite internamente
 * 
 * 
 * @author camilas
 * 
 */
public class BancoDadosScript{
	
	private static final String[] SCRIPT_DATABASE_DELETE = new String[]{ 
			"DROP TABLE IF EXISTS tb_cartao;",
			"DROP TABLE IF EXISTS tb_categoria;",
			"DROP TABLE IF EXISTS tb_lancamento;",
			"DROP TABLE IF EXISTS tb_recebimento;",
			"DROP TABLE IF EXISTS tb_despesa;"};


//	// Cria a tabela com o "_id" sequencial
//	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
//			"CREATE TABLE tb_cartoes ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome_cartao TEXT NOT NULL, bandeira_Cartao TEXT NOT NULL, fechaFatura_cartao INTEGER NOT NULL);",
//			"CREATE TABLE tb_categorias ( _id INTEGER PRIMARY KEY AUTOINCREMENT, nome_categoria TEXT NOT NULL);",
//			"CREATE TABLE tb_despesa ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, [descricao] CHAR(45) NOT NULL, [id_categoria] INT(8) NOT NULL CONSTRAINT [id_categoria] REFERENCES [tb_categorias]([id_categoria]) ON DELETE NO ACTION ON UPDATE CASCADE, [tipo_pagamento] CHAR(45) NOT NULL, [data_pagamento] DATETIME NOT NULL, [valor] FLOAT(8) NOT NULL, [parcelas] INT(2), [periodo] CHAR(45), [id_cartao] INT(8) CONSTRAINT [id_cartao] REFERENCES [tb_cartoes]([id_cartao]) ON DELETE NO ACTION ON UPDATE CASCADE);",
//			"CREATE TABLE tb_recebimento ( _id INTEGER PRIMARY KEY AUTOINCREMENT, [descricao] CHAR(45) NOT NULL, [id_categoria] INT(8) NOT NULL CONSTRAINT [id_categoria] REFERENCES [tb_categorias]([id_categoria]) ON DELETE NO ACTION ON UPDATE CASCADE, [valor] FLOAT(8) NOT NULL, [data_pagamento] DATETIME NOT NULL);",
//			"CREATE TABLE tb_controle ( _id INTEGER PRIMARY KEY AUTOINCREMENT, [id_recebimento] INT(8) CONSTRAINT [id_recebimento] REFERENCES [tb_recebimento]([id_rcebimento]) ON DELETE CASCADE ON UPDATE CASCADE, [id_despesa] INT(8) CONSTRAINT [id_despesa] REFERENCES [tb_despesa]([id_despesa]) ON DELETE CASCADE ON UPDATE CASCADE, [data_baixa] DATETIME, [valor] FLOAT(8));",
//	};
	
	// Cria as tabelas com o "_id" sequencial e insere a Categoria padrão.
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		"CREATE TABLE [tb_cartao] ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [nome_cartao] CHAR(30) NOT NULL, [bandeira_Cartao] CHAR(15) NOT NULL, [fechaFatura_cartao] INTEGER(2) NULL);",
		"CREATE TABLE [tb_categoria] ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [nome_categoria] CHAR(15) NOT NULL);",
		"INSERT INTO [tb_categoria]([nome_categoria]) VALUES ('Geral');",
		"CREATE TABLE [tb_lancamento] ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,[tipo_Lancamento] CHAR(1) NOT NULL,[descricao] CHAR(35) NOT NULL, [id_categoria] INTEGER NOT NULL CONSTRAINT [_id] REFERENCES [tb_categoria]([_id]) ON DELETE NO ACTION ON UPDATE NO ACTION,[data_baixa] TEXT NULL, [valor] FLOAT(9), [pago] BOOLEAN NULL);",
		"CREATE TABLE [tb_recebimento] ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [id_lancamento] INTEGER NOT NULL CONSTRAINT [_id] REFERENCES [tb_lancamento]([_id]) ON DELETE CASCADE ON UPDATE CASCADE,[data_credito] TEXT NOT NULL);",
		"CREATE TABLE [tb_despesa] ( [_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [id_lancamento] INTEGER NOT NULL CONSTRAINT [_id] REFERENCES [tb_lancamento]([_id]) ON DELETE CASCADE ON UPDATE CASCADE,[data_vencimento] TEXT NOT NULL, [forma_pagto] CHAR(8) NOT NULL, [tipo_cartao] CHAR(7) NULL, [id_cartao] INTEGER NULL CONSTRAINT [_id] REFERENCES [tb_cartoes]([_id]) ON DELETE NO ACTION NULL);"
};

	// Nome do banco
	private static final String NOME_BANCO = "bd_cnm";

	// Controle de versão
	private static final int VERSAO_BANCO = 1;
	
	//Nome das Views
//	public static final String VIEW_FLUXOCAIXA = "view_fluxoCaixa";

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
	    if (!db.isReadOnly()) {
	        // Habilita chave estrangeira
	        db.execSQL("PRAGMA foreign_keys=ON;");
	    }
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
