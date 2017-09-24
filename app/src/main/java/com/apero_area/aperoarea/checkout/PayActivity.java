package com.apero_area.aperoarea.checkout;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apero_area.aperoarea.R;
import com.apero_area.aperoarea.model.CenterRepository;
import com.apero_area.aperoarea.model.entities.Product;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.ArrayList;

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
    private ArrayList<String> idProduct = new ArrayList<String>();
    private ArrayList<String> quantityProduct = new ArrayList<String>();
    Location loc = null;
    String posGps = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        communicator = new Communicator();

        Bundle extras = getIntent().getExtras();
        amount = extras.getString("plan_price");
        loc = (Location) getIntent().getParcelableExtra("loc");
        posGps = loc.getLatitude()+ " , " + loc.getLongitude();

        stripe = new Stripe(getApplicationContext(),"pk_test_QxNQHsJ0MaEiKDtVAlcj7Qk0");
    }

    public void submitCard(View view) {
        cardName = (TextView) findViewById(R.id.cardName);
        cardFirstName = (TextView) findViewById(R.id.cardFirstName);
        cardMail = (TextView) findViewById(R.id.cardMail);
        cardPhoneNumber = (TextView) findViewById(R.id.cardPhoneNumber);
        notes = (TextView) findViewById(R.id.notes);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave != null) {
            cardToSave.setCurrency("eur");
            cardToSave.setName(cardName.getText().toString() + " " + cardFirstName.getText().toString());

            stripe.createToken(cardToSave, "pk_test_QxNQHsJ0MaEiKDtVAlcj7Qk0", new TokenCallback() {
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
            Log.e("test", "Invalid Card Data");
            //mErrorDialogHandler.showError("Invalid Card Data");
        }
    }

}


