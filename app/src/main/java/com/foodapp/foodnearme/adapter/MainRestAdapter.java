package com.foodapp.foodnearme.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foodapp.foodnearme.CircleImageView;
import com.foodapp.foodnearme.CircularImageView;
import com.foodapp.foodnearme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Harsh on 07-Mar-15.
 */
public class MainRestAdapter extends BaseAdapter {
    JSONArray obj;
    Context cont;
    public MainRestAdapter(JSONArray obj,Context context)
    {
        this.obj = obj;
        cont = context;
    }
    @Override
    public int getCount() {
        return obj.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;


        LayoutInflater mInflater = (LayoutInflater)
                cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_rest_list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.rest_title);
            holder.imageView = (CircularImageView) convertView.findViewById(R.id.rest_icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject currentobj = obj.getJSONObject(position);
            holder.txtTitle.setText(currentobj.getString("rest_nam"));
            holder.imageView.setImageBitmap(drawText(currentobj.getString("rest_nam").substring(0,1),(int)(50*cont.getResources().getDisplayMetrics().density),(int)(25*cont.getResources().getDisplayMetrics().density)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public static Bitmap drawText(String text, int textWidth, int textSize) {
// Get text dimensions
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);

// Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawPaint(paint);

// Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }
}
