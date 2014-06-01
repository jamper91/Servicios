package com.jamper91.Lector;

import java.io.Console;
import java.io.File;
import java.util.Vector;

import com.jamper91.Lector.DialogoCausal.DialogoCausalListener;
import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
@SuppressLint("NewApi")
public class DialogoObservaciones extends DialogFragment 
{
	View view;
	//Elementos de la interfaz grafica
	EditText txtOb1,txtOb2,txtOb3;
	ImageView btnOb1,btnOb2,btnOb3;
	
	//Elementos recibidos como parametros
	String obs1=null,obs2=null,obs3=null,enrutamiento,matricula;
	String path=null;
	//Variables que determinar si se tomo o no la foto
	Boolean fo1=false,fo2=false,fo3=false;
	
	//Variable que contiene todas las Observaciones
	Vector<String> observaciones=null;
	
	Administrador admin = Administrador.getInstance(null);
	private final int REQUEST_CAMERA01 = 1,REQUEST_CAMERA02 = 2,REQUEST_CAMERA03 = 3;
	static DialogoObservaciones newInstance(String ob1,String ob2, String ob3, String en, String ma)
	{
		DialogoObservaciones dC=new DialogoObservaciones();
		Bundle args=new Bundle();
		args.putString("ob1", ob1);
		args.putString("ob2", ob2);
		args.putString("ob3", ob3);
		args.putString("enrutamiento", en);
		args.putString("matricula", ma);
		dC.setArguments(args);
		return dC;
	}

	public interface DialogoObservacionesListener {
		public void onDialogAceptarClick(DialogFragment dialog, String ob1,String ob2,String ob3);
	}
	
	DialogoObservacionesListener mListener;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (DialogoObservacionesListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar DialogoObservacionesListener");
		}
	}
	public void iniciarlizar()
	{
		observaciones=admin.getAllElementos("Observaciones");
		txtOb1 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs1);
		txtOb1.setText(obs1);
		txtOb2 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs2);
		txtOb2.setText(obs2);
		txtOb3 = (EditText) view.findViewById(R.id.DialogoObservacion_txtCodObs3);
		txtOb3.setText(obs3);
		btnOb1 = (ImageView) view.findViewById(R.id.DialogoObservacion_btnCamara1);
		btnOb2 = (ImageView) view.findViewById(R.id.imgEncuestas);
		btnOb3 = (ImageView) view.findViewById(R.id.DialogoObservacion_btnCamara3);
		btnOb1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"01.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA01);

    			} catch (Exception e) {

    			}
            }
        });
		btnOb2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"02.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA02);

    			} catch (Exception e) {

    			}
            }
        });
		btnOb3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path+"03.jpg");
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(Intent.createChooser(i, "Capturar Foto"), REQUEST_CAMERA03);

    			} catch (Exception e) {

    			}
            }
        });
	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Log.i("requestCode", requestCode+"");
			  
			  switch (requestCode)
		        {
		        	case REQUEST_CAMERA01:        
		        		fo1=true;
		            break;
		        	case REQUEST_CAMERA02:        
		        		fo2=true;          
		            break;
		        	case REQUEST_CAMERA03:        
		        		fo3=true;            
		            break;
		                             
		        }
		} catch (Exception e) {
			// TODO: handle exception
			
			Toast.makeText(getActivity(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
        
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		view = getActivity().getLayoutInflater().inflate(
				R.layout.dialogo_observacion, null);
		iniciarlizar();
		builder.setView(view)
				// Set the action buttons
				.setPositiveButton("Aceptar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
								

							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});
		return builder.create();
	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obs1 = getArguments().getString("ob1");
        obs2 = getArguments().getString("ob2");
        obs3 = getArguments().getString("ob3");
        
        enrutamiento= getArguments().getString("enrutamiento");
        matricula= getArguments().getString("matricula");
        path=admin.getRutaSalida()+"/Observacion/Observacion-"+matricula+"-";
        Log.i("path",path);
        
        
        
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
	                    	String men="";
	                        Boolean wantToCloseDialog = true;
	                        
	                      //Obtengo la informacion de los cuadros de texto
							if(!txtOb1.getText().toString().equals(""))
								obs1=txtOb1.getText().toString();
							if(!txtOb2.getText().toString().equals(""))
								obs2=txtOb2.getText().toString();
							if(!txtOb3.getText().toString().equals(""))
								obs3=txtOb3.getText().toString();
							
	                        if(obs1!=null && esObligatorio(obs1) && fo1==false)
	                        {
	                        	wantToCloseDialog=false;
	                        	men="Foto de la observacion 1 es obligatoria";
	                        }
	                        if(obs2!=null && esObligatorio(obs2) && fo2==false)
	                        {
	                        	wantToCloseDialog=false;
	                        	men="Foto de la observacion 2 es obligatoria";
	                        }
	                        if(obs3!=null && esObligatorio(obs3) && fo3==false)
	                        {
	                        	wantToCloseDialog=false;
	                        	men="Foto de la observacion 3 es obligatoria";
	                        }
	                        if(wantToCloseDialog)
	                        {
	                        	mListener.onDialogAceptarClick(DialogoObservaciones.this, obs1,obs2,obs3 );
	                            dismiss();
	                        }else{
	                        	Toast.makeText(getActivity(), men, Toast.LENGTH_SHORT).show();
	                        }
	                        
	                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
	                    }
	                });
	    }
	}
	private boolean esObligatorio(String c)
	{
		boolean r=false;
		//Busco el causal con codigo c
		for (int i = 0; i < observaciones.size(); i++) {
			String cau=observaciones.get(i);
			Log.i("esObligatorio", "cau: "+cau);
			String aux[]=cau.split(",");
			if(aux[0].equals(c))
			{
				Log.i("Causal: esObligatorio", aux[2]);
				if(aux[2].equals("1;"))
					r=true;
				break;
			}
		}
		
		return r;
	}

}
