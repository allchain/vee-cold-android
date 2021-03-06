package tech.vee.veecoldwallet.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.Gson;

import tech.vee.veecoldwallet.R;
import tech.vee.veecoldwallet.Util.UIUtil;
import tech.vee.veecoldwallet.Wallet.VEEAccount;
import tech.vee.veecoldwallet.Wallet.VEEWallet;

public class ConfirmTxActivity extends AppCompatActivity {
    private static final String TAG = "Winston";
    private ActionBar actionBar;
    private ConfirmTxActivity activity;

    private VEEAccount sender;
    private String recipient,assetId, feeAssetId, txId, attachment, walletStr;
    private long timestamp, amount, fee;
    private short feeScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tx);

        activity = this;

        Intent intent = getIntent();
        String action = intent.getStringExtra("ACTION");

        Gson gson = new Gson();
        String senderStr;
        walletStr = intent.getStringExtra("WALLET");

        switch (action) {
            case "PAYMENT":
                senderStr = intent.getStringExtra("SENDER");

                sender = gson.fromJson(senderStr, VEEAccount.class);
                recipient = intent.getStringExtra("RECIPIENT");
                amount= intent.getLongExtra("AMOUNT", 0);
                fee = intent.getLongExtra("FEE", 0);
                feeScale = intent.getShortExtra("FEESCALE", Short.valueOf("100"));
                attachment = intent.getStringExtra("ATTACHMENT");
                timestamp =intent.getLongExtra("TIMESTAMP", 0);

                UIUtil.setPaymentTx(activity, sender, recipient, amount,
                       fee, feeScale, attachment, timestamp);
                break;

            case "TRANSFER":
                senderStr = intent.getStringExtra("SENDER");

                sender = gson.fromJson(senderStr, VEEAccount.class);
                recipient = intent.getStringExtra("RECIPIENT");
                assetId = intent.getStringExtra("ASSET_ID");
                feeAssetId = intent.getStringExtra("FEE_ASSET_ID");
                attachment = intent.getStringExtra("ATTACHMENT");
                amount= intent.getLongExtra("AMOUNT", 0);
                fee = intent.getLongExtra("FEE", 0);
                timestamp =intent.getLongExtra("TIMESTAMP", 0);

                UIUtil.setTransferTx(activity, sender, recipient, amount,
                        assetId, fee, feeAssetId, attachment, timestamp);
                break;

            case "LEASE":
                senderStr = intent.getStringExtra("SENDER");

                sender = gson.fromJson(senderStr, VEEAccount.class);
                recipient = intent.getStringExtra("RECIPIENT");
                amount= intent.getLongExtra("AMOUNT", 0);
                fee = intent.getLongExtra("FEE", 0);
                feeScale = intent.getShortExtra("FEESCALE", Short.valueOf("100"));
                timestamp =intent.getLongExtra("TIMESTAMP", 0);

                UIUtil.setLeaseTx(activity, sender, recipient, amount, fee, feeScale, timestamp);
                break;

            case "CANCEL_LEASE":
                senderStr = intent.getStringExtra("SENDER");

                sender = gson.fromJson(senderStr, VEEAccount.class);
                txId = intent.getStringExtra("TX_ID");
                fee = intent.getLongExtra("FEE", 0);
                feeScale = intent.getShortExtra("FEESCALE", Short.valueOf("100"));
                timestamp =intent.getLongExtra("TIMESTAMP", 0);

                UIUtil.setCancelLeaseTx(activity, sender, txId, fee, feeScale, timestamp);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        Drawable icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_qr_code);
        icon.mutate();
        icon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(icon);
        actionBar.setTitle(R.string.title_confirm_tx);
    }

    public String getWalletStr() {
        return walletStr;
    }
}
