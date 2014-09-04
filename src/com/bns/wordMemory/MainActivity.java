package com.bns.wordMemory;

import java.text.*;
import java.util.*;

import android.database.sqlite.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.bns.Controller.*;
import com.bns.DataClass.*;
import com.bns.Database.*;


public class MainActivity extends ActionBarActivity {
	static int nextClickCount = 0;
	private SQLiteDatabase _dbW = null;
	private SQLiteDatabase _dbD = null;
	private DBQueryWord dbqw = null;
	private DBQueryDate dbqd = null;
	private ListView listView = null;
	private MACustomAdapter ma_adapter = null;
	private String randomAnswer = null;
	private List<String> answerList = null;
    private List<String> wordList = null;
    private List<String> meanList = null;
    private String problemString = null;
    private DBQueryWord.PROBLEMTYPE type = null;
    
	/* layout */
	private TextView textView = null;
	private Button button = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.db_init();
        
        wordList = dbqw.wordList(this._dbW);
        meanList = dbqw.meanList(this._dbW);
        if(answerList == null) answerList = this.setAnswer(meanList, meanList.size()); 
        
        listView = (ListView)this.findViewById(R.id.answer);
        textView = (TextView)this.findViewById(R.id.problemText);
        button = (Button)this.findViewById(R.id.nextBtn);
        
        ma_adapter = new MACustomAdapter(this,0,answerList);
        
        listView.setAdapter(ma_adapter);
        if(answerList != null && answerList.size() != 0) randomAnswer = answerList.get((int)(Math.random() * 5));
        
        String problemString = dbqw.getData(this._dbW, randomAnswer, DBQueryWord.PROBLEMTYPE.WORD);
        textView.setText(problemString);
        this.type = DBQueryWord.PROBLEMTYPE.WORD;
        nextClickCount = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Word");
        menu.add(0, 1, 0, "Mean");
        menu.add(0, 2, 0, "Miss");
        /*
         * menu 3 : setting
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
        case 0:
        	answerList = this.setAnswer(meanList, meanList.size());
            ma_adapter = new MACustomAdapter(this,0,answerList);
            
            listView.setAdapter(ma_adapter);
            if(answerList != null && answerList.size() != 0) randomAnswer = answerList.get((int)(Math.random() * 5));
            
            problemString = dbqw.getData(this._dbW, randomAnswer, DBQueryWord.PROBLEMTYPE.WORD);
            textView.setText(problemString);
            nextClickCount = 0;
            this.type = DBQueryWord.PROBLEMTYPE.WORD;
        	return true;
        case 1:
        	answerList = this.setAnswer(wordList, wordList.size());
            ma_adapter = new MACustomAdapter(this,0,answerList);
            
            listView.setAdapter(ma_adapter);
            if(answerList != null && answerList.size() != 0) randomAnswer = answerList.get((int)(Math.random() * 5));
            
            problemString = dbqw.getData(this._dbW, randomAnswer, DBQueryWord.PROBLEMTYPE.MEAN);
            textView.setText(problemString);
            this.type = DBQueryWord.PROBLEMTYPE.MEAN;
            nextClickCount = 0;
        	return true;
        case 2:
        	Toast.makeText(this, "Menu 3", Toast.LENGTH_SHORT).show();
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
    }
    
    private void db_init()
    {
    	String date = this.setDate();
    	dbqw = new DBQueryWord("word","word.db",this);
    	dbqd = new DBQueryDate(date,date + ".db",this);
    	this._dbW = dbqw.DBCreate();
    	this._dbD = dbqd.DBCreate();
    	dbqw.DeleteAll(this._dbW);
    	dbqd.DeleteAll(this._dbD);
    	dbqw.TableCreate(this._dbW);
    	dbqd.TableCreate(this._dbD);
    	Word word = new Word(0,"get","얻다");
    	Word word1 = new Word(1,"set","설정");
    	Word word2 = new Word(2,"fire","불");
    	Word word3 = new Word(3,"water","물");
    	Word word4 = new Word(4,"heaven","하늘");
    	Word word5 = new Word(5,"knife","칼");
    	Word word6 = new Word(6,"android","안드로이드");
    	Word word7 = new Word(7,"iphone","아이폰");
    	Word word8 = new Word(8,"cancel","취소");
    	Word word9 = new Word(9,"ok","오케이");
    	dbqw.Insert(this._dbW, word);
    	dbqw.Insert(this._dbW, word1);
    	dbqw.Insert(this._dbW, word2);
    	dbqw.Insert(this._dbW, word3);
    	dbqw.Insert(this._dbW, word4);
    	dbqw.Insert(this._dbW, word5);
    	dbqw.Insert(this._dbW, word6);
    	dbqw.Insert(this._dbW, word7);
    	dbqw.Insert(this._dbW, word8);
    	dbqw.Insert(this._dbW, word9);
    }
    
    private String setDate()
    {
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd",Locale.KOREA);
    	String result = sdf.format(date);
    	return result;
    }
    
    private List<String> setAnswer(List<String> list ,int list_size)
    {
    	List<String> temp = list;
    	TreeSet<String> tempSet = new TreeSet<String>();
    	List<String>result = new ArrayList<String>();
    	while(tempSet.size() < 5)
    	{
    		int index = (int)(Math.random()*10);
    		String tempString = temp.get(index);
    		tempSet.add(tempString);
    	}
    	
    	while(tempSet.size() != 0)
    	{
    		String first = tempSet.first();
    		result.add(first);
    		tempSet.remove(first);
    	}
    	return result;
    }
    
    public void checkAnswer(View view)
    {
    	nextClickCount++;
    	String selected = null;
    	for(int index = 0;index < MACustomAdapter.check.size();index++)
    	{
    		String temp = MACustomAdapter.check.get(index);
    		if(temp.equals("ON"))
    		{
    			selected = MACustomAdapter.checkString.get(index);
    		}
    	}
    	
    	String resultString = dbqw.getData(this._dbW, selected, this.type);
    	
    	if(resultString != null)
    	{
    		// add MissTable OK
    		Log.d("Check","OK");
    		DateDB dateDB = new DateDB(result);
    	}
    	else
    	{
    		// add MissTable Miss
    		Log.d("Check", "Miss");
    	}
    	this.updateList();
    	if(nextClickCount == 9)
    	{
    		button.setText("Result");
    	}
    	else if(nextClickCount == 10)
    	{
    		nextClickCount = 0;
    	}
    	//page move and database update
    }
    
    private void updateList()
    {
    	if(type == DBQueryWord.PROBLEMTYPE.MEAN)
    	{
    		answerList = this.setAnswer(wordList, wordList.size());
    		ma_adapter = new MACustomAdapter(this,0,answerList);
        
        	listView.setAdapter(ma_adapter);
        	if(answerList != null && answerList.size() != 0) randomAnswer = answerList.get((int)(Math.random() * 5));

        	problemString = dbqw.getData(this._dbW, randomAnswer, DBQueryWord.PROBLEMTYPE.MEAN);
        	textView.setText(problemString);
    	}
    	else if(type == DBQueryWord.PROBLEMTYPE.WORD)
    	{
        	answerList = this.setAnswer(meanList, meanList.size());
            ma_adapter = new MACustomAdapter(this,0,answerList);
            
            listView.setAdapter(ma_adapter);
            if(answerList != null && answerList.size() != 0) randomAnswer = answerList.get((int)(Math.random() * 5));
            /* not display */
            problemString = dbqw.getData(this._dbW, randomAnswer, DBQueryWord.PROBLEMTYPE.WORD);
            Log.d("problemString is null","" + problemString);
            textView.setText(problemString);
    	}
    }
}
