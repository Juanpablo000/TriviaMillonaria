package com.example.triviamillonaria;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comodin> listaComodines;

    public Adapter(Context context, ArrayList<Comodin> listaComodines) {
        this.context = context;
        this.listaComodines = listaComodines;
    }


    @Override
    public int getCount() {
        return listaComodines.size();
    }

    @Override
    public Object getItem(int i) {
        return listaComodines.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Comodin comodin = (Comodin) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.layoutcomodines, null);
        ImageView  imgFoto = (ImageView) view.findViewById(R.id.imgComodin);
        TextView tvTitulo = (TextView) view.findViewById(R.id.txtTitulo);
        TextView tvDescripcion = (TextView) view.findViewById(R.id.txtDescripcion);

        imgFoto.setImageResource(comodin.getImgFoto());
        tvTitulo.setText(comodin.getTitulo());
        tvDescripcion.setText(comodin.getDescripcion());
        return view;
    }
}
