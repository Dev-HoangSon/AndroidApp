package com.example.game;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AsyntalkLinhVuc extends AsyncTask<String , Void , String> {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    public AsyntalkLinhVuc(Button btn1, Button btn2, Button btn3, Button btn4) {
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.btn3 = btn3;
        this.btn4 = btn4;
    }


    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader =new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
            content.append(line);
            }
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject response = new JSONObject(s);
            JSONArray jsonArray = response.getJSONArray("data");
            for(int i=0;i<=3;i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String ten = jsonObject.getString("ten_linh_vuc");
               GiaoDienChoiGame.linh_vucArrayList.add(new Linh_Vuc(Integer.valueOf(id),ten));
            }
            if(GiaoDienChoiGame.linh_vucArrayList.size() != 0){
                btn1.setText(String.valueOf(GiaoDienChoiGame.linh_vucArrayList.get(0).getTenlinhVuc()));
                btn2.setText(String.valueOf(GiaoDienChoiGame.linh_vucArrayList.get(1).getTenlinhVuc()));
                btn3.setText(String.valueOf(GiaoDienChoiGame.linh_vucArrayList.get(2).getTenlinhVuc()));
                btn4.setText(String.valueOf(GiaoDienChoiGame.linh_vucArrayList.get(3).getTenlinhVuc()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
