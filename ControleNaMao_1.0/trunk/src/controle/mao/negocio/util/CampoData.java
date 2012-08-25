package controle.mao.negocio.util;

import java.util.Calendar;

import controle.mao.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

public class CampoData extends DatePicker{
	
	DatePicker campoData;
	
    public CampoData(Context context, int campoId) {
		super(context);
		campoData = (DatePicker) findViewById(campoId);
    }
    
    public CampoData(Context context) {
		super(context);
		campoData = new DatePicker(context);
    }

	/**
	 * Método que retorna a data atual para o campo "Data do Crédito
	 */
	public void setDataAtual() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into datepicker
		campoData.init(year, month, day, null);
	}
}
