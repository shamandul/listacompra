package es.webweaver.listadelacompra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Clase que nos peremite conectarnos a la base de datos
 * a través de un adptador
 *
 * @author Jesús de Serdio
 */
public class DBAdapter {
    private static final String CAMPO_ID = "_id";
    private String CAMPO_ARTICULO = "articulo";
    private String CAMPO_ESTABLECIMIENTO = "establecimiento";
    private static final String TABLA_BD = "Productos";
    private static final String DB_NOMBRE = "dblistacompra";
    private  final Context contexto;
    private SQLiteDatabase baseDatos;
    private SQLiteHelper bdHelper;
    /**
     * Constructor
     * @param contexto contexto
     */
    public DBAdapter(Context contexto){
        this.contexto=contexto;
    }
    /**
     * Método que abre la base de datos
     * @return this
     * @throws SQLException excepción de SQL
     */
    public DBAdapter abrir() throws SQLException {

        // Abrimos la base de datos en modo escritura
        bdHelper = new SQLiteHelper(contexto);
        baseDatos = bdHelper.getReadableDatabase();
        return this;
    }

    /**
     * Método que nos permite obtener el campo id
     * @return String
     */
    private String getCampoId() {
        return CAMPO_ID;
    }

    /**
     * Método que nos permite comprobar si existe la base de datos
     * @return boolean verdadero o falso
     */
    public boolean existeBD(){
        SQLiteDatabase sqliteDB = null;
        String myPath = contexto.getDatabasePath(DB_NOMBRE).getPath();
        //String myPath ="/data/data/es.webweaver.listadelacompra/databases/dblistacompra.db";
        try{
            sqliteDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //si llegamos aqui es porque la base de datos no existe aún.

        }
        if(sqliteDB != null){

            sqliteDB.close();

        }
        return sqliteDB != null ;
    }

    /**
     * Método que cierra la base de datos
     */
    public void cerrar(){
        bdHelper.close();
    }

    /**
     * Método que nos permite obtener los registro de la tabla y nos devuelve
     * un cursor
     * @return Cursor devuelve un cursor
     */
    public Cursor obtenerArticulos(){

        return baseDatos.query(TABLA_BD, new String[]{getCampoId(), CAMPO_ARTICULO,
                CAMPO_ESTABLECIMIENTO}, null, null, null, null, null);

    }

    /**
     * Método que nos permite obtener los registro de la tabla y nos devuelve
     * un cursor con los artículos
     * @return cursor
     */
    public Cursor obtenerArticulo(){

        return baseDatos.query(TABLA_BD, new String[]{getCampoId(), CAMPO_ARTICULO}, null, null, null, null, null);

    }

    /**
     * Método que nos permite buscar en nuestra base de datos a partir del artículo
     * @param articulo de tipo string
     * @return Cursor de tipo Cursor
     * @throws SQLException excepción SQL
     */

    public Cursor getDatosArticulo(String articulo) throws SQLException{

        Cursor mCursor = baseDatos.query(true, TABLA_BD, new String[]{getCampoId(), CAMPO_ARTICULO,
                CAMPO_ESTABLECIMIENTO}, CAMPO_ARTICULO + "= '" + articulo +"'", null, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Método que nos permite buscar en nuestra base de datos a partir de la id
     * @param id de tipo int
     * @return Cursor de tipo Cursor
     * @throws SQLException excepción SQL
     */

    public Cursor getId(int id) throws SQLException{

        Cursor mCursor = baseDatos.query(true, TABLA_BD, new String[]{getCampoId(), CAMPO_ARTICULO,
                CAMPO_ESTABLECIMIENTO}, getCampoId() + "= '" + id +"'", null, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /**
     * Método que nos permite ingresar registros en nuestra Base de datos
     * @param registro de tipo Registro
     */
    public void insertarRegistro(Registro registro){

        ContentValues nuevoRegistro= new ContentValues();

        nuevoRegistro.put(CAMPO_ARTICULO, registro.getArticulo());
        nuevoRegistro.put(CAMPO_ESTABLECIMIENTO, registro.getEstablecimiento());

        baseDatos.insert(TABLA_BD, null, nuevoRegistro);
    }

    /**
     * Método que nos permite borrar un Artículo
     * @param id de tipo int
     */
    public void borrarArticulo(int id){

        baseDatos.delete(TABLA_BD,"_id="+id, null);

    }

    /**
     * Método que nos permite borrar todos los artículos
     */
    public void borrarTodosArticulos(){

        baseDatos.delete(TABLA_BD,null, null);

    }

}
