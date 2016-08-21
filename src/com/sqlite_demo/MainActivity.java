package com.sqlite_demo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity<Stirng> extends Activity {
	
	Button btntaotb, btnxoadb, btnxemnk;
	Button btnthem, btnsua, btnxoa, btnxem;
	EditText edtmaso, edttenphim, edtnuocsx, edtnamsx;
	ListView lvds;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      btntaotb = (Button) findViewById(R.id.btntaotb);
      btnxoadb = (Button) findViewById(R.id.btnxoadb);

      btnthem = (Button) findViewById(R.id.btnthem);
   
      btnxoa = (Button) findViewById(R.id.btnxoa);
      btnxem = (Button) findViewById(R.id.btnxem);
      
      edtmaso = (EditText) findViewById(R.id.edtmaso);
      edttenphim = (EditText) findViewById(R.id.edttenphim);
      edtnuocsx = (EditText) findViewById(R.id.edtnuocsx);
      edtnamsx = (EditText) findViewById(R.id.edtnamsx);
      
      lvds = (ListView) findViewById(R.id.lvds);
      
      btntaotb.setOnClickListener( new myEvent());
      
      btnxoadb.setOnClickListener( new myEvent());
      
      btnthem.setOnClickListener( new myEvent());
      
      btnxoa.setOnClickListener( new myEvent());
      
      btnxem.setOnClickListener( new myEvent());
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    SQLiteDatabase database = null;
    public void TaoCSDL(){
    	database = openOrCreateDatabase("qlphim.db", MODE_PRIVATE, null);
    }
    
    public void TaoBangFilm(){
    	
    	String tbphim = "CREATE TABLE PHIM (ms_phim integer primary key autoincrement, ten_phim text, nuoc_sx text, nam_sx, integer)";
    	database.execSQL(tbphim);
  
    }
    
    public void CreateTable(){
    	
    	TaoCSDL();
    	TaoBangFilm();
    }

    
    public void Them(){
    	ContentValues v = new ContentValues();
		v.put("ms_phim", edtmaso.getText().toString());
		v.put("ten_phim", edttenphim.getText().toString());
		v.put("nuoc_sx", edtnuocsx.getText().toString());
		v.put("nam_sx", edtnamsx.getText().toString());
		String message = "";
		if(database.insert("phim", null, v)== -1)
			message = "Thêm phim thất bại!";
		else 
				message = "Phim đã được thêm thành công!";
		
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		
		edtmaso.setText("");
		edttenphim.setText("");
		edtnuocsx.setText("");
		edtnamsx.setText("");
		
    }

    public void Xoa(){
    	String message = "";
    	String ms = edtmaso.getText().toString();
    	if(database.delete("phim", "ms_phim =?", new String[] {ms})== -1)
    		message = "Xóa phim thất bại";
    	else message = "Xóa phim thành công";
    	
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void Xem(){
    	
    	// khai báo mảng listview
    	String view = "";
    	ArrayList<String> arraylist = null;
        ArrayAdapter<String> adapter = null;
        arraylist = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
        lvds.setAdapter(adapter);
        
    	Cursor c = database.query("phim", null, null, null, null, null, null);
    	c.moveToFirst();
    	while(!c.isAfterLast()){
    		view +=c.getString(0)+ "-";
    		view +=c.getString(1)+ "-";
    		view +=c.getString(2)+ "-";
    		view +=c.getString(3);
    		view += "\n";
    		c.moveToNext();
    	}
    		c.close();
    		
    	  arraylist.add(view);
    	  
    	  adapter.notifyDataSetChanged();
    	  
    	//Toast.makeText(this, view, Toast.LENGTH_LONG).show();
    	
    }
    
    public void Xemnk(){
    	String view = "";
    	Cursor c = database.query("insert_diary", null, null, null, null, null, null);
    	c.moveToFirst();
    	while(!c.isAfterLast()){
    		view +=c.getInt(0)+"-";
    		view +=c.getString(1)+"-";
    		view +=c.getString(2);
    		view +="\n";
    		c.moveToNext();
    		
    	}
    	Toast.makeText(this, view, Toast.LENGTH_LONG).show();
    	c.close();
    }
    
    public void XoaDB(){
    	String s = "";
    	if(deleteDatabase("qlphim.db")==true){
    		s = "Đã xóa CSDL";
    	}
    	else{
    		s = "Có lỗi, hoặc chưa có CSDL!";
    	}
    	Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    
    } 
    class myEvent implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			switch(arg0.getId()){
			
				case R.id.btntaotb:
					CreateTable(); break;
					
				case R.id.btnxoadb:
					XoaDB(); break;
					
//				case R.id.btnxemnk:
//					Xemnk(); break;
					
				case R.id.btnthem:
					Them(); break;
//					
//				case R.id.btnsua:
//					Sua(); break;
					
				case R.id.btnxoa:
					Xoa(); break;
					
				case R.id.btnxem:
					Xem(); break;
			}
		}
    }
    
//  public void Sua(){
//	ContentValues v = new ContentValues();
//	v.put("ten_phim", edttenphim.getText().toString());
//	if(database.update("phim", v, "ms_phim =?", new String[] {edtmaso.getText().toString()})!= -1)
//		
//		Toast.makeText(this, "Cập nhật thành công!!", Toast.LENGTH_LONG).show();
//	
//	else Toast.makeText(this, "Có lỗi! Cập nhật thất bại!", Toast.LENGTH_LONG).show();
//}
    
    
    
//  public void TaoBang_nhatky_themphim(){
//  	String tb_nkChen = "CREATE TABLE INSERT_DIARY (id integer primary key autoincrement, ten_phim text, thoi_gian datetime)";
//  	database.execSQL(tb_nkChen);
//  	
//  	String trigger_nkChen = "CREATE or replace TRIGGER NK after"
//  			+ "insert on phim"
//  			+ "for each row"
//  			+ "begin"
//  			+ "insert into insert_diary (ten_phim, thoi_gian) values (new.ten_phim, datetime('now'));"
//  			+ "end";
//  	database.execSQL(trigger_nkChen);
//  }
  
  
  
}
