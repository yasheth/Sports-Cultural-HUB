package yasheth.sportsandculturalhub1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class S_Sports_Indi extends AppCompatActivity {


    private static final String url = "jdbc:mysql://samipsdb.ce5w22sralgz.us-west-2.rds.amazonaws.com:3306/projectdb";
    private static final String user = "samip";
    private static final String password="iamsamip";


    String username;
    String sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle b=getIntent().getExtras();
        sport=b.getString("SPORT");
        String desc=b.getString("DESC");
        String id=b.getString("ID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_sports_indi_layout);

        SharedPreferences prefs = getSharedPreferences("content", MODE_PRIVATE);
        username= prefs.getString("uname", "default");

        TextView s_head=(TextView)findViewById(R.id.s_head);
        s_head.setText(sport);
        s_head=(TextView)findViewById(R.id.s_desc);
        s_head.setText(desc);

        if(id.equals("1"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_football);
        }
        else if(id.equals("2"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_tabletennis);//tabletennis
        }
        else if(id.equals("3"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_cricket);//cricket
        }
        else if(id.equals("4"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_basketball);//basketball
        }
        else if(id.equals("5"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_tennis);//tennis
        }
        else if(id.equals("6"))
        {
            ImageView cov=(ImageView)findViewById(R.id.s_cov);
            cov.setBackgroundResource(R.drawable.cov_volleyball);//volleyball
        }
    }

    public void onclick(View v) {
        if (v.getId() == R.id.btnregsport) {
            Toast toast= Toast.makeText(getApplicationContext(), "Registering.. \nPlease Wait!", Toast.LENGTH_LONG);
            toast.show();

            new MyTask().execute();
        }
    }
    private class MyTask extends AsyncTask<Void,Void,Void>{
        private int id;
        private java.sql.ResultSet rs=null,rs1=null;
        int rflag=0;
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                rs1=st.executeQuery("select * from Registration where `Student_username`='"+username+"' and `Activity`='"+sport+"'");
                if(rs1.next()) {
                    rflag++;
                }
                else {
                    rs = st.executeQuery("select * from Student where `Student_username`='" + username + "'");
                    while (rs.next()) {
                        Statement st1 = con.createStatement();
                        st1.executeUpdate("insert into Registration(`Student_username`,`Student_name`,`Student_Rollno`,`Student_emailid`,`Student_phoneno`,`Activity`)" + "values('" + rs.getString(5) + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getString(7) + "','" + sport + "')");
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                if(rflag==0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Registered for " + sport, Toast.LENGTH_LONG);
                    toast.show();
                    Intent i = new Intent(S_Sports_Indi.this, S_Sports.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Already Registered for " + sport, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }

}


