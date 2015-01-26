package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int mem1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void recalculate() {
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();

        if(e!="") {
            String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");
            long result = Long.parseLong(tokens[0]);

            for (int i = 1; i < tokens.length; i = i + 2) {
                if((i+1)<tokens.length) {
                    switch (tokens[i]) {
                        case "+":
                            result = result + Long.parseLong(tokens[i + 1]);
                            break;
                        case "-":
                            result = result - Long.parseLong(tokens[i + 1]);
                            break;
                        case "*":
                            result = result * Long.parseLong(tokens[i + 1]);
                            break;
                        case "/":

                            result = result / Long.parseLong(tokens[i + 1]);
                            break;

                    }
                }
            }

            TextView tvAns = (TextView) findViewById(R.id.tvAns);
            tvAns.setText(Long.toString(result));
        }
    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void memClicked(View v){
        String bu1 = ((TextView)v).getText().toString();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);

        int result;

        if(tvAns.getText().length()>0){
            result = Integer.parseInt(tvAns.getText().toString());
        } else {
            result = 0;
        }

        switch (bu1){
            case "M+":
                mem1 += result;
                break;
            case "M-":
                mem1 -= result;
                break;
            case "MC":
                mem1 = 0;
                break;

            case "MR":
                expr.append(mem1);
                break;
        }
        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory: " +mem1, Toast.LENGTH_SHORT);
        t.show();
        updateExprDisplay();
        recalculate();
    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator and expr is not "",
        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing
        if(expr.length()>0) {
            char lastchar = expr.charAt(expr.length() - 1);

            String o = ((TextView) v).getText().toString();

            if (lastchar != '+' && lastchar != '-' && lastchar != '*' && lastchar != '/') {
                expr.append(o);

                updateExprDisplay();
            } else if(lastchar=='+' ||lastchar=='-' ||lastchar=='*' ||lastchar=='/'){
                     expr.deleteCharAt(expr.length()-1);
                    expr.append(o);
                updateExprDisplay();
                }

        }
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();

        TextView tvAns = (TextView) findViewById(R.id.tvAns);
        tvAns.setText("");
        mem1 = 0;

        updateExprDisplay();
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);


            if(expr.length()>0) {

                    recalculate();

            } else {
                TextView tvAns = (TextView) findViewById(R.id.tvAns);
                tvAns.setText("");
            }

        }
        updateExprDisplay();



    }

    public void equalClicked(View v){
        TextView tvAns = (TextView)findViewById(R.id.tvAns);

        if(tvAns.getText()!="") {
            expr.delete(0, expr.length());
            expr.append(tvAns.getText());
            updateExprDisplay();
            tvAns.setText("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
