package com.digitalcashbag.mlm.fragments.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalcashbag.R;
import com.digitalcashbag.mlm.adapter.WalletLedgerAdapter;
import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kkm.com.core.app.PreferencesManager;
import kkm.com.core.constants.BaseFragment;
import kkm.com.core.model.response.wallet.ResponseWalletLedger;
import kkm.com.core.utils.LoggerUtil;
import kkm.com.core.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletLedger extends BaseFragment {

    @BindView(R.id.available_balance)
    TextView availableBalance;
    @BindView(R.id.avail_lo)
    LinearLayout availLo;
    @BindView(R.id.wallet_ledger_recycler)
    RecyclerView walletLedgerRecycler;
    @BindView(R.id.noRecFound)
    RelativeLayout noRecFound;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_ledger, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        walletLedgerRecycler.setLayoutManager(manager);

        if (NetworkUtils.getConnectivityStatus(context) != 0) {
            getWalletLedger();
        } else {
            showAlert(getResources().getString(R.string.alert_internet), R.color.red, R.drawable.error);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getWalletLedger() {
        JsonObject param = new JsonObject();
        param.addProperty("Fk_MemId", PreferencesManager.getInstance(context).getUSERID());
        showLoading();
        LoggerUtil.logItem(param);
        Call<ResponseWalletLedger> directCall = apiServices.getEWalletLedger(param);

        directCall.enqueue(new Callback<ResponseWalletLedger>() {
            @Override
            public void onResponse(@NonNull Call<ResponseWalletLedger> call, @NonNull Response<ResponseWalletLedger> response) {
                hideLoading();
                LoggerUtil.logItem(response.body());
                if (response.body().getResponse().equalsIgnoreCase("Success")) {
                    WalletLedgerAdapter adapter = new WalletLedgerAdapter(context, response.body().getEWalletLedgerlist());
                    walletLedgerRecycler.setAdapter(adapter);
                    walletLedgerRecycler.setVisibility(View.VISIBLE);
                    noRecFound.setVisibility(View.GONE);
                } else {
                    if (walletLedgerRecycler != null)
                        walletLedgerRecycler.setVisibility(View.GONE);
                    noRecFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseWalletLedger> call, @NonNull Throwable t) {
                hideLoading();
            }
        });
    }

}