package com.jamper91.Lector;

import java.io.File;

import com.jamper91.Lector.DialogoCausal.DialogoCausalListener;
import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DialogoReenrutar extends DialogFragment {
		// Elementos graficos a usar
		EditText txtCiclo,txtRuta, txtConsecutivo;
		int oldCiclo,oldRuta,oldConsecutivo;
		View view;

		Administrador admin = Administrador.getInstance(null);
		
		
		
		public interface DialogoReenrutarListener {
			public void onDialogAceptarReenrutarClick(DialogFragment dialog, int ciclo, int ruta, int consecutivo);
		}

		DialogoReenrutarListener mListener;

		@Override
		public void onAttach(Activity activity) {
			// TODO Auto-generated method stub
			super.onAttach(activity);
			try {
				mListener = (DialogoReenrutarListener) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString()
						+ " debe implementar DialogoReenrutarListener");
			}
		}
		
		//Parametros que recibe este dialogo
		static DialogoReenrutar newInstance(int oldCilo, int oldRuta,int oldConsecutivo)
		{
			DialogoReenrutar dC=new DialogoReenrutar();
			Bundle args=new Bundle();
			args.putString("oldCilo", oldCilo+"");
			args.putString("oldRuta", oldRuta+"");
			args.putString("oldConsecutivo", oldConsecutivo+"");
			dC.setArguments(args);
			return dC;
		}
		//Metodo que crear el dialogo
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        oldCiclo =Integer.parseInt(getArguments().getString("oldCilo"));
	        oldRuta=Integer.parseInt(getArguments().getString("oldRuta"));
	        oldConsecutivo=Integer.parseInt(getArguments().getString("oldConsecutivo"));
	        Log.i("oldCiclo", oldCiclo+"");
	        Log.i("oldRuta", oldRuta+"");
	        Log.i("oldConsecutivo", oldConsecutivo+"");
	        
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			view = getActivity().getLayoutInflater().inflate(
					R.layout.dialogo_reenrutar, null);
			iniciarlizar();
			builder.setView(view)
					// Set the action buttons
					.setPositiveButton("Aceptar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {

									// Me encargo de agrega la causal a la base de
									// datos y luego se la paso al que llamo este
									// dialogo
									

								}
							})
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int id) {
									// mListener.onDialogCancelarClick(DialogoCicloRuta.this);
								}
							});
			return builder.create();
		}
		public void iniciarlizar() {
			txtCiclo = (EditText) view.findViewById(R.id.DialogoReenrutar_txtCiclo);
			txtRuta = (EditText) view.findViewById(R.id.DialogoReenrutar_txtRuta);
			txtConsecutivo = (EditText) view.findViewById(R.id.DialogoReenrutar_txtConsecutivo);
			txtCiclo.setText(oldCiclo+"");
			txtRuta.setText(oldRuta+"");
			txtConsecutivo.setText(oldConsecutivo+"");
			
			txtCiclo.addTextChangedListener(new TextWatcher() {

				    public void onTextChanged(CharSequence s, int start, int before,
				            int count) {
				        if(!s.equals("") )
				        { 
				        	txtRuta.setText("0");
				        	txtConsecutivo.setText("0");
				        }

				    }

				    public void beforeTextChanged(CharSequence s, int start, int count,
				            int after) {

				    }

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
			txtRuta.addTextChangedListener(new TextWatcher() {

			    public void onTextChanged(CharSequence s, int start, int before,
			            int count) {
			        if(!s.equals("") )
			        { 
			        	txtConsecutivo.setText("0");
			        }

			    }

			    public void beforeTextChanged(CharSequence s, int start, int count,
			            int after) {

			    }

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}

		
		//Para evitar que se cierre el dialgo si los datos estan incompletos
		@Override
		public void onStart()
		{
		    super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
		    AlertDialog d = (AlertDialog)getDialog();
		    if(d != null)
		    {
		        Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
		        positiveButton.setOnClickListener(new View.OnClickListener()
		                {
		                    @Override
		                    public void onClick(View v)
		                    {
		                    	int ciclo,ruta,consecutivo;
		                        if(txtCiclo.getText().toString().length()>0 && txtRuta.getText().toString().length()>0 && txtConsecutivo.getText().toString().length()>0)
		                        {
			                        ciclo = Integer.parseInt(txtCiclo.getText().toString());
			                        ruta= Integer.parseInt(txtRuta.getText().toString());
			                        consecutivo=Integer.parseInt(txtConsecutivo.getText().toString());
		                        	mListener.onDialogAceptarReenrutarClick(DialogoReenrutar.this, ciclo, ruta, consecutivo);
		                        	dismiss();
		                        	
		                        }else{
		                        	Toast.makeText(getActivity(), "Datos incompletos", Toast.LENGTH_SHORT).show();
		                        }
		                        
		                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
		                    }
		                });
		    }
		}
}
