package com.example.mysql;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

//Se referencian las Clases necesarias para la conexion con el Servidor MySQL
import com.lucamax.basiclibrary.sql.SqlAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.lucamax.basiclibrary.sql.SqlAdapter;

//Ejemplo aplicacion Android que permite conectar con un servidor MySQl y realizar
//consultas sobre una Base de Datos creada

public class MainActivity extends AppCompatActivity {

    //Declaramos los componentes necesarios para introducir los datos de conexion al servidor
    private EditText edServidor;
    private EditText edPuerto;
    private EditText edUsuario;
    private EditText edPassword;
    private EditText edBaseDatos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Enlazamos los componentes con los recursos definidos en el layout
        edServidor = (EditText)findViewById(R.id.edServidor);
        edBaseDatos = (EditText)findViewById(R.id.edBaseDatos);
        edPuerto  = (EditText)findViewById(R.id.edPuerto);
        edUsuario = (EditText)findViewById(R.id.edUsuario);
        edPassword = (EditText)findViewById(R.id.edPassword);
    }

    //Funcion que establecer la conexion con el Servidor si los datos introducidos son correctos.
    //Devuelve un valor de verdadero o falso que indica si se ha establecido la conexion
    public boolean conectarMySQL()
    {
        //Variable boolean que almacena si el estado de la conexion es true o false
        boolean estadoConexion = false;
        //Inicializamos la Clase Connection encarada de conectar con la base de datos.
        Connection conexionMySQL = null;

        //Se declaran varias variables de tipo String que almacenaran los valores introducidos por pantalla
        String user = edUsuario.getText().toString();
        String password = edPassword.getText().toString();
        String puerto = edPuerto.getText().toString();
        String ip = edServidor.getText().toString();
        String baseDatos = edBaseDatos.getText().toString();
        //Asignamos el driver a una variable de tipo String
        String driver = "com.mysql.jdbc.Driver";
        SqlAdapter sqlAdapter = new SqlAdapter("mysql root/lnpTur01@jdbc:mysql://10.0.0.4:3306/sucamec?autoReconnect=true&#38;useSSL=true");
        System.out.println("Resutado is open = " + sqlAdapter.isOpen());
        try {
            conexionMySQL = DriverManager.getConnection("jdbc:mysql://10.0.0.4:3306/sucamec", "root", "lnpTur01");
        } catch (Exception e)
            {
            e.printStackTrace();
    }
        if (conexionMySQL==null){
            return false;
        }

        if (1==1) {
            return true;
        }
        ip="10.0.0.4";
        puerto="3306";
        //Construimos la url para establecer la conexion
        String urlMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/";
        try{
            baseDatos= "sucamec";
            user= "root";
            password="lnpTur01";
            //Cargamos el driver del conector JDBC
            Class.forName(driver).newInstance ();
            //Establecemos la conexion con el Servidor MySQL indicandole como parametros la url construida,
            //la Base de Datos a la que vamos a conectarnos, y el usuario y contrasenia de acceso al servidor
            conexionMySQL = DriverManager.getConnection(urlMySQL + baseDatos, user, password);
            //Comprobamos que la conexion se ha establecido
            if(!conexionMySQL.isClosed())
            {
                estadoConexion = true;
                Toast.makeText(MainActivity.this,"Conexion Establecida", Toast.LENGTH_LONG).show();
            }
        }catch(Exception ex)
        {
            Toast.makeText(MainActivity.this,"Error al comprobar las credenciales:" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally
        {
            try {
                if (1==2)
               conexionMySQL.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return estadoConexion;
    }

    //Evento On Click que realiza la llamada a la función conectarMySQL() obteniendo el valor de verdadero
    //o falso para la peticion de conexion
    public void abrirConexion(View view)
    {
        edServidor = (EditText)findViewById(R.id.edServidor);
        edPuerto  = (EditText)findViewById(R.id.edPuerto);
        edUsuario = (EditText)findViewById(R.id.edUsuario);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edBaseDatos=(EditText)findViewById(R.id.edBaseDatos);
        Intent intent = new Intent(this,ConsultasSQL.class);
        //Si el valor devuelto por la funcion es true, pasaremos los datos de la conexion a la siguiente Activity
        if(conectarMySQL() == true)
        {
            Toast.makeText(this, "Los datos de conexion introducidos son correctos."
                    , Toast.LENGTH_LONG).show();
            // Añadimos cinco parámetros con el método putExtra
            intent.putExtra("servidor", edServidor.getText().toString());
            intent.putExtra("puerto", edPuerto.getText().toString());
            intent.putExtra("usuario", edUsuario.getText().toString());
            intent.putExtra("password", edPassword.getText().toString());
            intent.putExtra("datos", edBaseDatos.getText().toString());
            startActivity(intent);

        }
    }

}
