package tech.vee.veecoldwallet.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.ListView;

import tech.vee.veecoldwallet.Activity.AboutUsActivity;
import tech.vee.veecoldwallet.Activity.ColdWalletActivity;
import tech.vee.veecoldwallet.R;
import tech.vee.veecoldwallet.Util.FileUtil;
import tech.vee.veecoldwallet.Util.UIUtil;
import tech.vee.veecoldwallet.Wallet.VEEWallet;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private Activity activity;

    private PreferenceScreen preferenceScreen;
    private SwitchPreference backup;
    private SwitchPreference monitor;
    private VEEWallet wallet;
    private PreferenceScreen aboutUs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();

        addPreferencesFromResource(R.xml.preferences_settings);
        preferenceScreen = (PreferenceScreen) findPreference("settings_export_seed");
        preferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                // custom dialog
                ColdWalletActivity activity = (ColdWalletActivity) getActivity();
                wallet = activity.getWallet();
                UIUtil.createExportSeedDialog(getActivity(), wallet);

                return true;
            }
        });
        backup = (SwitchPreference) findPreference("settings_auto_backup");
        monitor = (SwitchPreference) findPreference("settings_connectivity");
        aboutUs = (PreferenceScreen) findPreference("settings_about_us");
        aboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent();
                intent.setClass(activity, AboutUsActivity.class);
                startActivity(intent);
                return true;
            }
        });
        if (!FileUtil.sdCardMountedExists()) {
            backup.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference){
        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // remove dividers
        View rootView = getView();
        ListView list = (ListView) rootView.findViewById(android.R.id.list);
        list.setDivider(null);
    }
}

