package es.webweaver.listadelacompra;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que nos permite crear la base de datos
 *
 * @author Jesús de Serdio
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String BD_NOMBRE = "dblistacompra.db";
    private static final int BD_VERSION = 1;
    private static final String BD_CREAR = "CREATE TABLE Productos(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " articulo TEXT NOT NULL, establecimiento TEXT NOT NULL)";


    /**
     * Constructor de la clase SqliteHelper
     * @param contexto de  tipo Context
     */
    SQLiteHelper(Context contexto) {
        super(contexto, BD_NOMBRE, null, BD_VERSION);
    }

    /**
     * Método onCreate que es llamado si la base de datos no existe
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(BD_CREAR);
    }

    /**
     * Método onUpgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Productos");
        // Se crea la nueva versión de la tabla
        onCreate(db);
    }
}
