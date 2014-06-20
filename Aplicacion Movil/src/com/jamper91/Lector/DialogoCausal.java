package com.jamper91.Lector;

import java.io.File;
import java.util.Vector;

import android.annotation.SuppressLint;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jamper91.base.Administrador;
import com.jamper91.servicios.R;

@SuppressLint("NewApi")
public class DialogoCausal extends DialogFragment {

	// Elementos graficos a usar
	EditText txtCausal;
	ImageView btnCamara,imgFoto;
	View view;
	//Variable que contiene todos Causales
	Vector<String> causales=null;
	//Variable que almacena la direccion de la foto a tomar
	private String path=null,causal=null;
	//Variables que recibe este dialogo, para funcionar bn
	String enrutamiento="",rutaFoto=null;
	private final int REQUEST_CAMERA = 1;
	Administrador admin = Administrador.getInstance(null);
	
	//Para determinar is tomo la foto
	Boolean fc1=false;
	
	static DialogoCausal newInstance(String enrutamiento,String causal, String rutaFoto)
	{
		DialogoCausal dC=new DialogoCausal();
		Bundle args=new Bundle();
		args.putString("enrutamiento", enrutamiento);
		args.putString("causal", causal);
		args.putString("rutaFoto", rutaFoto);
		dC.setArguments(args);
		return dC;
	}
	

	public interface DialogoCausalListener {
		public void onDialogAceptarClick(DialogFragment dialog, String causal,
				String rutaFoto);
	}

	DialogoCausalListener mListener;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mListener = (DialogoCausalListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " debe implementar DialogoArchivosListener");
		}
	}
	public void iniciarlizar() {
		causales=admin.getAllElementos("Causales");
		txtCausal = (EditText) view.findViewById(R.id.Dialogo_Causal_txtCausal);
		btnCamara = (ImageView) view.findViewById(R.id.Dialogo_Causal_btnCamara);
		btnCamara.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			try {
    				Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
    				File photo = new File(path);
    				i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
    				startActivityForResult(
    						Intent.createChooser(i, "Capturar Foto"),
    						REQUEST_CAMERA);

    			} catch (Exception e) {

    			}
            }
        });
		imgFoto=(ImageView)view.findViewById(R.id.Dialogo_causal_imgFoto);
		//Luego de inicializar, muestro los valore spor default
		if(causal!=null)
			txtCausal.setText(causal);
		if(rutaFoto!=null)
		{
			File imgFile = new  File(rutaFoto);
			if(imgFile.exists()){

			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    int nh = (int) ( myBitmap.getHeight() * (512.0 / myBitmap.getWidth()) );
			    Bitmap scaled = Bitmap.createScaledBitmap(myBitmap, 512, nh, true);
			    imgFoto.setImageBitmap(scaled);
			    imgFoto.setVisibility(0);

			}
		}
		

		
	}

		@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			  try {
				  Log.i("onActivityResult","requestCode"+ requestCode+"");
				  switch (requestCode)
			        {
			        	case REQUEST_CAMERA:    
			        		rutaFoto=path;
			        		fc1=true;
			            break ;
			                             
			        }
			} catch (Exception e) {
				Log.e("onActivityResult", "Error: "+e.getMessage());
			}
	        
		}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		view = getActivity().getLayoutInflater().inflate(
				R.layout.dialogo_causal, null);
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
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enrutamiento = getArguments().getString("enrutamiento");
        rutaFoto=getArguments().getString("rutaFoto");
        causal=getArguments().getString("causal");
        //Log.i("rutaFoto", rutaFoto);
        //Log.i("causal", causal);
        
        path=admin.getRutaSalida()+"/Causal/Causal-"+enrutamiento+".jpg";
        Log.i("Path: ",path);
        
        
	}
	
	private boolean esObligatorio(String c)
	{
		boolean r=false;
		//Busco el causal con codigo c
		for (int i = 0; i < causales.size(); i++) {
			String cau=causales.get(i);
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
	                    	causal = txtCausal.getText().toString();
	                        Boolean wantToCloseDialog = true,obligatorio=esObligatorio(causal);
	                        
	                        Log.i("causal: ",causal+"");
	                        Log.i("Obligatorio: ",obligatorio+"");
	                        Log.i("fc1: ",fc1+"");
	                        if(rutaFoto==null)
	                        	rutaFoto="";
	                        
	                        if(causal.length()==0)
	                        	causal=null;
	                        if(causal==null && fc1==false)
	                        	wantToCloseDialog=true;
	                        else if(causal.length()>0 && fc1==false && obligatorio){
	                        	wantToCloseDialog=false;
	                        	men="Foto Obligatoria";
	                        }
	                        
	                        if(wantToCloseDialog)
	                        {
	                        	mListener.onDialogAceptarClick(
										DialogoCausal.this, causal, rutaFoto);
	                            dismiss();
	                        }else{
	                        	Toast.makeText(getActivity(), men, Toast.LENGTH_SHORT).show();
	                        }
	                        
	                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
	                    }
	                });
	    }
	}

}
