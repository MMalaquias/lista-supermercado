package br.com.supermercado;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText txt_produto;
    ProdutoAdapter adapter;

    View.onClickListener clicl_ck = new View(onClickListener(){
        Override
        public void onClickListener(View view){


            CheckBox ck (CheckBox) View;
            int posicao = (int) ck.getTag ;
            Produto produtoSelecionado = adapter.getItem(posicao);

            Produto produtoDB = Produto.findById(Produto.class,produtoSelecionado.getId());

            if(ck.isChecked()){
                produtoDB.setAtivo(true);
                produtoDB.save();

                Toast.makeText(getApplicationContext(),"Checado", Toast.LENGTH_LONG)
                        .show();
            }else{
                produtoDB.setAtivo(false);
                produtoDB.save();

                produtoSelecionado.setAtivo(true);
                Toast.makeText(getApplicationContext(),"Não Checado", Toast.LENGTH_LONG)
                        .show();
            }
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_view);
        txt_produto = (EditText) findViewById(R.id.txtProduto);
        List<Produto> lstProdutos = Produto.listAll(Produto.class);


        /*
        lstProduto.add(new Produto("Arroz", false));
        lstProduto.add(new Produto("Feijão", false));
        lstProduto.add(new Produto("Macarrão", true));
        */
        adapter = new ProdutoAdapter(this, lstProdutos);




        list_view.setAdapter(adapter);
    }

    public void inserirItem(View view){

        String produto = txt_produto.getText().toString();

        if(produto.isEmpty())return;

        Produto p = new Produto(produto,false);

        adapter.add(p);

        p.save();

        txt_produto.setText(null);
    }

    //classe do adapter

    private class ProdutoAdapter extends ArrayAdapter<Produto>{
        public ProdutoAdapter(Context ctx, List<Produto> items){
            super(ctx, 0, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View v = convertView;

            if(v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, null);
            }

            Produto item = getItem(position);
            TextView txt_item_produto = v.findViewById(R.id.txt_item_produto);
            CheckBox ck_item_produto = v.findViewById(R.id.ck_item_produto);

            txt_item_produto.setText(item.getNome());
            ck_item_produto.setChecked(item.isAtivo());

            ck_item_produto.setTag(position);

            ck_item_produto.setOnClickListener(clicl_ck);

            return v;
        }
    }

}
