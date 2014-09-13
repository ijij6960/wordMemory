package com.bns.CustomAdapter;import android.content.*;import android.view.*;import android.widget.*;import com.bns.Common.*;import com.bns.Eliars.*;import com.bns.View.*;public class ChapterAdapter extends ArrayAdapter<String> {	private LayoutInflater inflater;	private String[] objects;	public ChapterAdapter(Context context, String[] objects) {		super(context, 0, objects);		// TODO Auto-generated constructor stub		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		this.objects = objects;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		// TODO Auto-generated method stub		View view = null;		final int _position = position;		final ViewGroup root = parent;		if(convertView == null)		{					view = inflater.inflate(R.layout.chapter_item_view , null);		}		else		{			view = convertView;		}				TextView chapterText = (TextView)view.findViewById(R.id.chapter_Num);		chapterText.setText(objects[_position]);		chapterText.setTextSize(30);		view.setOnClickListener(new View.OnClickListener() {						@Override			public void onClick(View v) {				// TODO Auto-generated method stub				Context context = root.getContext();				Intent intent = new Intent(context,ProblemView.class);				intent.putExtra("Chapter", _position +1);				context.startActivity(intent);				Common.ViewList.get("ChapterView").finish();			}		});		return view;	}}