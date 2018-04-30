
package com.example.diancanclient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter
{
	private class ListItemView
    {                //自定义控件集合    
            public ImageView imageview;    
            public TextView tv1;    
            public TextView tv2;
            public ImageView tv_reduce;
            public TextView tv_num;
            public ImageView tv_add;           
    }   
	private Context context;                        //运行上下文  
    private List<HashMap<String, Object>> listItems;    //商品信息集合  
    private LayoutInflater listContainer;           //视图容器                
    private ListItemView  listItemView ;
      
    public MyAdapter(Context context, List<HashMap<String, Object>> listitems) {  
        this.context = context;           
        listContainer = LayoutInflater.from(context);   //创建视图容器并设置上下文  
        this.listItems = listitems;  
    }  

	public int getCount() {  
        // TODO Auto-generated method stub  
        return listItems.size();  
    }  

    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return listItems.get(position);  
    }  

    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  


    public View getView( int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        //自定义视图  
        if (convertView == null) {  
            listItemView = new ListItemView();   
            //获取list_item布局文件的视图  
            convertView = listContainer.inflate(R.layout.list_item, null);  
            //获取控件对象  
            listItemView.imageview = (ImageView)convertView.findViewById(R.id.imageview);  
            listItemView.tv1 = (TextView)convertView.findViewById(R.id.tv1);  
            listItemView.tv2 = (TextView)convertView.findViewById(R.id.tv2);
            listItemView.tv_reduce = (ImageView)convertView.findViewById(R.id.tv_reduce);
            listItemView.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            listItemView.tv_add = (ImageView)convertView.findViewById(R.id.tv_add);
            //设置控件集到convertView  
            convertView.setTag(listItemView);  
        }else {  
            listItemView = (ListItemView)convertView.getTag();  
        }  

        listItemView.imageview.setImageBitmap((Bitmap)listItems.get(  
                position).get("dpicture"));  
        listItemView.tv1.setText((String) listItems.get(position)  
                .get("dname"));  
        listItemView.tv2.setText((String) listItems.get(position)  
                .get("dprice"));  
        String number = String.valueOf(listItems.get(position)  
                .get("number"));
        
        if(number.equals("0"))
        {
        	listItemView.tv_reduce.setVisibility(View.INVISIBLE);
        	listItemView.tv_num.setVisibility(View.INVISIBLE);
        }
        else
        {
        	listItemView.tv_reduce.setVisibility(View.VISIBLE);
        	listItemView.tv_num.setVisibility(View.VISIBLE);
        }

        listItemView.tv_num.setText(number);
        listItemView.tv_add.setTag(position);
        listItemView.tv_reduce.setTag(position);
        listItemView.tv_add.setOnClickListener(new numOnClickListener(position));
        listItemView.tv_reduce.setOnClickListener(new numOnClickListener(position)); 
        return convertView;  
    }  
    
    public void addNum(int position) throws Exception{
        String numstring= String.valueOf(listItems.get(position).get("number"));
        int num= Integer.parseInt(numstring);
        num++;
        listItems.get(position).put("number",num);
        String dname=(String) listItems.get(position).get("dname");
        for(int j=0;j< mainActivity.cporder.size();j++)
        {
        	if(mainActivity.cporder.get(j).getDname().equals(dname))
        	{
        		Integer i=mainActivity.cporder.get(j).getQuantity()+1;
        		mainActivity.cporder.get(j).setQuantity(i);
        	}
    		
        }
    
        this.notifyDataSetChanged();
        
      }
      //减少edittext中的数据
      public void cutNum(int position){
        //获取一个item里面edittext中的数据源
    	  String numstring= String.valueOf(listItems.get(position).get("number"));
          int num= Integer.parseInt(numstring);
        if(num<=0){
        	listItems.get(position).put("number","0");
        }
        else{
        	num--;
            //将数据源中改变后的数据重新放进数据源中，再加载到item中
            listItems.get(position).put("number",num);
            String dname=(String) listItems.get(position).get("dname");
            for(int j=0;j< mainActivity.cporder.size();j++)
            {
            	if(mainActivity.cporder.get(j).getDname().equals(dname))
            	{
            		Integer i=mainActivity.cporder.get(j).getQuantity()-1;
            		mainActivity.cporder.get(j).setQuantity(i);
            	}
            }
        }
        //重新刷新页面
        this.notifyDataSetChanged();
      }
    
class numOnClickListener implements  View.OnClickListener{
    private int position;
    numOnClickListener(int pos){
      position = pos;
    }
    @Override
    //复写onClick方法用来监听按钮
    public void onClick(View v){
      int vid = v.getId();
	if(vid ==listItemView.tv_add.getId()){
        try {
			addNum(position);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
      else if(vid == listItemView.tv_reduce.getId()){
        cutNum(position);
      }

    }
}
}