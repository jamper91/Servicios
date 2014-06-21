package com.jamper91.Administrador;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.Inicio;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Preferencias extends Activity {

	TextView txtValidar;
	Administrador admin= Administrador.getInstance(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrador_preferencias);
		inicializar();
	}
	private void inicializar()
	{
		txtValidar=(TextView)findViewById(R.id.administradorPreferencias_txtValidar);
		String p=admin.getParametroByNombre("validar");
		if(p!=null)
		{
			txtValidar.setText(p);
		}
	}
	
	public void ejecutar(View v)
	{ 	
		switch (v.getId()) {
		case R.id.administradorPreferencias_btnAceptar:
			int cant=Integer.parseInt(txtValidar.getText().toString());
			admin.addParametro("validar", cant+"");
			dialogo("Parametro almacenado con éxito", "Archivos");
			Log.i("ejecutar", "almacenado con exito: "+cant);
			break;
		case R.id.administradorPreferencias_btnSalir:
			Intent i = new Intent(this, Inicio.class);
			startActivity(i);
			break;
		default:
			break;
		}
	}
	private void dialogo(String men, String tit)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage(men)
		       .setTitle(tit);

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
