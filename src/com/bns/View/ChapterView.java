package com.bns.View;

import android.app.*;
import android.os.*;
import android.widget.*;

import com.bns.Common.*;
import com.bns.CustomAdapter.*;
import com.bns.Eliars.*;

public class ChapterView extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_view);
        
        Common.ViewList.put("ChapterView",ChapterView.this);
        
        ListView chapter_List = (ListView)this.findViewById(R.id.chapter_list);
        TextView textView = (TextView)this.findViewById(R.id.main_text);
        
        textView.setText("Select Chapter - !!");
        textView.setTextSize(32);
        
        String[] array = this.getResources().getStringArray(R.array.chapter_array);
        ChapterAdapter adapter = new ChapterAdapter(this,array);
        chapter_List.setAdapter(adapter);
    }
    
}
