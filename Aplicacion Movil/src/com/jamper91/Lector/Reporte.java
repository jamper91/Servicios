package com.jamper91.Lector;

import java.util.Vector;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Reporte extends Activity {
	Administrador admin = Administrador.getInstance(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte);
		inicializar();
	}

	private void inicializar() {
		TableLayout datos = (TableLayout) findViewById(R.id.Reporte_tblReporte);
		//TextView txtMensaje=(TextView)findViewById(R.id.reporte_txtMensaje);
		// Obtengo todos los planesLectura del usuario logeado
		Vector<String[]> planes = admin
				.getPlanLecturasByLogin(admin.getLogin());
		//Agrego las cabezeras
		TableRow row2 = new TableRow(this);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams(
				TableRow.LayoutParams.MATCH_PARENT);
		row2.setLayoutParams(lp2);
		TextView txtCiclo2 = new TextView(this);
		txtCiclo2.setGravity(Gravity.CENTER);
		txtCiclo2.setTextColor(Color.parseColor("#ffffff"));
//		txtCiclo.setLayoutParams(lp);
		
		TextView txtRuta2 = new TextView(this);
		txtRuta2.setGravity(Gravity.CENTER);
//		txtRuta.setLayoutParams(lp);
		txtRuta2.setTextColor(Color.parseColor("#ffffff"));
		
		TextView txtCantidad2 = new TextView(this);
		txtCantidad2.setGravity(Gravity.CENTER);
//		txtCantidad.setLayoutParams(lp);
		txtCantidad2.setTextColor(Color.parseColor("#ffffff"));
		
		TextView txtTomadas2 = new TextView(this);
		txtTomadas2.setGravity(Gravity.CENTER);
//		txtTomadas.setLayoutParams(lp);
		txtTomadas2.setTextColor(Color.parseColor("#ffffff"));
		
		TextView txtPendientes2 = new TextView(this);
		txtPendientes2.setGravity(Gravity.CENTER);
//		txtPendientes.setLayoutParams(lp);
		txtPendientes2.setTextColor(Color.parseColor("#ffffff"));
		
		txtCiclo2.setText("Ciclo");
		txtRuta2.setText("Ruta");
		txtCantidad2.setText("Lecturas");
		txtTomadas2.setText("Tomadas");
		txtPendientes2.setText("Pendiente");

		row2.addView(txtCiclo2,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
		row2.addView(txtRuta2,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
		row2.addView(txtCantidad2,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
		row2.addView(txtTomadas2,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
		row2.addView(txtPendientes2,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
		datos.addView(row2);
		if (planes != null) {
			Log.i("inicializar", planes.toString());
			for (String[] plan : planes) {
				// Obtengo el ciclo y ruta y consulto lo necesario
				String ciclo = plan[0];
				String ruta = plan[1];
				String inf[] = admin.getInformacionRuta(ciclo, ruta);
				if (inf != null) {
					TableRow row = new TableRow(this);
					TableRow.LayoutParams lp = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT);
					row.setLayoutParams(lp);
					TextView txtCiclo = new TextView(this);
					txtCiclo.setGravity(Gravity.CENTER);
					txtCiclo.setTextColor(Color.parseColor("#ffffff"));
//					txtCiclo.setLayoutParams(lp);
					
					TextView txtRuta = new TextView(this);
					txtRuta.setGravity(Gravity.CENTER);
//					txtRuta.setLayoutParams(lp);
					txtRuta.setTextColor(Color.parseColor("#ffffff"));
					
					TextView txtCantidad = new TextView(this);
					txtCantidad.setGravity(Gravity.CENTER);
//					txtCantidad.setLayoutParams(lp);
					txtCantidad.setTextColor(Color.parseColor("#ffffff"));
					
					TextView txtTomadas = new TextView(this);
					txtTomadas.setGravity(Gravity.CENTER);
//					txtTomadas.setLayoutParams(lp);
					txtTomadas.setTextColor(Color.parseColor("#ffffff"));
					
					TextView txtPendientes = new TextView(this);
					txtPendientes.setGravity(Gravity.CENTER);
//					txtPendientes.setLayoutParams(lp);
					txtPendientes.setTextColor(Color.parseColor("#ffffff"));
					
					txtCiclo.setText(ciclo + "");
					txtRuta.setText(ruta + "");
					txtCantidad.setText(inf[0]);
					txtTomadas.setText(inf[1]);
					txtPendientes.setText(inf[2]);

					row.addView(txtCiclo,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
					row.addView(txtRuta,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
					row.addView(txtCantidad,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
					row.addView(txtTomadas,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
					row.addView(txtPendientes,new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
					datos.addView(row);

					
				}else{
					Log.i("inicializar",
							"Error, no hay informacion de la ruta con ciclo: "+ ciclo + " y ruta: "+ruta);
				}

			}
		} else {
			Log.i("inicializar",
					"Error, no hay informacion de planlectura con el usuario: "
							+ admin.getLogin());
		}

	}

}
