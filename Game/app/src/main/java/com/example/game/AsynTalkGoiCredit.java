package com.example.game;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AsynTalkGoiCredit extends AsyncTask<String , Void , String> {


    Button[] btn_credit = new Button[]{};

    public AsynTalkGoiCredit(Button[] btn_credit) {
        this.btn_credit = btn_credit;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
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
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i = 0;i<jsonArray.length();i++){
                String Credit = jsonArray.getJSONObject(i).getString("credit");
                String SoTien = jsonArray.getJSONObject(i).getString("so_tien");
              GiaoDienChoiGame.goiGreditArrayList.add(new GoiGredit(Credit,SoTien));
            }
            if(GiaoDienChoiGame.goiGreditArrayList.size() != 0){
                for (int i =0 ;i<GiaoDienChoiGame.goiGreditArrayList.size();i++){
                    btn_credit[i].setText(GiaoDienChoiGame.goiGreditArrayList.get(i).getCredit());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
