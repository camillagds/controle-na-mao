package controle.mao.negocio.util;

import java.util.Calendar;

import controle.mao.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DialogoData extends DialogFragment implements DatePickerDialog.OnDateSetListener{
	private DatePicker campoData;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
    	campoData = (DatePicker) view;
    	campoData.init(year, month, day, null);
	}
    
    /**
     * Retorna a data capturada pelo usuario
     */
    public DatePicker getDataUsuario(){
    	return campoData;
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
