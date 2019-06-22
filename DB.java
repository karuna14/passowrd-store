package pass.kk.passwordstore;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    public DB(Context context){
        super(context,"Important",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sd){
        sd.execSQL("create table Password(name text,password text)");
    }
    @Override public void onUpgrade(SQLiteDatabase db,int ov,int nv){
        db.execSQL("drop table if exists Password");
        onCreate(db);
    }
    public boolean insert(String name,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",pass);
        db.insert("Password",null,contentValues);
        //db.close();
        return true;
    }
    public boolean delet(String name,String Pass){
        final String n=name;
        final String p=Pass;
        SQLiteDatabase db=this.getWritableDatabase();
        if(name.equals("")&&p.equals("")){
            return false;
        }
      //  db.execSQL("delete from Password where name='"+name+"' and password='"+Pass+"'");
        db.delete("Password","name=? and password=?",new String[]{n,p});
        //  db.close();
        return true;
    }
   public ArrayList<String> getAllName(){
        ArrayList<String> al=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
       Cursor res=db.rawQuery("select * from Password",null);
       res.moveToFirst();
       while (res.isAfterLast()==false){
           al.add(res.getString(res.getColumnIndex("name")));
           res.moveToNext();
       }
       return al;
   }
    public ArrayList<String> getAllPass(){
        ArrayList<String> al=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from Password",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){
            al.add(res.getString(res.getColumnIndex("password")));
            res.moveToNext();
        }
        return al;
    }
}
