package edu.ncue.im;

import android.app.ActionBar;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import edu.ncue.test.jls.R;

public class SettingsActivity extends Activity {
	
	private SharedPreferences mPrefs;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			Intent intent = new Intent(this, MainMapActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	 
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.settings);
		 
		 ActionBar actionBar = getActionBar();
		 actionBar.setDisplayHomeAsUpEnabled(true);
		 
		 Spinner spinner = (Spinner) findViewById(R.id.distance_spinner);
		 //�إߤ@��ArrayAdapter����A�é�m�U�Կ�檺���e
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"��","��","��"});
		 //�]�w�U�Կ�檺�˦�
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinner.setAdapter(adapter);
		 //�]�w���سQ������᪺�ʧ@
		 mPrefs = this.getSharedPreferences("edu.ncue.im.DistanceSetting", Context.MODE_PRIVATE);
		 int distanceSetting = mPrefs.getInt("distanceSetting", 1);
		 spinner.setSelection(distanceSetting);
		 spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
		 public void onItemSelected(AdapterView adapterView, View view, int position, long id){
			 SharedPreferences.Editor editor = mPrefs.edit();
			 switch(adapterView.getSelectedItemPosition()){				
			 				case 0:
			 					editor.putInt("distanceSetting", 0);
			 					break;
			 				case 1:
			 					editor.putInt("distanceSetting", 1);
			 					break;
			 				case 2:
			 					editor.putInt("distanceSetting", 2);
			 					break;
			 				}
			 editor.commit();
		 }
		 @Override
		 public void onNothingSelected(AdapterView<?> arg0) {}
		 });
		 		
		 
	}
	
}