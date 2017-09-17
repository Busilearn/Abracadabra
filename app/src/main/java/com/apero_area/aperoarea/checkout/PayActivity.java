package com.apero_area.aperoarea.checkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apero_area.aperoarea.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;


public class PayActivity extends AppCompatActivity {

    private Communicator communicator;
    Stripe stripe;
    String amount;
    Card card;
    Token tok;
    private TextView cardName;
    private TextView cardFirstName;
    private TextView cardMail;
    private TextView cardPhoneNumber;
    private TextView notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        communicator = new Communicator();

        Bundle extras = getIntent().getExtras();
        amount = extras.getString("plan_price");
        Log.e("test",amount);
        try {
            stripe = new Stripe("pk_test_QxNQHsJ0MaEiKDtVAlcj7Qk0");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public void submitCard(View view) {
        TextView cardNumberField = (TextView) findViewById(R.id.cardNumber);
        TextView monthField = (TextView) findViewById(R.id.month);
        TextView yearField = (TextView) findViewById(R.id.year);
        TextView cvcField = (TextView) findViewById(R.id.cvc);
        cardName = (TextView) findViewById(R.id.cardName);
        cardFirstName = (TextView) findViewById(R.id.cardFirstName);
        cardMail = (TextView) findViewById(R.id.cardMail);
        cardPhoneNumber = (TextView) findViewById(R.id.cardPhoneNumber);
        notes = (TextView) findViewById(R.id.notes);

        card = new Card(
                cardNumberField.getText().toString(),
                Integer.valueOf(monthField.getText().toString()),
                Integer.valueOf(yearField.getText().toString()),
                cvcField.getText().toString()
        );

        card.setCurrency("eur");
        card.setName(cardName.getText().toString() + " " + cardFirstName.getText().toString());

        stripe.createToken(card, "pk_test_QxNQHsJ0MaEiKDtVAlcj7Qk0", new TokenCallback() {
            public void onSuccess(Token token) {
                Toast.makeText(getApplicationContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                tok = token;
                //new StripeCharge(token.getId()).execute();
                communicator.loginPost("charge", cardName.getText().toString(), cardFirstName.getText().toString(), cardPhoneNumber.getText().toString(), cardMail.getText().toString(), token.getId(),amount, "eur", notes.getText().toString(), getApplicationContext());
            }

            public void onError(Exception error) {
                Log.d("Stripe", error.getLocalizedMessage());
            }
        });
    }

}


