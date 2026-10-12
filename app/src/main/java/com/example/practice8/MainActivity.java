package com.example.practice8;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> products;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = new ArrayList<>();
        products.add(new Product("Чай", "шт."));
        products.add(new Product("Говядина", "кг."));
        products.add(new Product("Молоко", "л."));
        products.add(new Product("Яблоко", "кг."));
        products.add(new Product("Соль", "кг."));
        products.add(new Product("Яйца", "шт."));

        ListView productList = findViewById(R.id.productList);
        Button addProduct = findViewById(R.id.addProduct);
        //поля дополнительного задания
        EditText nameProduct = findViewById(R.id.nameProduct);
        EditText unitProduct = findViewById(R.id.unitProduct);

        adapter = new ProductAdapter(this, R.layout.list_item, products);
        productList.setAdapter(adapter);

        //слушатель дополнительной кнопки
        addProduct.setOnClickListener(v -> {
            String name = nameProduct.getText().toString().trim();
            String unit = unitProduct.getText().toString().trim();

            if (name.isEmpty() || unit.isEmpty()) {
                Toast.makeText(this,"Введите название и единицу измерения", Toast.LENGTH_SHORT).show();
                return;
            }

            products.add(new Product(name, unit));
            adapter.notifyDataSetChanged();

            nameProduct.setText("");
            unitProduct.setText("");
            nameProduct.requestFocus();
        });

        //слушатель выбора дополнительной функции
        productList.setOnItemClickListener((parent, view, position, id) -> {
            showManageDialog(position);
        });
    }

    //открытие всплывающего окна для управления
    private void showManageDialog(int position) {
        Product product = products.get(position);

        new AlertDialog.Builder(this)
                .setTitle(product.getName())
                .setItems(new CharSequence[]{"Редактировать", "Удалить"}, (dialog, which) -> {
                    if (which == 0) {
                        showEditDialog(position);
                    }
                    else {
                        confirmDeleteDialog(position);
                    }
                })
                .show();
    }

    //открытие всплывающего окна для удаления
    private void confirmDeleteDialog(int position) {
        Product product = products.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Удалить товар?")
                .setMessage(product.getName())
                .setPositiveButton("Удалить", (d, w) -> {
                    products.remove(position);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    //открытие всплывающего окна для редактирования
    private void showEditDialog(int position) {
        Product product = products.get(position);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);
        EditText nameInput = new EditText(this);
        nameInput.setHint("Название");
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
        nameInput.setText(product.getName());
        layout.addView(nameInput);
        EditText unitInput = new EditText(this);
        unitInput.setHint("Ед. измерения");
        unitInput.setInputType(InputType.TYPE_CLASS_TEXT);
        unitInput.setText(product.getUnit());
        layout.addView(unitInput);
        new AlertDialog.Builder(this)
                .setTitle("Редактирование")
                .setView(layout)
                .setPositiveButton("Сохранить", (d, w) -> {
                    String newName = nameInput.getText().toString().trim();
                    String newUnit = unitInput.getText().toString().trim();
                    if (newName.isEmpty() || newUnit.isEmpty()) { Toast.makeText(this,"Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    product.setName(newName);
                    product.setUnit(newUnit);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}