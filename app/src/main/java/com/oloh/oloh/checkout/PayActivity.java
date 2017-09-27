package com.oloh.oloh.checkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oloh.oloh.R;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Product;
import com.oloh.oloh.util.AppConstants;
import com.oloh.oloh.util.TinyDB;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by micka on 15-Sep-17.
 */

public class PayActivity extends AppCompatActivity {
    private Communicator communicator;
    Stripe stripe;
    String amount;
    Token tok;
    private TextView cardName;
    private TextView cardFirstName;
    private TextView cardMail;
    private TextView cardPhoneNumber;
    private TextView notes;
    private Button submitButton;
    private ArrayList<String> idProduct = new ArrayList<String>();
    private ArrayList<String> quantityProduct = new ArrayList<String>();
    String posGps = null;
    CardInputWidget mCardInputWidget = null;
    Boolean test = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        communicator = new Communicator();

        Bundle extras = getIntent().getExtras();
        amount = extras.getString("plan_price");
        posGps = new TinyDB(getApplicationContext()).getLocation();

        stripe = new Stripe(getApplicationContext(), AppConstants.PUBLISHABLE_KEY);

        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        cardName = (TextView) findViewById(R.id.cardName);
        cardFirstName = (TextView) findViewById(R.id.cardFirstName);
        cardMail = (TextView) findViewById(R.id.cardMail);
        cardPhoneNumber = (TextView) findViewById(R.id.cardPhoneNumber);
        notes = (TextView) findViewById(R.id.notes);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setClickable(false);


        cardName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (cardName.getText().length() < 3) {
                        cardName.setError("Merci d'entrer au minimum 3 caractères");
                        submitButton.setClickable(false);
                    }
                    else
                        submitButton.setClickable(true);
                }
            }
        });

        cardFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (cardFirstName.getText().length() < 3) {
                        cardFirstName.setError("Merci d'entrer au minimum 3 caractères");
                        submitButton.setClickable(false);
                    }
                    else
                        submitButton.setClickable(true);
                }
            }
        });

        cardMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!(isEmailValid(cardMail.getText().toString()))) {
                        cardMail.setError("Merci d'entrer une adresse mail valide");
                        submitButton.setClickable(false);
                    }
                    else
                        submitButton.setClickable(true);
                }
            }
        });

        cardPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (cardPhoneNumber.getText().length() < 10) {
                        cardPhoneNumber.setError("Merci d'entrer un numéro de téléphone valide");
                        submitButton.setClickable(false);
                    }
                    else
                        submitButton.setClickable(true);
                }
            }
        });
    }

    public void submitCard(View view) {

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave != null) {
            cardToSave.setCurrency("eur");
            cardToSave.setName(cardName.getText().toString() + " " + cardFirstName.getText().toString());

            stripe.createToken(cardToSave, AppConstants.PUBLISHABLE_KEY, new TokenCallback() {
                public void onSuccess(Token token) {
                    tok = token;
                    //new StripeCharge(token.getId()).execute();

                    for (Product productFromShoppingList : CenterRepository.getCenterRepository().getListOfProductsInShoppingList()) {
                        //add product ids to array
                        idProduct.add(productFromShoppingList.getId());
                        quantityProduct.add(productFromShoppingList.getQuantity());
                    }
                    communicator.loginPost("charge", cardName.getText().toString(), cardFirstName.getText().toString(), cardPhoneNumber.getText().toString(), cardMail.getText().toString(), token.getId(),amount, "eur", notes.getText().toString(), idProduct, quantityProduct, posGps, getApplicationContext());
                }

                public void onError(Exception error) {
                    Log.d("Stripe", error.getLocalizedMessage());
                }
            });
        }
        else {
            Toast.makeText(getBaseContext(), "Le numéro de carte entré n'est pas valide", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}


