package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Se referencian las Clases necesarias para la conexion con el Servidor MySQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//Ejemplo aplicacion Android que permite conectar con un servidor MySQl y realizar
//consultas sobre una Base de Datos creada
//
public class ConsultasSQL extends AppCompatActivity {

    //Declaramos los componentes y clases necesarias para realizar consultas a una base de datos en MySQL
    private TextView txtBaseDatos;
    private EditText edConsulta;
    private TextView txtResultados;
    private TextView txtPuerto;
    private TextView txtServidor;
    private TextView txtUsuario;
    private TextView txtPassword;
    private Statement st;
    private ResultSet rs;
    private Connection con;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_sql);

        //Enlazamos los componentes con los recursos definidos en el layout
        txtBaseDatos = (TextView)findViewById(R.id.txtBaseDatos);
        edConsulta = (EditText)findViewById(R.id.edConsulta);
        txtResultados = (TextView)findViewById(R.id.txtResultados);
        txtPuerto = (TextView)findViewById(R.id.txtPuerto);
        txtServidor = (TextView)findViewById(R.id.txtServidor);
        txtUsuario = (TextView)findViewById(R.id.txtUsuario);
        txtPassword= (TextView) findViewById(R.id.txtPassword);

        bundle = getIntent().getExtras();
        //Obtenemos los valores introducidos en la Activity principal
        txtPuerto.setText(bundle.getString("puerto"));
        txtServidor.setText(bundle.getString("servidor"));
        txtBaseDatos.setText(bundle.getString("datos"));
        txtUsuario.setText(bundle.getString("usuario"));
        txtPassword.setText(bundle.getString("password"));

    }
    //Evento On Click que conecta con el servidor MySQL y procesa las consultas mostrando los resultados
    public void mostrarResultados(View view)
    {
        try{
            if(edConsulta.getText().toString() != "")
            {
                //Asignamos el driver a una variable de tipo String
                String driver = "com.mysql.jdbc.Driver";
                //Construimos la url para establecer la conexion
                String urlMySQL = "jdbc:mysql://" + txtServidor.getText().toString() + ":"
                        + txtPuerto.getText().toString() + "/";
                //Cargamos el driver del conector JDBC
                Class.forName(driver).newInstance ();
                //Establecemos la conexion con el Servidor MySQL indicandole como parametros la url construida,
                //la Base de Datos a la que vamos a conectarnos, y el usuario y contrasenia de acceso al servidor
                con = DriverManager.getConnection(urlMySQL + txtBaseDatos.getText().toString(), txtUsuario.getText().toString()
                        , txtPassword.getText().toString());
                st = con.createStatement();
                //Se ejecutara la consulta indicada en el campo edConsulta
                rs = st.executeQuery(edConsulta.getText().toString());
                String resultadoSQL = "";
                Integer numColumnas = 0;

                //Variable que almacenar� el n�mero de columnas obtenidas de la consulta Transact-SQL
                numColumnas = rs.getMetaData().getColumnCount();

                //B�cle encargado de recorrer y mostrar los resultados a partir de la consulta ejecutada
                while (rs.next())
                {
                    for (int i = 1; i <= numColumnas; i++)
                    {
                        if (rs.getObject(i) != null)
                        {
                            if (resultadoSQL != "")
                                if (i < numColumnas)
                                    resultadoSQL = resultadoSQL + rs.getObject(i).toString() + ";";
                                else
                                    resultadoSQL = resultadoSQL + rs.getObject(i).toString();
                            else
                            if (i < numColumnas)
                                resultadoSQL = rs.getObject(i).toString() + ";";
                            else
                                resultadoSQL = rs.getObject(i).toString();
                        }
                        else
                        {
                            if (resultadoSQL != "")
                                resultadoSQL = resultadoSQL + "null;";
                            else
                                resultadoSQL = "null;";
                        }
                    }
                    resultadoSQL = resultadoSQL + "n";
                }
                txtResultados.setText(resultadoSQL);
                st.close();
                rs.close();
            }
        }catch(Exception ex)
        {
            Toast.makeText(this, "Error al obtener resultados de la consulta Transact-SQL: "
                    + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }





}
